package com.durdinstudios.goonwarscollector.ui.components.semantics

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

/**
 * Semantics that can be added to any Compose node to give information about the resource ID used to set a drawable.
 *
 * Example:
 * val resId = R.drawable.my_drawable
 * Image(
 *   painter = painterResource(id = resId),
 *   contentDescription = null,
 *   modifier = Modifier.semantics { drawableId = resId }
 * )
 *
 * Extracted from: https://stackoverflow.com/a/71389302/9288365
 */
val DrawableResId = SemanticsPropertyKey<Int>("DrawableResId")
var SemanticsPropertyReceiver.drawableResId by DrawableResId
