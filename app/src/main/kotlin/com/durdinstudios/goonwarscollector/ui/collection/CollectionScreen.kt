package com.durdinstudios.goonwarscollector.ui.collection

import androidx.annotation.Keep
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.LeadingIconTab
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.durdinstudios.goonwarscollector.R
import com.durdinstudios.goonwarscollector.core.arch.Resource
import com.durdinstudios.goonwarscollector.core.arch.SwipeToRefreshPlaceholderContent
import com.durdinstudios.goonwarscollector.core.arch.rememberSelector
import com.durdinstudios.goonwarscollector.core.arch.useStore
import com.durdinstudios.goonwarscollector.domain.wallet.GetGobCardsAction
import com.durdinstudios.goonwarscollector.domain.wallet.GobCard
import com.durdinstudios.goonwarscollector.domain.wallet.cardsSelector
import com.durdinstudios.goonwarscollector.domain.wallet.userCardsSelector
import com.durdinstudios.goonwarscollector.ui.components.AppImage
import com.durdinstudios.goonwarscollector.ui.components.AppPreview
import com.durdinstudios.goonwarscollector.ui.components.AppScaffold
import com.durdinstudios.goonwarscollector.ui.components.BodyMediumEmphasisCenter
import com.durdinstudios.goonwarscollector.ui.components.BodyMediumEmphasisLeft
import com.durdinstudios.goonwarscollector.ui.components.BodySmallMediumEmphasisCenter
import com.durdinstudios.goonwarscollector.ui.components.Heading4
import com.durdinstudios.goonwarscollector.ui.components.bottomnavigation.AppBottomBar
import com.durdinstudios.goonwarscollector.ui.components.pagerindicators.pagerTabIndicatorOffset
import com.durdinstudios.goonwarscollector.ui.components.spacers.HorizontalSpacer
import com.durdinstudios.goonwarscollector.ui.components.spacers.VerticalSpacer
import com.durdinstudios.goonwarscollector.ui.theme.Colors
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@Composable
fun CollectionScreen(scaffoldState: ScaffoldState = rememberScaffoldState()) {
    val scope = rememberCoroutineScope()
    val store = useStore()
    val context = LocalContext.current
    val selector = rememberSelector(selector = userCardsSelector)
    val gobCards = rememberSelector(selector = cardsSelector)

    LaunchedEffect(Unit) {
        if (gobCards.getOrNull()?.isEmpty() == true) {
            store.dispatch(GetGobCardsAction.Request)
        }
    }

    CollectionScreen(selector, scaffoldState)
}

@Composable
fun CollectionScreen(
    userLoggedSelector: Resource<List<CollectionCardItem>>,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    AppScaffold(scaffoldState = scaffoldState,
        bottomBar = { AppBottomBar() }) {
        SwipeToRefreshPlaceholderContent(resource = userLoggedSelector, placeholderValue = emptyList()) {
            CollectionContent(it)
        }
    }
}

@Composable
fun CollectionContent(collectionCardItems: List<CollectionCardItem>) {
    val pagerState = rememberPagerState(0)
    val selectedTabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()
    val ownedRegular = remember {
        derivedStateOf {
            collectionCardItems.filter { it.ownedAmount != 0 }.size
        }
    }
    val ownedShiny = remember {
        derivedStateOf {
            collectionCardItems.filter { it.ownedShinyAmount > 0 }.size
        }
    }

    Column {
        CollectionHeader(ownedRegular.value, ownedShiny.value)
        // Wrap the TabRow inside an elevated Surface (zIndex is needed to display the shadow above the Pager)
        Surface(
            modifier = Modifier.zIndex(2.0f),
            elevation = 4.dp
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(
                            pagerState,
                            tabPositions
                        ),
                        color = Colors.appColorPalette.greyMedium
                    )
                },
                backgroundColor = Colors.appColorPalette.primaryDark
            ) {
                LeadingIconTab(
                    selected = selectedTabIndex == 0,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
                    text = {
                        Heading4(text = "Grid View")
                    },
                    icon = { }
                )
                LeadingIconTab(
                    selected = selectedTabIndex == 1,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    },
                    text = {
                        Heading4(text = "List View")
                    },
                    icon = { }
                )
            }
        }
        HorizontalPager(state = pagerState, pageCount = 2) { tabIndex ->
            if (tabIndex == 0) {
                LazyVerticalGrid(modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(color = Colors.appColorPalette.primaryBackground),
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    content = {
                        items(items = collectionCardItems, key = { it.card.id }) {
                            CollectionGridCardItem(it)
                        }
                    })
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .background(color = Colors.appColorPalette.primaryBackground),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = collectionCardItems, key = { it.card.id }) {
                        CollectionListCardItem(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun CollectionHeader(ownedCards: Int, shinyCards: Int) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Colors.appColorPalette.primary)
            .padding(top = 24.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        AppImage(
            resId = R.drawable.gob_logo,
            contentDescription = null,
            modifier = Modifier.height(64.dp),
            contentScale = ContentScale.Fit
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .background(Colors.appColorPalette.primaryDark),
        ) {
            BodySmallMediumEmphasisCenter(
                text = "Regular $ownedCards/310", modifier = Modifier
                    .padding(4.dp)
                    .weight(1F)
            )
            BodySmallMediumEmphasisCenter(
                text = "Shiny $shinyCards/310", modifier = Modifier
                    .padding(4.dp)
                    .weight(1F)
            )
        }
        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp), color = Colors.appColorPalette.greyMedium
        )
    }

}

