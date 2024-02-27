package com.durdinstudios.goonwarscollector.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val ChipShape = CircleShape

@Composable
fun MaterialChip(
    selected: Boolean,
    text: String,
    textStyle: TextStyle = typography.body2,
    modifier: Modifier = Modifier,
    selectedBackgroundColor: Color = MaterialTheme.colors.onPrimary,
    unselectedBackgroundColor: Color = Color.Transparent,
    selectedTextColor: Color = Color.Black,
    unselectedTextColor: Color = Color.Black,
    selectedStrokeColor: Color = selectedTextColor,
    unselectedStrokeColor: Color = unselectedTextColor,
    selectedStrokeSize: Dp = 1.dp,
    unselectedStrokeSize: Dp = selectedStrokeSize,
    shape: Shape = ChipShape,
    elevation: Dp = 0.dp,
    onClick: () -> Unit = {}
) {
    Surface(
        color = when {
            selected -> selectedBackgroundColor
            else -> unselectedBackgroundColor
        },
        shape = shape,
        border = BorderStroke(
            width = when {
                selected -> selectedStrokeSize
                else -> unselectedStrokeSize
            },
            color = when {
                selected -> selectedStrokeColor
                else -> unselectedStrokeColor
            }
        ),
        modifier = modifier,
        elevation = elevation,
        onClick = onClick
    ) {
        Text(
            text = text,
            color = when {
                selected -> selectedTextColor
                else -> unselectedTextColor
            },
            textAlign = TextAlign.Center,
            style = textStyle,
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 12.dp
            )
        )
    }
}