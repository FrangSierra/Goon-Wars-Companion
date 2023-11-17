package com.durdinstudios.goonwarscollector.core.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.durdinstudios.goonwarscollector.core.error.ErrorHandler

const val ONBOARDING_GRAPH_ROUTE = "onboarding_graph"

@OptIn(ExperimentalAnimationApi::class)
@SuppressWarnings("LongMethod", "FunctionNaming")
fun NavGraphBuilder.OnboardingGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    errorHandler: ErrorHandler
) {
    navigation(
        startDestination = AppDestination.Home.getRoutePattern(),
        route = HOME_GRAPH_ROUTE
    ) {
        // Home root
        appComposable(AppDestination.Home) {

        }
    }
}