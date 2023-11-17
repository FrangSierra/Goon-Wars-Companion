package com.durdinstudios.goonwarscollector.core.error

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.durdinstudios.goonwarscollector.R
import com.durdinstudios.goonwarscollector.core.AppState
import com.minikorp.duo.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * Represents the needed values to inflate the default error screen.
 */
data class ErrorUiData(
    @DrawableRes val iconId: Int,
    @StringRes val titleId: Int,
    @StringRes val messageId: Int,
    @StringRes val retryButtonTextId: Int
)

/**
 * Error handler to manage exceptions on the application.
 */
interface ErrorHandler {
    /** Return an [ErrorUiData] for the given error. This method is used to draw the default retry screen */
    fun getDefaultErrorUiData(throwable: Throwable?): ErrorUiData

    /** Handles the given error*/
    fun handleError(throwable: Throwable)
}

/**
 * Empty [ErrorHandler] used to avoid exceptions on the [@Preview] methods.
 */
class EmptyErrorHandler : ErrorHandler {
    override fun getDefaultErrorUiData(throwable: Throwable?): ErrorUiData {
        return when (throwable) {
            else -> ErrorUiData(
                android.R.drawable.stat_notify_error, R.string.app_name, R.string.app_name, R.string.app_name
            )
        }
    }

    override fun handleError(throwable: Throwable) {
    }

}

/**
 * Default [ErrorHandler] implementation.
 */
class DefaultErrorHandler(private val appStore: Store<AppState>) : ErrorHandler {
    val scope = CoroutineScope(Job())

    override fun getDefaultErrorUiData(throwable: Throwable?): ErrorUiData {
        return when (throwable) {
            //TODO special cases
            else -> defaultUiData
        }
    }

    override fun handleError(throwable: Throwable) {
        when (throwable) {
            //TODO special cases
        }
    }

    private val defaultUiData = ErrorUiData(
        android.R.drawable.btn_default, R.string.app_name, R.string.app_name, R.string.app_name
    )
}