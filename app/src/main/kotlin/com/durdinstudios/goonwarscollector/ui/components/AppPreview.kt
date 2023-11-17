package com.durdinstudios.goonwarscollector.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.durdinstudios.goonwarscollector.core.AppState
import com.durdinstudios.goonwarscollector.core.appContext
import com.durdinstudios.goonwarscollector.core.arch.AppStore
import com.durdinstudios.goonwarscollector.core.error.EmptyErrorHandler
import com.durdinstudios.goonwarscollector.core.error.ErrorHandler
import com.durdinstudios.goonwarscollector.domain.analytics.AnalyticsMiddleware
import com.durdinstudios.goonwarscollector.domain.persistence.PersistenceController
import com.durdinstudios.goonwarscollector.ui.theme.AppMaterialTheme
import com.durdinstudios.goonwarscollector.ui.components.placeholders.withLocalLoadingState
import com.minikorp.duo.LoggerMiddleware
import com.minikorp.duo.Store
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.compose.withDI
import org.kodein.di.singleton

/**
 * Custom module to provide any nested dependency needed for a [Preview].
 */
private val previewModule = DI.Module("PreviewModule") {
    bind<PersistenceController>() with singleton { PersistenceController(appContext, Moshi.Builder().build()) }
    bind<ErrorHandler>() with singleton { EmptyErrorHandler() }
    bind<AppStore>() with singleton {
        Store(
            initialState = AppState(),
            storeScope = CoroutineScope(Dispatchers.Main.immediate),
            reducer = { }
        ).apply {
            addMiddleware(LoggerMiddleware())
            addMiddleware(AnalyticsMiddleware())
        }
    }
}

/**
 * Wrapper which initializes all the dependencies to be used on [Preview] annotated methods.
 * This wrappers add the actual [AppTheme] and a default [DI] module.
 */
@Composable
fun AppPreview(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    content: @Composable() () -> Unit
) {
    AppMaterialTheme {
        withDI(previewModule) {
            withLocalLoadingState(isLoading = isLoading) {
                Box(modifier) {
                    content()
                }
            }
        }
    }
}
