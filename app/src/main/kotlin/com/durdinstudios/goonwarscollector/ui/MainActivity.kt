package com.durdinstudios.goonwarscollector.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.durdinstudios.goonwarscollector.core.deeplinks.DeepLinkDispatcher
import com.durdinstudios.goonwarscollector.ui.base.GobCollectorApp
import com.minikorp.grove.Grove
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

/**
 * Application's main activity.
 */
class MainActivity : AppCompatActivity(), DIAware {
    override val di by closestDI()

    private val deepLinkDispatcher: DeepLinkDispatcher by instance()

    private val listener = ViewTreeObserver.OnPreDrawListener {
        // If anytime the app get stuck on the splash, this is the reason.
        // The setup should be done in [AppNavGraph].
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(listener)

        // We don't want Compose navigation to do its own navigation stuff on creation, so we set the activity intent
        // to null and keep a copy for our uses
        val activityIntent = intent
        Grove.w { "On create Intent: $activityIntent" }
        intent = null
        setContent {
            GobCollectorApp(deepLinkDispatcher, di) { removeSplashScreen(content) }
        }

        // Process intents from activity to handle cases where app was dead
        processIntent(activityIntent)
    }

    val scope = CoroutineScope(Dispatchers.IO)

    private fun processIntent(intent: Intent?) {
        Grove.w { "On New Intent: $intent" }
        if (intent == null) return
        if (intent.flags and Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY == 0) {
            // Only send deep links if there's actually a deep link
            val deepLink = intent.data

            deepLink?.let {
                // this can be also an appMainScope.launch the interceptors support suspend functions but they are not used atm
                scope.launch {
                    deepLinkDispatcher.processUri(it)
                }
            }
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        processIntent(intent)
    }

    private fun removeSplashScreen(content: View) {
        content.viewTreeObserver.removeOnPreDrawListener(listener)
    }
}
