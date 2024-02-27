package com.durdinstudios.goonwarscollector.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.durdinstudios.goonwarscollector.ui.components.images.GlideAppImage
import com.durdinstudios.goonwarscollector.ui.components.placeholders.withPlaceholder
import com.durdinstudios.goonwarscollector.ui.theme.Colors
import com.skydoves.landscapist.ImageOptions

@Composable
fun NftCard(name: String, imageUrl: String, priceString: String? = null, size: Int) {
    Card(
        modifier = Modifier.withPlaceholder().width(size.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.Transparent,
        border = BorderStroke(1.dp, Colors.appColorPalette.greyMedium)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box {
                GlideAppImage(
                    url = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(size.dp),
                    imageOptions = ImageOptions(contentScale = ContentScale.Fit)
                )
                BodySmallMediumEmphasisCenter(
                    text = name,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.5F))
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                )
            }
            priceString?.let {
                BodySmallMediumEmphasisCenter(
                    text = it, modifier = Modifier.padding(bottom = 4.dp),
                    color = Colors.appColorPalette.greyMedium
                )
            }

        }
    }
}