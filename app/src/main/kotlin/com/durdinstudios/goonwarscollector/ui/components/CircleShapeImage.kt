package com.durdinstudios.goonwarscollector.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.durdinstudios.goonwarscollector.R
import com.durdinstudios.goonwarscollector.ui.components.placeholders.withPlaceholder
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CircleShapeImage(modifier: Modifier, model: Any) {
    val context = LocalContext.current

    if (LocalInspectionMode.current || model is Int) {
        AppImage(
            modifier = modifier
                .clip(CircleShape)
                .withPlaceholder(),
            resId = R.drawable.gob_logo,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            tintColor = Color.White
        )
    } else {
        GlideImage(
            modifier = modifier
                .clip(CircleShape)
                .withPlaceholder(),
            imageModel = { model },
            requestBuilder = {
                Glide.with(context)
                    .asDrawable()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(DrawableTransitionOptions.withCrossFade())
            },
            imageOptions = ImageOptions(
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit
            )
        )
    }
}

