package com.durdinstudios.goonwarscollector.ui.components.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.durdinstudios.goonwarscollector.R
import com.durdinstudios.goonwarscollector.ui.components.AppIcon
import com.durdinstudios.goonwarscollector.ui.components.placeholders.withPlaceholder
import com.durdinstudios.goonwarscollector.ui.components.spacers.HorizontalSpacer
import com.durdinstudios.goonwarscollector.ui.theme.Colors

const val ADD_CHECKED_BUTTON = "addCheckedButton"
const val ADD_CHECKED_ICON = "addCheckedIcon"

@Composable
fun ButtonAddCheckedComponent() {
    Row {
        HorizontalSpacer(width = 8.dp) // In cancel bonus the text is so close to the icon
        AppIcon(
            modifier = Modifier
                .height(32.dp)
                .width(32.dp)
                .testTag(ADD_CHECKED_ICON),
            resId = R.drawable.ic_check,
            contentDescription = null,
            tint = Colors.appColorPalette.success
        )
    }
}

@Composable
fun ButtonAddInactiveComponent(text: String, onClick: () -> Unit) {
    ButtonSolidSecondarySmall(modifier = Modifier
        .withPlaceholder()
        .width(100.dp)
        .testTag(ADD_CHECKED_BUTTON),
        text = text,
        onClick = { onClick() })
}
