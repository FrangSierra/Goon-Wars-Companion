package com.durdinstudios.goonwarscollector.ui.settings

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.durdinstudios.goonwarscollector.R
import com.durdinstudios.goonwarscollector.ui.components.AppPreview
import com.durdinstudios.goonwarscollector.ui.components.AppScaffold
import com.durdinstudios.goonwarscollector.ui.components.BodyMediumEmphasisLeft
import com.durdinstudios.goonwarscollector.ui.components.Heading4
import com.durdinstudios.goonwarscollector.ui.components.SingleTextAppTopBar
import com.durdinstudios.goonwarscollector.ui.components.placeholders.withPlaceholder
import com.durdinstudios.goonwarscollector.ui.components.spacers.VerticalDivider
import com.durdinstudios.goonwarscollector.ui.components.spacers.VerticalSpacer
import com.durdinstudios.goonwarscollector.utils.openUrl

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    navigateToLogin: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    SettingsScreen(scaffoldState, onBack, navigateToLogin)
}

@Composable
fun SettingsScreen(
    scaffoldState: ScaffoldState,
    onBack: () -> Unit = {},
    onLogout: () -> Unit = {},
) {
    AppScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SingleTextAppTopBar(
                title = "Settings",
                //backgroundColor = LocalAppTheme.current.palette.topAppBar,
                //contentColor = LocalAppTheme.current.palette.topAppBarContent,
                onBack = onBack
            )
        }
    ) {
        SettingsScreenContent(onLogoutClick = onLogout)
    }
}

@Composable
private fun SettingsScreenContent(
    onLogoutClick: () -> Unit,
) {
    val context = LocalContext.current
    Column(Modifier.fillMaxSize()) {
        Heading4(
            text = "Whats Goon-ing on", modifier = Modifier
                .padding(16.dp)
                .withPlaceholder()
        )
        ClickableValueSetting(key = "Visit Balatroon", icon = R.drawable.logo, value = null, onValueClick = {
            openUrl(
                context = context,
                url = Uri.parse("https://goonsofbalatroon.com/")
            )
        })
        ClickableValueSetting(key = "Join Discord", value = null, icon = R.drawable.discord_logo, onValueClick = {
            openUrl(
                context = context,
                url = Uri.parse("https://discord.com/invite/goonsnft")
            )
        })
        ClickableValueSetting(key = "Check Twitter", value = null, icon = R.drawable.x_logo_24, onValueClick = {
            openUrl(
                context = context,
                url = Uri.parse(" https://twitter.com/LFGoons")
            )
        })

        SettingsDivider()
        Heading4(
            text = "Opensea", modifier = Modifier
                .padding(16.dp)
                .withPlaceholder()
        )
        ClickableValueSetting(key = "World of Balatroon", value = null, onValueClick = {
            openUrl(
                context = context,
                url = Uri.parse("https://opensea.io/collection/balatroon")
            )
        })
        ClickableValueSetting(key = "Goons", value = null, onValueClick = {
            openUrl(
                context = context,
                url = Uri.parse("https://opensea.io/collection/goonsofbalatroon")
            )
        })
        ClickableValueSetting(key = "Bods", value = null, onValueClick = {
            openUrl(
                context = context,
                url = Uri.parse("https://opensea.io/collection/goonbods")
            )
        })
        SettingsDivider()
        Heading4(
            text = "Sphere", modifier = Modifier
                .padding(16.dp)
                .withPlaceholder()
        )
        ClickableValueSetting(key = "Goon Card Packs", value = null, onValueClick = {
            openUrl(
                context = context,
                url = Uri.parse("https://sphere.market/beam/collection/0x459781868175a703aa403cc1305a5293a7d253a6")
            )
        })
        ClickableValueSetting(key = "Goon Cards", value = null, onValueClick = {
            openUrl(
                context = context,
                url = Uri.parse("https://sphere.market/beam/collection/0xc889c965336d839fb2e91dcaf87abf40b7856db1")
            )
        })
        SettingsLogoFooter()
    }
}

@Composable
fun ClickableValueSetting(
    valueModifier: Modifier = Modifier,
    key: String,
    @DrawableRes icon: Int? = null,
    value: Any?,
    onValueClick: (() -> Unit)? = null
) {
    Row(
        Modifier
            .height(40.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .then(if (onValueClick != null) Modifier.clickable { onValueClick() } else Modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            icon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.White.copy(alpha = 0.3F)
                )
            }
            BodyMediumEmphasisLeft(text = key, modifier = Modifier.withPlaceholder())
        }
        value?.let {
            BodyMediumEmphasisLeft(
                text = it.toString(),
                //color = Color.White.copy(alpha = 0.5F),
                modifier = valueModifier.withPlaceholder()
            )
        }
    }
}

@Composable
fun SettingsLogoFooter() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp), contentAlignment = Alignment.Center
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            SettingsDivider()
            VerticalSpacer(height = 20.dp)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.3F))
            )
        }
    }
}

@Composable
fun SettingsDivider() {
    VerticalDivider(Modifier.padding(vertical = 12.dp))
}

@Preview
@Composable
fun SettingsScreenPreview() = AppPreview {
    SettingsScreenContent(onLogoutClick = { })
}