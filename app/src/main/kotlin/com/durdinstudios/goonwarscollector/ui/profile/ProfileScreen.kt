package com.durdinstudios.goonwarscollector.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.durdinstudios.goonwarscollector.R
import com.durdinstudios.goonwarscollector.core.FakeData
import com.durdinstudios.goonwarscollector.core.arch.Resource
import com.durdinstudios.goonwarscollector.core.arch.SwipeToRefreshPlaceholderContent
import com.durdinstudios.goonwarscollector.core.arch.rememberSelector
import com.durdinstudios.goonwarscollector.core.arch.useStore
import com.durdinstudios.goonwarscollector.domain.wallet.GetWalletNfts
import com.durdinstudios.goonwarscollector.domain.wallet.Nft
import com.durdinstudios.goonwarscollector.domain.wallet.TrackWalletAction
import com.durdinstudios.goonwarscollector.domain.wallet.userWallets
import com.durdinstudios.goonwarscollector.domain.wallet.walletsNftsSelector
import com.durdinstudios.goonwarscollector.ui.components.AppPreview
import com.durdinstudios.goonwarscollector.ui.components.AppScaffold
import com.durdinstudios.goonwarscollector.ui.components.BodyMediumEmphasisLeft
import com.durdinstudios.goonwarscollector.ui.components.BodySmallMediumEmphasisCenter
import com.durdinstudios.goonwarscollector.ui.components.NftCard
import com.durdinstudios.goonwarscollector.ui.components.Heading3
import com.durdinstudios.goonwarscollector.ui.components.Heading4
import com.durdinstudios.goonwarscollector.ui.components.Heading5
import com.durdinstudios.goonwarscollector.ui.components.HorizontalList
import com.durdinstudios.goonwarscollector.ui.components.SettingsIconButton
import com.durdinstudios.goonwarscollector.ui.components.bottomnavigation.AppBottomBar
import com.durdinstudios.goonwarscollector.ui.components.images.GlideAppImage
import com.durdinstudios.goonwarscollector.ui.components.placeholders.withPlaceholder
import com.durdinstudios.goonwarscollector.ui.theme.Colors
import com.durdinstudios.goonwarscollector.ui.components.dialogs.base.MaterialDialog
import com.durdinstudios.goonwarscollector.ui.components.dialogs.base.MaterialDialogState
import com.efimeral.continentalapp.ui.components.dialogs.base.input
import com.durdinstudios.goonwarscollector.ui.components.dialogs.base.rememberMaterialDialogState
import com.efimeral.continentalapp.ui.components.dialogs.base.title
import com.skydoves.landscapist.ImageOptions
import kotlinx.coroutines.launch

typealias GoonNfts = Pair<List<Nft>, List<Nft>>

@Composable
fun ProfileScreen(
    onBack: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val store = useStore()

    val wallets = rememberSelector(selector = userWallets)
    val nfts = rememberSelector(selector = walletsNftsSelector)
    val scope = rememberCoroutineScope()

    val onRefresh: () -> Unit = {
        scope.launch {
            if (wallets.isNotEmpty()) store.dispatch(GetWalletNfts.Request(wallets))
        }
    }

    LaunchedEffect(Unit) {
        onRefresh()
    }

    EditProfileScreen(
        onBack = onBack,
        onRefresh = onRefresh,
        nfts = nfts,
        wallets = wallets,
        onAddWalletClick = {
            scope.launch {
                store.dispatch(TrackWalletAction.Request(listOf(it)))
            }
        },
        onSettingsClick = onSettingsClick,
        scaffoldState = scaffoldState
    )
}

