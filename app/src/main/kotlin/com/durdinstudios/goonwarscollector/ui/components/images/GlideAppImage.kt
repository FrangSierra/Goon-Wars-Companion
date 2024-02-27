package com.durdinstudios.goonwarscollector.ui.components.images

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.durdinstudios.goonwarscollector.R
import com.durdinstudios.goonwarscollector.ui.components.AppImage
import com.durdinstudios.goonwarscollector.ui.components.placeholders.withPlaceholder
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

/**
 * Wrapper over [GlideImage] that displays preview images on preview.
 */
@Composable
fun GlideAppImage(
    modifier: Modifier = Modifier,
    url: String?,
    contentDescription: String? = null,
    imageOptions: ImageOptions = ImageOptions(),
    thumbnailMultiplier: Float? = null,
    @DrawableRes errorImageResId: Int? = null
) {
    val context = LocalContext.current

    // Add code for preview mode
    if (LocalInspectionMode.current) {
        AppImage(
            modifier = modifier,
            resId = R.drawable.logo,
            contentDescription = contentDescription
        )
    } else {
        // Add semantics for the Glide image (it seems it's not added in the default implementation)
        val semantics = if (contentDescription != null) {
            Modifier.semantics {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
        } else {
            Modifier
        }

        GlideImage(
            modifier = modifier
                .then(semantics)
                .withPlaceholder(),
            imageModel = { url },
            requestBuilder = {
                Glide.with(LocalContext.current)
                    .asDrawable()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .let { thumbnailMultiplier?.let { thumb -> it.thumbnail(thumb) } ?: it }
                    .transition(withCrossFade())
            },
            imageOptions = ImageOptions(
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
        )
    }
}
