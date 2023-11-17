package com.durdinstudios.goonwarscollector.ui.base

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.durdinstudios.goonwarscollector.core.arch.AppStore
import com.durdinstudios.goonwarscollector.core.arch.withStore
import com.durdinstudios.goonwarscollector.core.deeplinks.DeepLinkDispatcher
import com.durdinstudios.goonwarscollector.core.navigation.AppDestination
import com.durdinstudios.goonwarscollector.core.navigation.AppGraph
import com.durdinstudios.goonwarscollector.core.navigation.AppNavGraph
import com.durdinstudios.goonwarscollector.extensions.findActivity
import com.durdinstudios.goonwarscollector.ui.components.AppScaffold
import com.durdinstudios.goonwarscollector.ui.components.bottomnavigation.BottomBarContent
import com.durdinstudios.goonwarscollector.ui.components.bottomnavigation.BottomBarNavigationItem
import com.durdinstudios.goonwarscollector.ui.theme.AppMaterialTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.minikorp.grove.Grove
import org.kodein.di.DI
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI

val BottomBarComposition: ProvidableCompositionLocal<List<BottomBarNavigationItem>> =
    compositionLocalOf { error("Missing bottomBar values!") }

val NavHostControllerComposition: ProvidableCompositionLocal<NavHostController> =
    compositionLocalOf { error("Missing Nav Host!") }

@Composable
fun withNavHost(navHostControllerComposition: NavHostController, content: @Composable () -> Unit) {
    CompositionLocalProvider(NavHostControllerComposition provides navHostControllerComposition) { content() }
}

@Composable
fun withBottomBarItems(items: List<BottomBarNavigationItem>, content: @Composable () -> Unit) {
    CompositionLocalProvider(BottomBarComposition provides items) { content() }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GobCollectorApp(deepLinkDispatcher: DeepLinkDispatcher, di: DI, removeSystemSplash: () -> Unit) =
    withDI(di = di) {
        val store: AppStore by rememberInstance()
        withStore(store) {
            AppMaterialTheme {
                val context = LocalContext.current
                val scaffoldState = rememberScaffoldState()
                val navController = rememberAnimatedNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val pendingDeeplinkUri = deepLinkDispatcher.getLiveData().observeAsState()

                // Setup Deeplink Dispatcher if needed

                LaunchedEffect(pendingDeeplinkUri.value) { // Avoid multiple executions using the intent as key
                    pendingDeeplinkUri.value?.let { uri ->
                        context.findActivity()?.let { activity -> // Avoid navigation when app has died
                            Grove.w { "On launched Intent: $uri" }
                            if (navController.graph.hasDeepLink(uri)) {
                                val match =
                                    navController.graph.matchDeepLink(NavDeepLinkRequest.Builder.fromUri(uri).build())
                                val destination = match!!.destination
                                val destinationGraph = AppGraph.fromDestination(destination)

                                val hasSession = true //todo check session

                                if (destinationGraph is AppGraph.Home && !hasSession) return@LaunchedEffect

                                val shouldPopUpFullStack =
                                    navController.currentBackStackEntry?.destination?.parent != destination.parent

                                // Navigate while removing any prior screen if needed
                                navController.navigate(uri,
                                    navOptions {
                                        if (shouldPopUpFullStack) {
                                            popUpTo(0) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                )

                                // Clear the liveData after navigation was done in case we need to trigger the same value again.
                                deepLinkDispatcher.clearDeeplinkLiveData()
                            }
                        }
                    }
                }

                // Finish activity if we perform back gesture and splash is the only screen left
                val shouldEnableBackHandler = navBackStackEntry?.let { _ ->
                    !navController.backQueue.any { it.destination.route != AppDestination.Splash.getRoutePattern() }
                } ?: false

                BackHandler(enabled = shouldEnableBackHandler) {
                    context.findActivity()?.finish()
                }

                withNavHost(navController) {
                    BottomBarContent {
                        AppScaffold(scaffoldState = scaffoldState) { _ ->
                            AppNavGraph(
                                navController = navController,
                                scaffoldState = scaffoldState,
                                deepLinkDispatcher = deepLinkDispatcher,
                                removeSystemSplash = { clearDeeplinkDispatcher ->
                                    removeSystemSplash()
                                    Grove.d { "[DeepLinkManagement] remove splash" }
                                    if (clearDeeplinkDispatcher) deepLinkDispatcher.clear()
                                    deepLinkDispatcher.initialize()
                                })
                        }
                    }
                }
            }
        }
    }