@Composable
fun CollectionListCardItem(card: CollectionCardItem) {
    val ownedCard = remember { derivedStateOf { card.ownedAmount != 0 || card.ownedShinyAmount != 0 } }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (LocalInspectionMode.current) {
                GobCardListPlaceholder()
            } else {
                GlideImage(
                    modifier = Modifier
                        .height(64.dp)
                        .alpha(if (ownedCard.value) 1F else 0.4F),
                    imageModel = { card.card.regularNftUrl },
                    requestBuilder = {
                        Glide.with(LocalContext.current)
                            .asDrawable()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .placeholder(R.drawable.logo)
                    },
                    imageOptions = ImageOptions(
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit
                    ), loading = {
                        GobCardListPlaceholder()
                    }
                )
            }
            HorizontalSpacer(width = 8.dp)
            BodyMediumEmphasisLeft(text = card.card.name)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (card.ownedShinyAmount > 0) {
                BodyMediumEmphasisCenter(
                    modifier = Modifier.drawBehind {
                        drawCircle(Colors.appColorPalette.warning, radius = this.size.maxDimension / 1.75F)
                    }, text = card.ownedShinyAmount.toString(),
                    color = Colors.appColorPalette.primary
                )
            }
            if (card.ownedAmount != -1) {
                BodyMediumEmphasisCenter(
                    modifier = Modifier.drawBehind {
                        drawCircle(Colors.appColorPalette.secondary, radius = this.size.maxDimension / 1.75F)
                    }, text = card.ownedAmount.toString(),
                    color = Colors.appColorPalette.primary
                )
            }
        }
    }
}

@Composable
fun CollectionGridCardItem(card: CollectionCardItem) {

    val ownedCard = remember { derivedStateOf { card.ownedAmount != 0 || card.ownedShinyAmount != 0 } }
    Box(contentAlignment = Alignment.BottomCenter) {
        if (LocalInspectionMode.current) {
            GobCardGridPlaceholder()
        } else {
            GlideImage(
                modifier = Modifier
                    .height(200.dp)
                    .alpha(if (ownedCard.value) 1F else 0.4F),
                imageModel = { card.card.regularNftUrl },
                requestBuilder = {
                    Glide.with(LocalContext.current)
                        .asDrawable()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(R.drawable.logo)
                },
                imageOptions = ImageOptions(
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                ), loading = {
                    Column {
                        VerticalSpacer(height = 16.dp)
                        GobCardGridPlaceholder()
                    }
                }
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            if (card.ownedShinyAmount > 0) {
                BodyMediumEmphasisCenter(
                    modifier = Modifier.drawBehind {
                        drawCircle(Colors.appColorPalette.warning, radius = this.size.maxDimension / 1.75F)
                    }, text = card.ownedShinyAmount.toString(),
                    color = Colors.appColorPalette.primary
                )
            } else {
                Box {}
            }
            if (card.ownedAmount != -1) {
                BodyMediumEmphasisCenter(
                    modifier = Modifier.drawBehind {
                        drawCircle(Colors.appColorPalette.secondary, radius = this.size.maxDimension / 1.75F)
                    }, text = card.ownedAmount.toString(),
                    color = Colors.appColorPalette.primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun PriceCard(
    token: String = "GOB",
    logoUrl: Any = "",
    price: Double = 0.003122
) {
    Card(
        modifier = Modifier
            .padding(12.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Colors.appColorPalette.surface
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppImage(resId = R.drawable.gob_logo, contentDescription = null, modifier = Modifier.size(40.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            ) {
                Column {
                    Heading4(text = token)
                    BodyMediumEmphasisLeft(text = "Arbitrum")
                }
                BodyMediumEmphasisCenter(text = price.toString())
            }
        }
    }

}

@Composable
private fun GobCardGridPlaceholder() {
    Box(
        Modifier
            .height(200.dp)
            .width(120.dp)
            .background(Color(0xFF1d1b21))
            .border(1.dp, Colors.appColorPalette.surface), Alignment.Center
    ) {
        AppImage(
            resId = R.drawable.logo,
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun GobCardListPlaceholder() {
    AppImage(
        resId = R.drawable.logo,
        contentDescription = null,
        modifier = Modifier.size(40.dp),
        contentScale = ContentScale.Fit
    )
}

@Preview
@Composable
fun CollectionScreenPreview() = AppPreview {
    CollectionScreen(
        Resource.success(
            listOf(
                CollectionCardItem(
                    GobCard("0", "1", "", "", "", "", 1, 1, "", "", "", "", ""),
                    1,
                    0
                ),
                CollectionCardItem(
                    GobCard("1", "1", "", "", "", "", 1, 1, "", "", "", "", ""),
                    0,
                    0
                ),
                CollectionCardItem(
                    GobCard("2", "1", "", "", "", "", 1, 1, "", "", "", "", ""),
                    0,
                    1
                )
            )
        )
    )
}

@Keep
data class CollectionCardItem(
    val card: GobCard,
    val ownedAmount: Int,
    val ownedShinyAmount: Int
)