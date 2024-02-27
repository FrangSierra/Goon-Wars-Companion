@file:OptIn(ExperimentalAnimationApi::class)

package com.durdinstudios.goonwarscollector.core.navigation

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable

@Suppress("UndocumentedPublicClass")
sealed interface ScreenName

@Suppress("UndocumentedPublicClass")
interface NonTraceableScreenName : ScreenName

@Suppress("UndocumentedPublicClass")
object NoScreenName : NonTraceableScreenName

/**
 * Definition of a screen in the app.
 */
@Suppress("UndocumentedPublicFunction", "UndocumentedPublicClass")
sealed class AppDestination(
    private val route: String,
    val screenName: ScreenName = NoScreenName,
    val arguments: List<NamedNavArgument> = emptyList(),
    val deepLinks: List<NavDeepLink> = emptyList()
) {
    companion object {
        val destinations by lazy {
            AppDestination::class.nestedClasses.mapNotNull { it.objectInstance as? AppDestination }
        }
    }

    open fun navigateAndPopStack(
        navController: NavHostController,
        arguments: Map<String, Any> = emptyMap()
    ) {
        this.navigate(navController, arguments) {
            popUpTo(0)
        }
    }

    open fun navigate(
        navController: NavHostController,
        arguments: Map<String, Any> = emptyMap(),
        builder: NavOptionsBuilder.() -> Unit = {}
    ) {
        val route = Uri.Builder()
            .appendPath(route)
            .apply {
                arguments.forEach { (key, value) ->
                    appendQueryParameter(key, value.toString())
                }
            }
            .build().toString().removePrefix("/")

        navController.navigate(route, builder)
    }

    object NavigateUp : AppDestination(route = "", screenName = NoScreenName) {
        override fun navigate(
            navController: NavHostController,
            arguments: Map<String, Any>,
            builder: NavOptionsBuilder.() -> Unit
        ) {
            navController.popBackStack()
        }
    }

    object Debug : AppDestination(route = "debug")
    object Splash : AppDestination(route = "splash")
    object Login : AppDestination(route = "login")
    object SignIn : AppDestination(route = "register")
    object ForgetPassword : AppDestination(route = "forgetPassword")
    object HomeRoot : AppDestination(route = "home_root")
    object Home : AppDestination(route = "home")
    object Profile : AppDestination(route = "profile")
    object Account : AppDestination(route = "account")
    object Settings : AppDestination(route = "settings")
    object Collection : AppDestination(route = "collection")

    fun getRoutePattern() = Uri.Builder()
        .appendPath(route)
        .apply {
            arguments.forEach { navArgument ->
                appendQueryParameter(navArgument.name, "{${navArgument.name}}")
            }
        }
        .build().toString().removePrefix("/")
}

/**
 * Same as [composable], but it receives an [AppDestination] instead of a single route.
 */
@Suppress("LongParameterList")
fun NavGraphBuilder.appComposable(
    destination: AppDestination,
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.getRoutePattern(),
        arguments = destination.arguments,
        deepLinks = destination.deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = content
    )
}
