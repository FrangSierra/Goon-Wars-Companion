package com.durdinstudios.goonwarscollector.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.durdinstudios.goonwarscollector.ui.components.AppPreview
import com.durdinstudios.goonwarscollector.ui.components.VerticalList
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.ceil

/**
 * Main theme of the application.
 */
@Composable
fun AppMaterialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    MaterialTheme(
        colors = if (darkTheme) Colors.DarkColorPalette else Colors.LightColorPalette,
        typography = Typography.appTypography,
        shapes = Shapes(),
        content = content
    )
    SideEffect {
        systemUiController.setSystemBarsColor(Colors.appColorPalette.systemBarColor, false)
    }
}

@Preview
@Composable
private fun BrandColorsPreview(items: List<Pair<String, Color>> = previewItems[0]) {
    AppPreview(Modifier.fillMaxWidth()) {
        VerticalList(items = items, verticalArrangement = Arrangement.spacedBy(4.dp)) { (text, color) ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(
                    modifier = Modifier
                        .size(20.dp)
                        .background(color)
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.White
                )
            }
        }
    }
}

private val previewItems = listOf(
    "primary" to Colors.LightColorPalette.primary,
    "primaryVariant" to Colors.LightColorPalette.primaryVariant,
    "secondary" to Colors.LightColorPalette.secondary,
    "secondaryVariant" to Colors.LightColorPalette.secondaryVariant,
    "background" to Colors.LightColorPalette.background,
    "surface" to Colors.LightColorPalette.surface,
    "onError" to Colors.LightColorPalette.onError,
    "onPrimary" to Colors.LightColorPalette.onPrimary,
    "onSecondary" to Colors.LightColorPalette.onSecondary,
    "error" to Colors.LightColorPalette.error,
    "onBackground" to Colors.LightColorPalette.onBackground,
    "onSurface" to Colors.LightColorPalette.onSurface
).let {
    it.chunked(ceil(it.size / 2.0).toInt())
}

@Preview
@Composable
private fun BrandColorsPreview2() = BrandColorsPreview(previewItems[1])

@Preview
@Composable
private fun BrandTypographyPreview() {
    AppPreview(
        Modifier
            .fillMaxWidth()
            .background(Colors.LightColorPalette.background)
    ) {
        val text = "I can't beat the shit out of you without getting closer"

        Column {
            Text(text = text, style = MaterialTheme.typography.h1, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = text, style = MaterialTheme.typography.h2, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = text, style = MaterialTheme.typography.h3, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = text, style = MaterialTheme.typography.h4, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = text, style = MaterialTheme.typography.h5, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = text, style = MaterialTheme.typography.h6, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = text, style = MaterialTheme.typography.body1, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = text, style = MaterialTheme.typography.body2, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = text, style = MaterialTheme.typography.button, modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}
