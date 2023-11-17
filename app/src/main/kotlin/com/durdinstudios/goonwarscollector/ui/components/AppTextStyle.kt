package com.durdinstudios.goonwarscollector.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Wrap TextStyle allowing to use upperCase for setting texts in styles in all-caps,
 * and lineSpacingExtra for faster mapping from Zeplin.
 */
data class AppTextStyle(
    val color: Color = Color.Unspecified,
    val fontSize: TextUnit = TextUnit.Unspecified,
    val fontWeight: FontWeight? = null,
    val fontFamily: FontFamily? = null,
    val lineSpacingExtra: TextUnit = TextUnit.Unspecified,
    val upperCase: Boolean = false,
    val letterSpacing: TextUnit = TextUnit.Unspecified
) {
    val textStyle: TextStyle by lazy {
        check(fontSize.isSp && lineSpacingExtra.isSp) { "fontSize and lineSpacingExtra gotta be measured in sp" }

        TextStyle(
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            lineHeight = (fontSize.value + lineSpacingExtra.value).sp,
            letterSpacing = letterSpacing
        )
    }

    val spanStyle: SpanStyle by lazy {
        check(fontSize.isSp && lineSpacingExtra.isSp) { "fontSize and lineSpacingExtra gotta be measured in sp" }

        SpanStyle(
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing
        )
    }

    val paragraphStyle: ParagraphStyle by lazy {
        check(fontSize.isSp && lineSpacingExtra.isSp) { "fontSize and lineSpacingExtra gotta be measured in sp" }

        ParagraphStyle(
            textAlign = null,
            textDirection = null,
            lineHeight = (fontSize.value + lineSpacingExtra.value).sp,
            textIndent = null
        )
    }
}

/**
 * Pushes style to the AnnotatedString.Builder, executes block and then pops the style.
 */
inline fun <R : Any> AnnotatedString.Builder.withStyle(style: AppTextStyle, crossinline block: AnnotatedString.Builder.() -> R): R {
    return withStyle(style.paragraphStyle) {
        withStyle(style.spanStyle) {
            block(this)
        }
    }
}
