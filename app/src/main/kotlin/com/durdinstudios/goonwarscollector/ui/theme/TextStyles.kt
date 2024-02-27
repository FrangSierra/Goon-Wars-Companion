package com.durdinstudios.goonwarscollector.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.durdinstudios.goonwarscollector.ui.components.AppTextStyle

object TextStyles {

    /**
     * Text Styles definition.
     */
    val heading1TextStyle = AppTextStyle(
        color = Color.White,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = roboto,
        lineSpacingExtra = 16.sp,
        upperCase = false
    )

    val heading2TextStyle = AppTextStyle(
        color = Color.White,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = roboto,
        lineSpacingExtra = 10.sp,
        upperCase = false
    )

    val heading3TextStyle = AppTextStyle(
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = roboto,
        lineSpacingExtra = 8.sp
    )

    val heading4TextStyle = AppTextStyle(
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = roboto,
        lineSpacingExtra = 2.sp,
        upperCase = true
    )

    val heading5TextStyle = AppTextStyle(
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = roboto,
        lineSpacingExtra = 8.sp
    )

    val heading6TextStyle = AppTextStyle(
        color = Color.White,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = roboto,
        lineSpacingExtra = 6.sp,
        upperCase = true
    )

    val heading7TextStyle = AppTextStyle(
        color = Color.White,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = roboto,
        lineSpacingExtra = 8.sp,
        upperCase = true
    )

    val bodyMediumEmphasisTextStyle = AppTextStyle(
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = roboto,
        lineSpacingExtra = 8.sp
    )

    val bodySmallMediumEmphasisTextStyle = AppTextStyle(
        color = Color.White,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = roboto,
        lineSpacingExtra = 6.sp
    )

    val buttonTextStyle = AppTextStyle(
        color = Colors.LightColorPalette.secondary,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = roboto,
        lineSpacingExtra = 6.sp,
        upperCase = true
    )

    val textFieldInputTextTextStyle = AppTextStyle(
        color = Color.White,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = roboto,
        lineSpacingExtra = 4.sp
    )

    private val textFieldAssistiveTextTextStyle = AppTextStyle(
        color = Color.White,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = roboto,
        lineSpacingExtra = 2.sp,
        upperCase = false
    )

    val linkTextStyle = AppTextStyle(
        color = Colors.appColorPalette.secondary,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = roboto,
        lineSpacingExtra = 12.sp
    )

    val linkSmallTextStyle = AppTextStyle(
        color = Colors.appColorPalette.secondary,
        fontSize = 14.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = roboto,
        lineSpacingExtra = 10.sp
    )

    val textFieldInputTextFilledTextStyle = textFieldInputTextTextStyle.copy()

    val textFieldAssistiveTextActiveTextStyle = textFieldAssistiveTextTextStyle.copy()

    val textFieldAssistiveTextErrorTextStyle = textFieldAssistiveTextTextStyle.copy(
        color = Colors.LightColorPalette.error
    )

    val labelActiveTextStyle = AppTextStyle(
        color = Colors.appColorPalette.greyDarker,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = roboto,
        lineSpacingExtra = 4.sp,
        upperCase = false
    )

    val labelInactiveTextStyle = AppTextStyle(
        color = Colors.appColorPalette.greyMedium,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = roboto,
        lineSpacingExtra = 4.sp,
        upperCase = false
    )

    val labelBottomInactiveTextStyle = labelInactiveTextStyle.copy(
        color = Colors.appColorPalette.greyMedium
    )

    val labelBottomActiveTextStyle = labelActiveTextStyle.copy(
        color = Colors.appColorPalette.secondary
    )

    val buttonTextDisabledTextStyle = buttonTextStyle.copy(
        color = Colors.LightColorPalette.secondaryVariant
    )

    val buttonSnackbarTextStyle = AppTextStyle(
        color = Colors.LightColorPalette.secondary,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = roboto,
        lineSpacingExtra = 4.sp,
        upperCase = false
    )

    private val buttonSmallTextStyle = AppTextStyle(
        color = Colors.appColorPalette.black,
        fontFamily = roboto,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.sp,
        lineSpacingExtra = 6.sp,
        upperCase = false
    )

    private val buttonMediumTextStyle = AppTextStyle(
        color = Colors.appColorPalette.black,
        fontFamily = roboto,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
        lineSpacingExtra = 8.sp,
        upperCase = false
    )

    val buttonSmallDefaultTextStyle = buttonSmallTextStyle.copy()

    val buttonSmallOnPrimaryTextStyle = buttonSmallTextStyle.copy(
        color = Colors.appColorPalette.onPrimary
    )

    val buttonSmallOnSecondaryTextStyle = buttonSmallTextStyle.copy(
        color = Colors.appColorPalette.onSecondary
    )

    val buttonSmallAlertTextStyle = buttonSmallTextStyle.copy(
        color = Colors.appColorPalette.alert
    )

    val buttonSmallDisabledTextStyle = buttonSmallTextStyle.copy(
        color = Colors.appColorPalette.greyLight
    )

    val buttonMediumDefaultTextStyle = buttonMediumTextStyle.copy()

    val buttonMediumOnPrimaryTextStyle = buttonMediumTextStyle.copy(
        color = Colors.appColorPalette.onPrimary
    )

    val buttonMediumOnSecondaryTextStyle = buttonMediumTextStyle.copy(
        color = Colors.appColorPalette.onSecondary
    )

    val buttonMediumAlertTextStyle = buttonMediumTextStyle.copy(
        color = Colors.appColorPalette.alert
    )

    val buttonMediumDisabledTextStyle = buttonMediumTextStyle.copy(
        color = Colors.appColorPalette.greyLight
    )

}