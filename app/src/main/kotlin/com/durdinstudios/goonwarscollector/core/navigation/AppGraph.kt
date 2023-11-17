package com.durdinstudios.goonwarscollector.core.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination

const val EMPTY_GRAPH_ROUTE = "none"

/**
 * Description of the graphs used in the app.
 */
@Suppress("UndocumentedPublicClass")
sealed class AppGraph(val rootPath: String) {
    companion object {
        private val staticGraphMap: Map<String, AppGraph> by lazy {
            // This doesn't work likely in Kotlin 1.8.10 with R8 running. Review some other day.
            /*
            AppGraph::class.nestedClasses
                .filter { it.isSubclassOf(AppGraph::class) }
                .map { klass -> klass.objectInstance }
                .filterIsInstance<AppGraph>()
                .associateBy { value -> value.rootPath }

             */
            mapOf(
                HOME_GRAPH_ROUTE to Home,
                ONBOARDING_GRAPH_ROUTE to Onboarding,
                EMPTY_GRAPH_ROUTE to None
            )
        }

        /**
         * Obtains the graph linked to an Uri.
         */
        fun fromPendingUri(navController: NavController, uri: Uri?): AppGraph {
            if (uri == null || !navController.graph.hasDeepLink(uri)) return None

            val match = navController.graph.matchDeepLink(NavDeepLinkRequest.Builder.fromUri(uri).build())
            val destination = match!!.destination
            return fromDestination(destination)
        }

        /**
         * Obtains the graph linked to a [NavDestination].
         */
        fun fromDestination(destination: NavDestination): AppGraph {
            val deeplinkGraphRoot = destination.parent?.route
            return deeplinkGraphRoot?.let { staticGraphMap.getValue(it) } ?: None
        }
    }

    object Home : AppGraph(HOME_GRAPH_ROUTE)
    object Onboarding : AppGraph(ONBOARDING_GRAPH_ROUTE)
    object None : AppGraph(EMPTY_GRAPH_ROUTE)
}