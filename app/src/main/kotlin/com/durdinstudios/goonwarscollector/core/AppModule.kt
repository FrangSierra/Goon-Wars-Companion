package com.durdinstudios.goonwarscollector.core

import android.app.Application
import com.durdinstudios.goonwarscollector.core.arch.AppStore
import com.durdinstudios.goonwarscollector.core.deeplinks.DeepLinkDispatcher
import com.durdinstudios.goonwarscollector.core.deeplinks.DeepLinkDispatcherImpl
import com.durdinstudios.goonwarscollector.core.error.DefaultErrorHandler
import com.durdinstudios.goonwarscollector.core.error.ErrorHandler
import com.durdinstudios.goonwarscollector.domain.analytics.AnalyticsMiddleware
import com.durdinstudios.goonwarscollector.domain.persistence.PersistenceController
import com.durdinstudios.goonwarscollector.domain.session.SessionController
import com.durdinstudios.goonwarscollector.domain.session.SessionControllerImpl
import com.durdinstudios.goonwarscollector.domain.session.SessionReducer
import com.durdinstudios.goonwarscollector.domain.session.SessionSaga
import com.durdinstudios.goonwarscollector.domain.wallet.WalletController
import com.durdinstudios.goonwarscollector.domain.wallet.WalletControllerImpl
import com.durdinstudios.goonwarscollector.domain.wallet.WalletReducer
import com.durdinstudios.goonwarscollector.domain.wallet.WalletSaga
import com.minikorp.duo.LoggerMiddleware
import com.minikorp.duo.Store
import com.minikorp.duo.plus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

/**
 * App module for [DI].
 */
object AppModule {
    @Suppress("UndocumentedPublicFunction")
    fun create() = DI.Module("AppModule") {
        bind<Application>() with singleton { app }
        bind<ErrorHandler>() with singleton { DefaultErrorHandler(instance()) }
        bind<DeepLinkDispatcher>() with singleton { DeepLinkDispatcherImpl() }
        bind<PersistenceController>() with singleton { PersistenceController(instance(), instance()) }
        bind<SessionController>() with singleton { SessionControllerImpl() }
        bind<WalletController>() with singleton { WalletControllerImpl(instance(), instance()) }

        bind<AppStore>() with singleton {
            Store(
                initialState = AppState(),
                storeScope = CoroutineScope(Dispatchers.Main.immediate),
                reducer = createAppStateReducer(
                    session = SessionReducer() + SessionSaga(di),
                    wallet = WalletReducer() + WalletSaga(di)
                )
            ).apply {
                addMiddleware(LoggerMiddleware())
                addMiddleware(AnalyticsMiddleware())
            }
        }
    }
}

