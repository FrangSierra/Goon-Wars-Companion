package com.durdinstudios.goonwarscollector.ui.components.buttons

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.durdinstudios.goonwarscollector.ui.theme.Colors
import com.durdinstudios.goonwarscollector.ui.theme.TextStyles

val buttonSmallShape = RoundedCornerShape(4.dp)
val buttonMediumShape = RoundedCornerShape(4.dp)
val buttonLargeShape = RoundedCornerShape(4.dp)

val secondaryButtonDefaultColor = Colors.appColorPalette.secondary
val secondaryButtonDisabledColor = Colors.appColorPalette.secondaryDark
val secondaryButtonFocusedColor = Colors.appColorPalette.secondaryDark
val secondaryButtonPressedColor = Colors.appColorPalette.secondaryLight

private val buttonSolidPrimaryStyle = GenericButtonStyle(
    shape = RoundedCornerShape(4.dp),
    backgroundColor = Colors.appColorPalette.primary,
    pressedBackgroundColor = Colors.appColorPalette.primaryLight,
    focusedBackgroundColor = Colors.appColorPalette.primaryDark,
    disabledBackgroundColor = Color.LightGray,
    disabledContentColor = Color.Gray
)

val buttonSolidPrimarySmallStyle = buttonSolidPrimaryStyle.copy(
    height = 32.dp,
    textStyle = TextStyles.buttonSmallOnPrimaryTextStyle,
    contentColor = TextStyles.buttonSmallOnPrimaryTextStyle.color,
    shape = buttonSmallShape
)

val buttonSolidPrimaryMediumStyle = buttonSolidPrimaryStyle.copy(
    height = 40.dp,
    textStyle = TextStyles.buttonMediumOnPrimaryTextStyle,
    contentColor = TextStyles.buttonMediumOnPrimaryTextStyle.color,
    shape = buttonMediumShape
)

val buttonSolidPrimaryLargeStyle = buttonSolidPrimaryStyle.copy(
    height = 48.dp,
    textStyle = TextStyles.buttonMediumOnPrimaryTextStyle,
    contentColor = Colors.appColorPalette.onPrimary,
    shape = buttonLargeShape
)

private val buttonSolidSecondaryStyle = GenericButtonStyle(
    shape = RoundedCornerShape(4.dp),
    backgroundColor = secondaryButtonDefaultColor,
    pressedBackgroundColor = secondaryButtonPressedColor,
    focusedBackgroundColor = secondaryButtonFocusedColor,
    disabledBackgroundColor = secondaryButtonDisabledColor,
    disabledContentColor = Color.Gray
)

val buttonSolidSecondarySmallStyle = buttonSolidSecondaryStyle.copy(
    height = 32.dp,
    textStyle = TextStyles.buttonSmallOnSecondaryTextStyle,
    contentColor = TextStyles.buttonSmallOnSecondaryTextStyle.color,
    shape = buttonSmallShape
)

val buttonSolidSecondaryMediumStyle = buttonSolidSecondaryStyle.copy(
    height = 40.dp,
    textStyle = TextStyles.buttonMediumOnSecondaryTextStyle,
    contentColor = TextStyles.buttonMediumOnSecondaryTextStyle.color,
    shape = buttonMediumShape
)

val buttonSolidSecondaryLargeStyle = buttonSolidSecondaryStyle.copy(
    height = 48.dp,
    textStyle = TextStyles.buttonMediumOnSecondaryTextStyle,
    contentColor = TextStyles.buttonMediumOnSecondaryTextStyle.color,
    shape = buttonLargeShape
)

private val buttonSolidSurfaceStyle = GenericButtonStyle(
    shape = RoundedCornerShape(4.dp),
    outlined = true,
    backgroundColor = Colors.appColorPalette.surface,
    pressedBackgroundColor = Colors.appColorPalette.surface,
    focusedBackgroundColor = Colors.appColorPalette.surface,
    disabledBackgroundColor = Colors.appColorPalette.surface,
    disabledContentColor = Color.LightGray,
    elevation = GenericButtonStyle.GenericButtonElevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp
    ),
    outlineColor = Color.Transparent
)

