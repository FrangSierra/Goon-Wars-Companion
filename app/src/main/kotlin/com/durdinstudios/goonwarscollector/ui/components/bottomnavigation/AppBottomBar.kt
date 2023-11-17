package com.durdinstudios.goonwarscollector.ui.components.bottomnavigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.durdinstudios.goonwarscollector.R
import com.durdinstudios.goonwarscollector.core.navigation.AppDestination
import com.durdinstudios.goonwarscollector.ui.base.NavHostControllerComposition
import com.durdinstudios.goonwarscollector.ui.components.LabelBottomActive
import com.durdinstudios.goonwarscollector.ui.components.LabelBottomInactive
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.labelBottomActiveTextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.labelBottomInactiveTextStyle

const val BOTTOM_BAR_HEIGHT = 64

val BottomBarComposition: ProvidableCompositionLocal<List<BottomBarNavigationItem>> =
    compositionLocalOf { error("Missing bottomBar values!") }

@Composable
fun withBottomBarItems(items: List<BottomBarNavigationItem>, content: @Composable () -> Unit) {
    CompositionLocalProvider(BottomBarComposition provides items) { content() }
}

@Preview
@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    cutoutShape: Shape? = null,
    newMessages: Int = 0
) {
    val currentBottomBarItems = BottomBarComposition.current
    if (currentBottomBarItems.isEmpty()) return

    val navHostController = NavHostControllerComposition.current
    val navBackStackEntry = navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0x59000000), Color(0xFF000000)),
                    tileMode = TileMode.Decal
                )
            ),
        elevation = 8.dp,
        backgroundColor = Color.Transparent,
        cutoutShape = cutoutShape
    ) {
        // Inflate each navigation item
        BottomNavigation(
            elevation = 8.dp,
            backgroundColor = Color.Transparent,
            modifier = modifier
                .height(BOTTOM_BAR_HEIGHT.dp)
                .fillMaxWidth(),
        ) {
            currentBottomBarItems.forEach { item ->
                val isSelected = currentRoute == item.destination.getRoutePattern()
                    || item.nestedRoutes.contains(currentRoute)
                val icon = if (isSelected) item.selectedDrawableIcon else item.drawableIcon

                BottomNavigationItem(icon = {
                    Icon(
                        painterResource(id = item.drawableIcon),
                        null,
                        modifier = Modifier.height(28.dp),
                        tint = if (isSelected) labelBottomActiveTextStyle.color else labelBottomInactiveTextStyle.color
                    )
                },
                    label = {
                        if (isSelected) {
                            LabelBottomActive(
                                text = stringResource(item.resourceId),
                                overflow = TextOverflow.Clip,
                                maxLines = 1
                            )
                        } else {
                            LabelBottomInactive(
                                text = stringResource(item.resourceId),
                                overflow = TextOverflow.Clip,
                                maxLines = 1
                            )
                        }
                    },
                    selected = isSelected,
                    onClick = {
                        // Avoid navigation to the same route
                        if (currentRoute == item.destination.getRoutePattern()) return@BottomNavigationItem

                        navHostController.navigate(item.destination.getRoutePattern()) {
                            // Pop up to the Home screen of the graph to avoid building up a large stack of destinations on the back
                            // stack as users select items.
                            // If the home screen is not present in the back stack (for example: deep links or opening from
                            // widget/shortcuts) destroy the whole stack.
                            if (navHostController.backQueue.any { it.destination.route == AppDestination.Home.getRoutePattern() }) {
                                popUpTo(AppDestination.Home.getRoutePattern())
                            } else {
                                popUpTo(0)
                            }

                            // Avoid multiple copies of the same destination when reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    })
            }
        }
    }
}

@Suppress("UndocumentedPublicClass")
sealed class BottomBarNavigationItem(
    val destination: AppDestination,
    val nestedRoutes: List<String>,
    @StringRes val resourceId: Int,
    @DrawableRes val drawableIcon: Int,
    @DrawableRes val selectedDrawableIcon: Int = drawableIcon
) {

    object Collection :
        BottomBarNavigationItem(
            AppDestination.Collection,
            emptyList(),
            R.string.general_collection,
            R.drawable.ic_collection,
            R.drawable.ic_collection
        )

    object Home :
        BottomBarNavigationItem(
            AppDestination.Home,
            emptyList(),
            R.string.general_home,
            R.drawable.logo,
            R.drawable.logo
        )

    object Account :
        BottomBarNavigationItem(
            AppDestination.Account,
            emptyList(),
            R.string.general_profile,
            R.drawable.ic_username,
            R.drawable.ic_username
        )

}

@Suppress("ComplexMethod")
@Composable
fun BottomBarContent(content: @Composable () -> Unit) {
    val bottomBarItems = listOfNotNull(
        BottomBarNavigationItem.Collection,
        BottomBarNavigationItem.Home,
        BottomBarNavigationItem.Account,
    )
    withBottomBarItems(bottomBarItems, content)
}