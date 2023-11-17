package com.durdinstudios.goonwarscollector.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.durdinstudios.goonwarscollector.R

val roboto = FontFamily(
    Font(resId = R.font.roboto_regular, FontWeight.Normal),
    Font(resId = R.font.roboto_black, FontWeight.Black),
    Font(resId = R.font.roboto_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(resId = R.font.roboto_bold, FontWeight.Bold),
    Font(resId = R.font.roboto_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(resId = R.font.roboto_light, FontWeight.Light),
    Font(resId = R.font.roboto_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(resId = R.font.roboto_thin, FontWeight.Thin),
    Font(resId = R.font.roboto_thin_italic, FontWeight.Thin, FontStyle.Italic)
)


/**
 * Custom typographies container to provide different values for each flavor if needed.
 * These values are used in [AppMaterialTheme] to compose the [AppTheme].
 */
object Typography {
    val appTypography = Typography(
        h1 = TextStyles.heading1TextStyle.textStyle,
        h2 = TextStyles.heading2TextStyle.textStyle,
        h3 = TextStyles.heading3TextStyle.textStyle,
        h4 = TextStyles.heading4TextStyle.textStyle,
        h5 = TextStyles.heading5TextStyle.textStyle,
        h6 = TextStyles.heading6TextStyle.textStyle,
        body1 = TextStyles.bodyMediumEmphasisTextStyle.textStyle,
        body2 = TextStyles.bodySmallMediumEmphasisTextStyle.textStyle,
        button = TextStyles.buttonTextStyle.textStyle,
        defaultFontFamily = roboto
    )
}