val buttonSolidSurfaceSmallStyle = buttonSolidSurfaceStyle.copy(
    height = 32.dp,
    textStyle = TextStyles.buttonSmallDefaultTextStyle,
    contentColor = TextStyles.buttonSmallDefaultTextStyle.color,
    shape = buttonSmallShape
)

val buttonSolidSurfaceMediumStyle = buttonSolidSurfaceStyle.copy(
    height = 40.dp,
    textStyle = TextStyles.buttonMediumDefaultTextStyle,
    contentColor = TextStyles.buttonMediumDefaultTextStyle.color,
    shape = buttonMediumShape
)

val buttonSolidSurfaceLargeStyle = buttonSolidSurfaceStyle.copy(
    height = 48.dp,
    textStyle = TextStyles.buttonMediumDefaultTextStyle,
    contentColor = TextStyles.buttonMediumDefaultTextStyle.color,
    shape = buttonLargeShape
)

private val buttonOutlineDefaultStyle = GenericButtonStyle(
    shape = RoundedCornerShape(4.dp),
    outlined = true,
    backgroundColor = Colors.appColorPalette.surface,
    pressedBackgroundColor = Colors.appColorPalette.surface,
    focusedBackgroundColor = Colors.appColorPalette.surface,
    disabledBackgroundColor = Colors.appColorPalette.surface,
    disabledContentColor = Color.LightGray,
    elevation = GenericButtonStyle.GenericButtonElevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp
    )
)

val buttonOutlineDefaultSmallStyle = buttonOutlineDefaultStyle.copy(
    height = 32.dp,
    textStyle = TextStyles.buttonSmallDefaultTextStyle,
    contentColor = TextStyles.buttonSmallDefaultTextStyle.color,
    outlineColor = TextStyles.buttonSmallDefaultTextStyle.color,
    shape = buttonSmallShape
)

val buttonOutlineDefaultMediumStyle = buttonOutlineDefaultStyle.copy(
    height = 40.dp,
    textStyle = TextStyles.buttonMediumDefaultTextStyle,
    contentColor = TextStyles.buttonMediumDefaultTextStyle.color,
    outlineColor = TextStyles.buttonMediumDefaultTextStyle.color,
    shape = buttonMediumShape
)

val buttonOutlineDefaultLargeStyle = buttonOutlineDefaultStyle.copy(
    height = 48.dp,
    textStyle = TextStyles.buttonMediumDefaultTextStyle,
    contentColor = Colors.appColorPalette.onPrimary,
    outlineColor = Colors.appColorPalette.onPrimary,
    shape = buttonLargeShape
)

private val buttonOutlineAlertStyle = GenericButtonStyle(
    shape = RoundedCornerShape(4.dp),
    outlined = true,
    backgroundColor = Colors.appColorPalette.alertBackground,
    pressedBackgroundColor = Colors.appColorPalette.alertBackground,
    focusedBackgroundColor = Colors.appColorPalette.alertBackground,
    disabledBackgroundColor = Colors.appColorPalette.alertBackground,
    disabledContentColor = Color.LightGray,
    elevation = GenericButtonStyle.GenericButtonElevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp
    )
)

val buttonOutlineAlertSmallStyle = buttonOutlineAlertStyle.copy(
    height = 32.dp,
    textStyle = TextStyles.buttonSmallAlertTextStyle,
    contentColor = TextStyles.buttonSmallAlertTextStyle.color,
    outlineColor = TextStyles.buttonSmallAlertTextStyle.color,
    shape = buttonSmallShape
)

val buttonOutlineAlertMediumStyle = buttonOutlineAlertStyle.copy(
    height = 40.dp,
    textStyle = TextStyles.buttonMediumAlertTextStyle,
    contentColor = TextStyles.buttonMediumAlertTextStyle.color,
    outlineColor = TextStyles.buttonMediumAlertTextStyle.color,
    shape = buttonMediumShape
)

val buttonOutlineAlertLargeStyle = buttonOutlineAlertStyle.copy(
    height = 48.dp,
    textStyle = TextStyles.buttonMediumAlertTextStyle,
    contentColor = TextStyles.buttonMediumAlertTextStyle.color,
    outlineColor = TextStyles.buttonMediumAlertTextStyle.color,
    shape = buttonLargeShape
)
