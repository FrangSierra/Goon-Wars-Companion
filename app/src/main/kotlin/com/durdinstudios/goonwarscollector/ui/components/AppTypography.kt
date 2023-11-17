package com.durdinstudios.goonwarscollector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.bodyMediumEmphasisTextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.bodySmallMediumEmphasisTextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.buttonSnackbarTextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.buttonTextDisabledTextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.buttonTextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.heading1TextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.heading2TextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.heading3TextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.heading4TextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.heading5TextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.heading6TextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.heading7TextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.labelBottomActiveTextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.labelBottomInactiveTextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.linkSmallTextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.linkTextStyle
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles.textFieldInputTextFilledTextStyle

/**
 * Custom typographies container to provide different values for each flavor.
 * These values are used in [AppMaterialTheme] to compose the [AppTheme].
 */

@Composable
fun Heading1(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = heading1TextStyle,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Heading2(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = heading2TextStyle,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Heading3(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = heading3TextStyle,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Heading4(
    modifier: Modifier = Modifier,
    text: String,
    color : Color = heading4TextStyle.color,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = heading4TextStyle.copy(color),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Heading5(
    modifier: Modifier = Modifier,
    text: String,
    color : Color = heading5TextStyle.color,
    textAlign: TextAlign = TextAlign.Left,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = heading5TextStyle.copy(color = color),
        maxLines = maxLines,
        overflow = overflow,
        textAlign = textAlign,
    )
}

@Composable
fun Heading6(
    modifier: Modifier = Modifier,
    text: String,
    color : Color = heading6TextStyle.color,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = heading6TextStyle.copy(color = color),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Heading7(
    modifier: Modifier = Modifier,
    text: String,
    color : Color = heading7TextStyle.color,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = heading7TextStyle.copy(color = color),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun BodyMediumEmphasisLeft(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = bodyMediumEmphasisTextStyle,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Left
    )
}

@Composable
fun BodyMediumEmphasisCenter(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = bodyMediumEmphasisTextStyle.color,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = bodyMediumEmphasisTextStyle.copy(color = color),
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Center
    )
}

@Composable
fun BodySmallMediumEmphasisLeft(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = bodySmallMediumEmphasisTextStyle,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Left
    )
}

@Composable
fun BodySmallMediumEmphasisCenter(
    modifier: Modifier = Modifier,
    text: String,
    color : Color = bodySmallMediumEmphasisTextStyle.color,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = bodySmallMediumEmphasisTextStyle.copy(color = color),
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ButtonText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = buttonTextStyle,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Left
    )
}

@Composable
fun ButtonTextDisabled(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = buttonTextDisabledTextStyle,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Left
    )
}

@Composable
fun ButtonSnackbarText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = buttonSnackbarTextStyle,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Left
    )
}

@Composable
fun TextFieldInputTextFilled(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = textFieldInputTextFilledTextStyle,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Left
    )
}


@Composable
fun LabelBottomActive(modifier: Modifier = Modifier,
                      text: String,
                      maxLines: Int = Int.MAX_VALUE,
                      overflow: TextOverflow = TextOverflow.Clip) {
    AppText(
        modifier = modifier,
        text = text,
        style = labelBottomActiveTextStyle,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Left)
}

@Composable
fun LabelBottomInactive(modifier: Modifier = Modifier,
                        text: String,
                        maxLines: Int = Int.MAX_VALUE,
                        overflow: TextOverflow = TextOverflow.Clip) {
    AppText(
        modifier = modifier,
        text = text,
        style = labelBottomInactiveTextStyle,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Left)
}


@Composable
fun Link(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = linkTextStyle,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Left
    )
}

@Composable
fun LinkSmall(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        modifier = modifier,
        text = text,
        style = linkSmallTextStyle,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = TextAlign.Left
    )
}

@Composable
fun AppText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Left,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        style = TextStyle(
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        modifier = modifier,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    style: AppTextStyle,
    textAlign: TextAlign = TextAlign.Left,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    AppText(
        text = buildAnnotatedString {
            withStyle(style) {
                append(if (style.upperCase) text.uppercase() else text)
            }
        },
        modifier = modifier,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF024728)
@Suppress("LongMethod")
private fun AppTypographySpannablePreview() {
    AppPreview {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Heading1(text = "Heading1")
            Heading2(text = "Heading2")
            Heading3(text = "Heading3")
            Heading4(text = "Heading4")
            Heading5(text = "Heading5")
            Heading6(text = "Heading6")
            Heading7(text = "Heading7")
            BodyMediumEmphasisLeft(text = "BodyMediumEmphasis")
            BodySmallMediumEmphasisLeft(text = "BodySmallMediumEmphasis")
            ButtonText(text = "Button")
            ButtonTextDisabled(text = "ButtonTextDisabled")
            ButtonSnackbarText(
                text = "ButtonSnackbar",
                modifier = Modifier.background(Color.Black)
            )
            TextFieldInputTextFilled(text = "TextFieldInputTextFilled")
            Link(text = "Link")
            LinkSmall(text = "LinkSmall")
        }
    }
}