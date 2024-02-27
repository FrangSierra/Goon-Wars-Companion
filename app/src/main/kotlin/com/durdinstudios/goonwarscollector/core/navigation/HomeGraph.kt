package com.durdinstudios.goonwarscollector.core.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.durdinstudios.goonwarscollector.core.error.ErrorHandler
import com.durdinstudios.goonwarscollector.ui.account.WelcomeScreen
import com.durdinstudios.goonwarscollector.ui.collection.CollectionScreen
import com.durdinstudios.goonwarscollector.ui.home.HomeScreen
import com.durdinstudios.goonwarscollector.ui.profile.ProfileScreen
import com.durdinstudios.goonwarscollector.ui.settings.SettingsScreen

const val HOME_GRAPH_ROUTE = "home_graph"

@OptIn(ExperimentalAnimationApi::class)
@SuppressWarnings("LongMethod", "FunctionNaming")
fun NavGraphBuilder.HomeGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    errorHandler: ErrorHandler
) {
    navigation(
        startDestination = AppDestination.Home.getRoutePattern(),
        route = HOME_GRAPH_ROUTE
    ) {
        // Home root
        //appComposable(AppDestination.HomeRoot) {
        //    HomeScreen()
        //}

        appComposable(AppDestination.Home) {
            HomeScreen(scaffoldState)
        }

        appComposable(AppDestination.Collection) {
            CollectionScreen(scaffoldState)
        }

        appComposable(AppDestination.Account) {
            ProfileScreen(
                scaffoldState = scaffoldState,
                onSettingsClick = { AppDestination.Settings.navigate(navController) })
        }
        appComposable(AppDestination.Settings) {
            SettingsScreen(scaffoldState = scaffoldState)
        }
    }
}