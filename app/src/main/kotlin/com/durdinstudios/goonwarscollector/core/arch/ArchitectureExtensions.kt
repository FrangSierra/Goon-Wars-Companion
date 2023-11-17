package com.durdinstudios.goonwarscollector.core.arch

import androidx.compose.runtime.*
import com.durdinstudios.goonwarscollector.core.AppState
import com.minikorp.duo.Action
import com.minikorp.duo.Store
import com.minikorp.duo.Task
import com.minikorp.duo.select
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.transformWhile
import java.lang.IllegalArgumentException

@Composable
fun <T : Any> Store<T>.observeAsState(): State<T> {
    return states.collectAsState()
}

@Composable
fun <T : Any, R> Store<T>.observeAsState(selector: (state: T) -> R): State<R> {
    return states.select { selector(it) }.collectAsState(selector(state))
}

val StoreComposition: ProvidableCompositionLocal<Store<*>> =
    compositionLocalOf { error("Missing Store!") }

@Composable
fun <S : Store<*>> withStore(store: S, content: @Composable () -> Unit) {
    CompositionLocalProvider(StoreComposition provides store) { content() }
}

@Composable
fun dispatch(action: Action): Job {
    return StoreComposition.current.offer(action)
}

//Auto Generate this

@Suppress("UNCHECKED_CAST")
@Composable
private fun appStoreComposition() = StoreComposition.current as Store<AppState>

@Composable
fun useStore(): Store<AppState> {
    return appStoreComposition()
}

class StateSelector<S : Any, T>(
    val keys: Array<(S) -> Any?>,
    val selector: (args: Array<Any?>) -> T
) {

    fun evaluate(store: Store<S>): T {
        val keys = Array(keys.size) { i -> keys[i](store.state) }
        return selector(keys)
    }
}

fun <T, P1> createAppStateSelector(
    p1: (AppState) -> P1,
    selector: (P1) -> T
): StateSelector<AppState, T> {
    return StateSelector(arrayOf(p1)) { args -> selector(args[0] as P1) }
}

fun <T, P1, P2> createAppStateSelector(
    p1: (AppState) -> P1,
    p2: (AppState) -> P2,
    selector: (P1, P2) -> T
): StateSelector<AppState, T> {
    return StateSelector(arrayOf(p1, p2)) { args -> selector(args[0] as P1, args[1] as P2) }
}

@Composable
fun <S : Any, T> rememberSelector(selector: StateSelector<S, T>): T {
    @Suppress("UNCHECKED_CAST")
    val store = StoreComposition.current as Store<S>

    val initialValue = remember(selector) {
        selector.evaluate(store)
    }

    return produceState(initialValue = initialValue, selector, producer = {
        val keys = Array(selector.keys.size) { i -> selector.keys[i](store.state) }
        //Skip first once since it will always match the initial value
        store.states.drop(1).collect { state ->
            var changed = false
            selector.keys.forEachIndexed { i, fn ->
                val newKey = fn(state)
                changed = changed || keys[i] != newKey
                keys[i] = newKey
            }
            if (changed) {
                val newValue = selector.selector(keys)
                value = newValue
            }
        }
    }).value
}

/**
 * Returns a flow that contains the elements of the given flow until the element that matches
 * the [predicate].
 *
 * It behaves the same way as RxJava's takeUntil.
 */
fun <T> Flow<T>.takeUntil(predicate: suspend (T) -> Boolean): Flow<T> =
    transformWhile { emit(it); !predicate(it) }

val <T> Resource<T>.isTerminal get() = isSuccess || isFailure
val Task.isTerminal get() = isSuccess || isFailure
fun <T> Resource<T>.toTask(): Task = when(state){
    Resource.State.SUCCESS -> Task.success()
    Resource.State.FAILURE -> Task.failure(exceptionOrNull())
    Resource.State.EMPTY -> Task.idle()
    Resource.State.LOADING -> Task.running()
}

fun <T> Resource<T>.getOrThrow(): T = getOrNull()!!

fun <T> Resource<T>.getSuccessValueOrNull(): T? = if (isSuccess) value as T? else null
fun <T> Resource<T>.getResourceValue(): T? = if (isSuccess || isLoading) value as T? else null

fun <T> Task.toTypedResource(value: T?): Resource<T> = when(state){
    Task.State.SUCCESS -> Resource.success(value!!)
    Task.State.IDLE -> Resource.empty()
    Task.State.RUNNING -> Resource.loading()
    else -> throw IllegalArgumentException("Tasks of failure type can't be casted to typed Resource")
}


