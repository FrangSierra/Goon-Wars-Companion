package com.durdinstudios.goonwarscollector.ui.home

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.durdinstudios.goonwarscollector.R
import com.durdinstudios.goonwarscollector.core.FakeData
import com.durdinstudios.goonwarscollector.core.arch.Resource
import com.durdinstudios.goonwarscollector.core.arch.ResourcePlaceholderContent
import com.durdinstudios.goonwarscollector.core.arch.rememberSelector
import com.durdinstudios.goonwarscollector.core.arch.useStore
import com.durdinstudios.goonwarscollector.domain.Article
import com.durdinstudios.goonwarscollector.domain.wallet.GetGobArticles
import com.durdinstudios.goonwarscollector.domain.wallet.GetMarketStats
import com.durdinstudios.goonwarscollector.domain.wallet.GetUserGobCardsAction
import com.durdinstudios.goonwarscollector.domain.wallet.MarketStats
import com.durdinstudios.goonwarscollector.domain.wallet.articlesSelector
import com.durdinstudios.goonwarscollector.domain.wallet.statsSelector
import com.durdinstudios.goonwarscollector.domain.wallet.userWallets
import com.durdinstudios.goonwarscollector.ui.components.AppImage
import com.durdinstudios.goonwarscollector.ui.components.AppPreview
import com.durdinstudios.goonwarscollector.ui.components.AppScaffold
import com.durdinstudios.goonwarscollector.ui.components.BodyMediumEmphasisLeft
import com.durdinstudios.goonwarscollector.ui.components.BodySmallMediumEmphasisCenter
import com.durdinstudios.goonwarscollector.ui.components.Heading1
import com.durdinstudios.goonwarscollector.ui.components.Heading4
import com.durdinstudios.goonwarscollector.ui.components.Heading6
import com.durdinstudios.goonwarscollector.ui.components.bottomnavigation.AppBottomBar
import com.durdinstudios.goonwarscollector.ui.components.images.GlideAppImage
import com.durdinstudios.goonwarscollector.ui.components.placeholders.withPlaceholder
import com.durdinstudios.goonwarscollector.ui.components.spacers.VerticalDivider
import com.durdinstudios.goonwarscollector.ui.components.spacers.VerticalSpacer
import com.durdinstudios.goonwarscollector.ui.theme.Colors
import com.durdinstudios.goonwarscollector.utils.openUrl
import com.skydoves.landscapist.ImageOptions

@Composable
fun HomeScreen(scaffoldState: ScaffoldState = rememberScaffoldState()) {
    val store = useStore()
    val selectorArticles = rememberSelector(selector = articlesSelector)
    val selector = rememberSelector(selector = statsSelector)
    val userWallet = rememberSelector(selector = userWallets)

    LaunchedEffect(Unit) {
        if (userWallet.isNotEmpty()) store.dispatch(GetUserGobCardsAction.Request(userWallet))

        store.dispatch(GetMarketStats.Request)
        store.dispatch(GetGobArticles.Request)
    }

    HomeScreen(selector, selectorArticles, scaffoldState)
}