@Composable
private fun EditProfileScreen(
    onBack: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    nfts: Resource<GoonNfts>,
    wallets: List<String>, onAddWalletClick: (String) -> Unit = {},
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    AppScaffold(
        scaffoldState = scaffoldState
    ) {
        Box(Modifier.fillMaxSize(), Alignment.BottomCenter) {
            SwipeToRefreshPlaceholderContent(
                modifier = Modifier
                    .fillMaxSize(),
                resource = nfts,
                placeholderValue = FakeData.Profile.nfts to FakeData.Profile.nfts,
                onRefresh = onRefresh
            ) {
                ProfileContent(it, wallets, onAddWalletClick, onSettingsClick)
            }
            AppBottomBar()
        }
    }
}

@Composable
private fun ProfileContent(
    nfts: GoonNfts, wallets: List<String>,
    onAddWalletClick: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    val scrollState = rememberScrollState(0)
    val phoneNumberDialog = rememberMaterialDialogState()

    WalletDialog(phoneNumberDialog) {
        onAddWalletClick(it)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 36.dp, bottom = 68.dp)
            .verticalScroll(state = scrollState)
    ) {
        Row(modifier = Modifier
            .clickable { onSettingsClick() }
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            SettingsIconButton(
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
            BodyMediumEmphasisLeft(
                text = "Settings",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )
            SettingsIconButton(tint = Color.White)
        }
        WalletList(wallets) { phoneNumberDialog.show() }
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Heading3(text = "My Goons")
            if (nfts.first.isNotEmpty()) Heading4(text = "See all")
        }
        if (nfts.first.isEmpty()) EmptyNfts()
        HorizontalList(
            items = nfts.first,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            NftCard(name = it.name, imageUrl = it.imageUrl, size = 128)
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Heading3(text = "My Bods")
            if (nfts.second.isNotEmpty()) Heading4(text = "See all")
        }

        if (nfts.second.isEmpty()) EmptyNfts()
        HorizontalList(
            items = nfts.second,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            WorldOfBalatroonCard(it)
        }
    }
}

@Composable
private fun EmptyNfts() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 16.dp)
            .border(1.dp, Colors.appColorPalette.greyMedium, shape = RoundedCornerShape(8.dp))
            .padding(vertical = 32.dp, horizontal = 16.dp),

        contentAlignment = Alignment.Center
    ) {
        Heading5(text = "No NFTs were found")
    }
}

@Composable
private fun WorldOfBalatroonCard(nft: Nft) {
    Card(
        modifier = Modifier.withPlaceholder(),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Colors.appColorPalette.surface,
        border = BorderStroke(1.dp, Colors.appColorPalette.greyMedium)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .width(128.dp)
                .height(256.dp)
        ) {
            GlideAppImage(
                url = nft.imageUrl,
                contentDescription = null,
                imageOptions = ImageOptions(contentScale = ContentScale.Fit),
                modifier = Modifier.height(232.dp)
            )
            BodySmallMediumEmphasisCenter(text = nft.name)
        }
    }
}

@Composable
fun ClickableValueSetting(
    valueModifier: Modifier = Modifier,
    key: String,
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
        BodyMediumEmphasisLeft(text = key)
        value?.let { BodyMediumEmphasisLeft(text = it.toString(), modifier = valueModifier) }
    }
}

@Composable
private fun WalletDialog(
    dialogState: MaterialDialogState = rememberMaterialDialogState(),
    onConfirm: (String) -> Unit
) {
    MaterialDialog(
        dialogState = dialogState,
        buttons = { positiveButton(text = "Confirm") },
    ) {
        title(text = "New wallet")
        input(
            label = "wallet address",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
        ) { onConfirm(it) }
    }
}

@Preview
@Composable
private fun ProfileLoadingPreview() {
    AppPreview(isLoading = true) {
        Box {
            EditProfileScreen(
                nfts = Resource.success(FakeData.Profile.nfts to FakeData.Profile.nfts),
                wallets = listOf("1", "2", "3")
            )
        }
    }
}

@Preview
@Composable
private fun ProfilePreview() {
    AppPreview {
        Box {
            EditProfileScreen(
                nfts = Resource.success(FakeData.Profile.nfts to FakeData.Profile.nfts),
                wallets = listOf("1", "2", "3")
            )
        }
    }
}