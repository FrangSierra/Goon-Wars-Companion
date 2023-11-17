package com.durdinstudios.goonwarscollector.core

import android.app.Application
import android.content.Context
import androidx.annotation.RestrictTo
import com.durdinstudios.goonwarscollector.core.arch.AppStore
import com.durdinstudios.goonwarscollector.domain.DataModule
import com.durdinstudios.goonwarscollector.domain.session.SessionState
import com.durdinstudios.goonwarscollector.domain.wallet.WalletState
import com.minikorp.duo.Action
import com.minikorp.duo.State
import com.minikorp.duo.TypedAction
import com.minikorp.grove.ConsoleLogTree
import com.minikorp.grove.Grove
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.*
import org.kodein.di.conf.ConfigurableDI
import kotlin.properties.Delegates
import kotlin.system.measureTimeMillis

/**
 * Main [Application] for the project. No needed at all for a Compose project, but it allow us to have an entry
 * point to initialize all the needed libraries and so.
 */
private var appInstance: App by Delegates.notNull()
val app: App get() = appInstance
val appContext: Context get() = app

class App : Application(), DIAware {
    val scope = CoroutineScope(Job())

    override val di = ConfigurableDI(mutable = true)

    private var testModule: DI.Module? = null

    override fun onCreate() {
        appInstance = this
        super.onCreate()
        Grove.plant(ConsoleLogTree())

        //  val crashlytics = FirebaseCrashlytics.getInstance()
        //  crashlytics.setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)

        val exceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Grove.e(e) { "Uncaught exception" }
            //  crashlytics.recordException(e)
            exceptionHandler?.uncaughtException(t, e)
        }

        initializeInjection()

        val store: AppStore = di.direct.instance()

        scope.launch(Dispatchers.Main) {
            Grove.d { "App gonna initialize" }
            val bootstrapTime = measureTimeMillis {
                store.dispatch(BootstrapAction)
            }
            Grove.d { "App got initialized in $bootstrapTime ms" }
        }
    }

    /**
     * Initializes dependency injection.
     */
    @Suppress("LongMethod")
    fun initializeInjection() {
        // Clear everything
        di.clear()

        with(di) {
            // First, add all dependencies
            addImports(
                false,
                // App
                AppModule.create(),
                // Network
                DataModule.create()
            )

            if (testModule != null) addImport(testModule!!, true)
        }
    }

    /**
     * Sets a test module to use in UI tests. Must be followed by [initializeInjection].
     */
    @RestrictTo(RestrictTo.Scope.TESTS)
    fun setTestModule(init: DI.Builder.() -> Unit) {
        testModule = DI.Module(name = "TestDIModule", allowSilentOverride = true, init = init)
    }

    /**
     * Clears current test module. Must be followed by [initializeInjection].
     */
    @RestrictTo(RestrictTo.Scope.TESTS)
    fun clearTestModule() {
        testModule = null
    }
}

private fun ConfigurableDI.addImports(allowOverride: Boolean, vararg moduleInfo: DI.Module) {
    moduleInfo.forEach { addImport(it, allowOverride) }
}

/**
 * Application State. Stored by [AppStore].
 */
@State
data class AppState(
    val session: SessionState = SessionState(),
    val wallet: WalletState = WalletState()
)

/**
 * Bootstrap action throw when the app passes the splash. It would suspend until all the side effects for the
 * launch of the app will be ready.
 */
@TypedAction
object BootstrapAction : Action


