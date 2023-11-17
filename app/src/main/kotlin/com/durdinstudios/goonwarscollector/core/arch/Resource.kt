package com.durdinstudios.goonwarscollector.core.arch

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.durdinstudios.goonwarscollector.core.arch.Resource.State
import com.durdinstudios.goonwarscollector.ui.components.placeholders.LocalLoadingState
import com.durdinstudios.goonwarscollector.ui.components.placeholders.withLocalLoadingState
import com.durdinstudios.goonwarscollector.ui.theme.AppColorPalette
import com.durdinstudios.goonwarscollector.ui.theme.Colors
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.minikorp.duo.Task
import java.io.Serializable
import java.lang.UnsupportedOperationException

/**
 * Simple wrapper to map ongoing tasks (network / database) for view implementation.
 *
 * Similar to kotlin [Result] but with loading and empty state.
 */
class Resource<out T> @PublishedApi internal constructor(
    val state: State,
    val value: Any?,
) : Parcelable {

    enum class State {
        SUCCESS, FAILURE, EMPTY, LOADING
    }

    companion object {

        private const val VALUE_PARCELABLE = 0
        private const val VALUE_SERIALIZABLE = 1

        @JvmField
        val CREATOR: Parcelable.Creator<Resource<*>> = object : Parcelable.Creator<Resource<*>> {
            override fun createFromParcel(source: Parcel): Resource<*> {
                val state = State.valueOf(source.readString()!!)
                val value: Any? = when (source.readInt()) {
                    VALUE_PARCELABLE -> source.readParcelable<Parcelable>(this::class.java.classLoader)
                    VALUE_SERIALIZABLE -> source.readSerializable()
                    else -> null
                }
                return Resource<Any>(state = state, value = value)
            }

            override fun newArray(size: Int): Array<Resource<*>> {
                return Array(size) { empty<Any>() }
            }
        }

        fun <T> success(value: T): Resource<T> = Resource(value = value, state = State.SUCCESS)
        fun <T> failure(exception: Throwable? = null): Resource<T> =
            Resource(value = exception, state = State.FAILURE)

        fun <T> loading(value: T? = null): Resource<T> =
            Resource(value = value, state = State.LOADING)

        fun <T> empty(): Resource<T> = Resource(value = null, state = State.EMPTY)

        /** Alias for empty */
        fun <T> idle(): Resource<T> = empty()
    }

    val isSuccess: Boolean get() = state == State.SUCCESS
    val isEmpty: Boolean get() = state == State.EMPTY
    val isFailure: Boolean get() = state == State.FAILURE
    val isLoading: Boolean get() = state == State.LOADING

    @Suppress("UNCHECKED_CAST")
    fun getOrNull(): T? = if (isSuccess) value as T? else null
    fun exceptionOrNull(): Throwable? = if (isFailure) value as Throwable else null

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(state.name)
        when (value) {
            is Parcelable -> {
                dest.writeInt(VALUE_PARCELABLE)
                dest.writeParcelable(value, flags)
            }
            is Serializable -> {
                dest.writeInt(VALUE_SERIALIZABLE)
                dest.writeSerializable(value)
            }
            else -> throw UnsupportedOperationException("$value can't be written to a parcel")
        }
    }

    override fun describeContents(): Int = 0

    override fun toString(): String {
        return value.toString()
    }
}

@Suppress("UNCHECKED_CAST")
fun <T, R> Resource<T>.map(fn: (T) -> R): Resource<R> {
    if (isSuccess) return Resource.success(fn(this.value as T))
    else return this as Resource<R>
}


@Composable
fun <T> ResourcePlaceholderContent(
    modifier: Modifier = Modifier,
    resource: Resource<T>,
    onErrorRetry: () -> Unit = {},
    placeholderValue: T,
    failureContent: @Composable (Throwable?) -> Unit = { },
    successContent: @Composable (T) -> Unit,
) {
    CompositionLocalProvider(LocalLoadingState provides !resource.isTerminal) {
        Box(modifier) {
            when (resource.state) {
                State.FAILURE -> failureContent(resource.exceptionOrNull())
                State.SUCCESS -> successContent(resource.getOrThrow())
                else -> successContent(placeholderValue)
            }
        }
    }
}

@Composable
fun <T> SwipeToRefreshPlaceholderContent(
    modifier: Modifier = Modifier,
    resource: Resource<T>,
    placeholderValue: T,
    onRefresh: () -> Unit = { },
    isRefreshing: Boolean = resource.isLoading,
    failureContent: @Composable (Throwable) -> Unit = { Text(text = it.message.toString()) },
    successContent: @Composable (T) -> Unit
) {
    withLocalLoadingState(
        (resource.isLoading || resource.isEmpty)
    ) {
        Box(modifier) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { onRefresh() },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state, refreshTriggerDistance = refreshTrigger,
                        backgroundColor = Colors.appColorPalette.primary,
                        contentColor = Colors.appColorPalette.surface
                    )
                }
            ) {
                when (resource.state) {
                    State.FAILURE -> failureContent(resource.exceptionOrNull()!!)
                    State.SUCCESS -> successContent(resource.getOrThrow())
                    else -> successContent(placeholderValue)
                }
            }
        }
    }
}


@Composable
fun <T> SwipeToRefreshTaskContent(
    modifier: Modifier = Modifier,
    task: Task,
    onRefresh: () -> Unit = { },
    isRefreshing: Boolean = task.isRunning,
    placeholderValue: T,
    successValue: T,
    onErrorRetry: () -> Unit = {},
    failureContent: @Composable (Throwable?) -> Unit =
        { },
    successContent: @Composable (T) -> Unit
) {
        withLocalLoadingState(
        (task.isRunning || task.isIdle)
    ) {
        Box(modifier) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { onRefresh() },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state, refreshTriggerDistance = refreshTrigger,
                        backgroundColor = Colors.appColorPalette.primary,
                        contentColor = Colors.appColorPalette.surface
                    )
                }
            ) {
                when (task.state) {
                    Task.State.FAILURE -> failureContent(task.error)
                    Task.State.SUCCESS -> successContent(successValue)
                    else -> successContent(placeholderValue)
                }
            }
        }
    }
}

@Composable
fun <T> TaskPlaceholderContent(
    modifier: Modifier = Modifier,
    task: Task,
    onErrorRetry: () -> Unit = {},
    placeholderValue: T,
    successValue: T,
    failureContent: @Composable (Throwable?) -> Unit =
        { },
    successContent: @Composable (T) -> Unit
) {
    CompositionLocalProvider(LocalLoadingState provides !task.isTerminal) {
        Box(modifier) {
            when (task.state) {
                Task.State.FAILURE -> failureContent(task.error)
                Task.State.SUCCESS -> successContent(successValue)
                else -> successContent(placeholderValue)
            }
        }
    }
}