@Composable
fun HomeScreen(
    userLoggedSelector: Resource<MarketStats>,
    articlesSelector: Resource<List<Article>> = Resource.empty(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    AppScaffold(scaffoldState = scaffoldState) {
        Box(Modifier.fillMaxSize(), Alignment.BottomCenter) {
            ResourcePlaceholderContent(
                resource = userLoggedSelector, placeholderValue = FakeData.MarketStats.marketStats
            ) {
                HomeContent(it, articlesSelector)
            }
            AppBottomBar()
        }
    }
}

@Composable
fun HomeArticlesList(articlesSelector: Resource<List<Article>>) {
    val context = LocalContext.current
    ResourcePlaceholderContent(
        resource = articlesSelector, placeholderValue = FakeData.Articles.articles
    ) { articles ->
        Column {
            Heading4(
                text = "Last Articles", modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                articles.forEach { article ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                article.link?.let {
                                    openUrl(
                                        context = context,
                                        url = Uri.parse(it)
                                    )
                                }
                            }
                            .withPlaceholder(), verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlideAppImage(
                            url = article.image,
                            modifier = Modifier
                                .height(60.dp)
                                .width(120.dp),
                            imageOptions = ImageOptions(contentScale = ContentScale.Fit)
                        )
                        Heading4(text = article.title ?: "", modifier = Modifier.padding(start = 12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun HomeRecentlySoldList(marketSelector: MarketStats) {
    Heading4(text = "Recently sold", modifier = Modifier.padding(horizontal = 16.dp))
    LazyRow(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = marketSelector.lastSales) {
            Card(
                modifier = Modifier
                    .withPlaceholder()
                    .width(120.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Colors.appColorPalette.surface,
                border = BorderStroke(1.dp, Colors.appColorPalette.greyMedium)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(contentAlignment = Alignment.TopCenter) {
                        GlideAppImage(
                            url = it.url,
                            contentDescription = null,
                            modifier = Modifier.size(120.dp),
                            imageOptions = ImageOptions(contentScale = ContentScale.Fit)
                        )
                        BodySmallMediumEmphasisCenter(
                            text = it.name,
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.5F))
                                .padding(vertical = 4.dp)
                                .fillMaxWidth()
                        )
                    }
                    BodySmallMediumEmphasisCenter(
                        text = it.price, modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeRecentlyListedCarrousel(marketSelector: MarketStats) {
    Heading4(text = "Recently listed", modifier = Modifier.padding(horizontal = 16.dp))
    LazyRow(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = marketSelector.listing) {
            Card(
                modifier = Modifier
                    .withPlaceholder()
                    .width(120.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Colors.appColorPalette.surface,
                border = BorderStroke(1.dp, Colors.appColorPalette.greyMedium)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(contentAlignment = Alignment.TopCenter) {
                        GlideAppImage(
                            url = it.url,
                            contentDescription = null,
                            modifier = Modifier.size(120.dp),
                            imageOptions = ImageOptions(contentScale = ContentScale.Fit)
                        )
                        BodySmallMediumEmphasisCenter(
                            text = it.name,
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.5F))
                                .padding(vertical = 4.dp)
                                .fillMaxWidth()
                        )
                    }
                    BodySmallMediumEmphasisCenter(
                        text = it.price, modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    marketSelector: MarketStats,
    articlesSelector: Resource<List<Article>>
) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(bottom = 68.dp)
    ) {
        AppImage(
            resId = R.drawable.goon_gm,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp),
            contentScale = ContentScale.Fit
        )
        HomeRecentlyListedCarrousel(marketSelector)
        HomeRecentlySoldList(marketSelector)
        VerticalDivider(modifier = Modifier.padding(vertical = 12.dp))
        HomeArticlesList(articlesSelector)
    }
}

@Composable
private fun HomeHeader(onProfileClick: () -> Unit = {}) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            BodyMediumEmphasisLeft(text = "Your Balance:", modifier = Modifier.withPlaceholder())
            Heading1(text = "1.000.000 $", modifier = Modifier.withPlaceholder())
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { onProfileClick() }) {
            AppImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)
                    .withPlaceholder(),
                resId = R.drawable.placeholder,
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
        }
    }
}

@Preview
@Composable
private fun PriceCard(
    token: String = "GOB",
    logoUrl: Int = R.drawable.chunks,
    price: Double = 0.003122
) {
    Card(
        modifier = Modifier.withPlaceholder(),
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Colors.appColorPalette.surface,
        border = BorderStroke(1.dp, Colors.appColorPalette.greyMedium)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppImage(resId = logoUrl, contentDescription = null, modifier = Modifier.size(36.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Column {
                    Heading4(text = "$$price")
                    VerticalSpacer(height = 4.dp)
                    //Heading6(text = token, color = Colors.appColorPalette.greyMedium)
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() = AppPreview {
    HomeContent(
        FakeData.MarketStats.marketStats,
        Resource.success(
            FakeData.Articles.articles
        )
    )
}