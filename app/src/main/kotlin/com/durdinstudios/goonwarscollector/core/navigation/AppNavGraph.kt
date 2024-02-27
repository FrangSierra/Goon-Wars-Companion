package com.durdinstudios.goonwarscollector.core.navigation

import androidx.compose.animation.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.durdinstudios.goonwarscollector.core.deeplinks.DeepLinkDispatcher
import com.durdinstudios.goonwarscollector.core.error.ErrorHandler
import com.durdinstudios.goonwarscollector.domain.persistence.PersistenceController
import com.durdinstudios.goonwarscollector.ui.account.WelcomeScreen
import com.durdinstudios.goonwarscollector.ui.splash.SplashScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import org.kodein.di.compose.rememberInstance

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    deepLinkDispatcher: DeepLinkDispatcher,
    startDestination: String = AppDestination.Splash.getRoutePattern(),
    removeSystemSplash: (clearDispatcher: Boolean) -> Unit
) {
    val context = LocalContext.current
    val errorHandler: ErrorHandler by rememberInstance()
    val coroutineScope = rememberCoroutineScope()

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        appComposable(AppDestination.Debug) {
            // DebugScreen()
        }

        appComposable(AppDestination.Splash) {
            SplashScreen(
                navigateToHome = { AppDestination.Home.navigateAndPopStack(navController); removeSystemSplash(false) },
                navigateToOnBoarding = {
                    AppDestination.Home.navigateAndPopStack(navController); removeSystemSplash(false)
                }
            )
        }

        appComposable(AppDestination.Login) {
            //TODO implement if we need auth
        }

        appComposable(AppDestination.ForgetPassword) {
            //TODO implement if we need auth
        }

        appComposable(AppDestination.SignIn) {
            //TODO implement if we need auth
        }

        HomeGraph(navController, scaffoldState, errorHandler)
    }
}