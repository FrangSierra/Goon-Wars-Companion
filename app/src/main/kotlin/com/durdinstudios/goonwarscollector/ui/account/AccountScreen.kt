package com.durdinstudios.goonwarscollector.ui.account

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.durdinstudios.goonwarscollector.R
import com.durdinstudios.goonwarscollector.core.arch.rememberSelector
import com.durdinstudios.goonwarscollector.core.arch.useStore
import com.durdinstudios.goonwarscollector.domain.wallet.TrackWalletAction
import com.durdinstudios.goonwarscollector.domain.wallet.userCardsSelector
import com.durdinstudios.goonwarscollector.domain.wallet.userWallets
import com.durdinstudios.goonwarscollector.ui.components.AppImage
import com.durdinstudios.goonwarscollector.ui.components.AppPreview
import com.durdinstudios.goonwarscollector.ui.components.AppScaffold
import com.durdinstudios.goonwarscollector.ui.components.BodySmallMediumEmphasisCenter
import com.durdinstudios.goonwarscollector.ui.components.Heading4
import com.durdinstudios.goonwarscollector.ui.components.Link
import com.durdinstudios.goonwarscollector.ui.components.bottomnavigation.AppBottomBar
import com.durdinstudios.goonwarscollector.ui.components.buttons.ButtonOutlineDefaultLarge
import com.durdinstudios.goonwarscollector.ui.components.buttons.ButtonSolidSecondaryLarge
import com.durdinstudios.goonwarscollector.ui.components.spacers.VerticalSpacer
import com.durdinstudios.goonwarscollector.ui.components.textfields.WalletAddressState
import com.durdinstudios.goonwarscollector.ui.components.textfields.WalletTextField
import com.durdinstudios.goonwarscollector.ui.theme.Colors
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val scope = rememberCoroutineScope()
    val store = useStore()
    val context = LocalContext.current

    val onWalletsDone: ((List<String>) -> Unit) = {
        scope.launch {
            store.dispatch(TrackWalletAction.Request(it))
            Toast.makeText(context, "Wallets added", Toast.LENGTH_SHORT).show()
        }
    }

    WelcomeScreenContent(scaffoldState, onWalletsDone)

}

@Composable
fun WelcomeScreenContent(
    scaffoldState: ScaffoldState,
    onWalletsDone: (List<String>) -> Unit = {}
) {
    AppScaffold(scaffoldState = scaffoldState, bottomBar = { AppBottomBar() }) {
        WelcomeContent(onWalletsDone)
    }
}

@Preview
@Composable
fun WelcomeContent(onWalletsDone: (List<String>) -> Unit = {}) {
    AppPreview {
        val linkedWallets = remember { mutableStateOf(listOf<String>()) }
        val walletState = remember { WalletAddressState() }
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 40.dp)
                .background(Colors.appColorPalette.primaryBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppImage(resId = R.drawable.gon_placeholder, contentDescription = null, modifier = Modifier.size(200.dp))
            VerticalSpacer(height = 16.dp)
            Heading4(text = "Welcome to your $${"GOB"} Companion!")
            BodySmallMediumEmphasisCenter(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Track easily your Goon Wars collection and GOB NFTs. Check stats on the market, last medium posts, all the info related with Goons of Balatron on a single place"
            )
            VerticalSpacer(height = 24.dp)
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(1F),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = CenterVertically
            ) {
                WalletTextField(
                    label = "Wallet Address",
                    modifier = Modifier.weight(0.8F),
                    walletState = walletState
                )
                ButtonOutlineDefaultLarge(
                    text = "Add",
                    modifier = Modifier.weight(0.2F),
                    enabled = walletState.text.isNotEmpty(),
                    onClick = {
                        linkedWallets.value = linkedWallets.value.plus(walletState.text).distinct()
                    },
                )
            }
            VerticalSpacer(height = 8.dp)
            linkedWallets.value.forEach {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = CenterVertically
                ) {
                    Icon(Icons.Default.Clear, null, tint = Colors.appColorPalette.onPrimary)
                    BodySmallMediumEmphasisCenter(text = it)
                }
            }
            VerticalSpacer(height = 24.dp)
            ButtonSolidSecondaryLarge(
                enabled = linkedWallets.value.isNotEmpty(),
                text = "Track Wallets", modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = { onWalletsDone(linkedWallets.value) })
        }
    }
}