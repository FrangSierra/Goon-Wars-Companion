package com.durdinstudios.goonwarscollector.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

/**
 * Custom colors container to provide different values for each flavor.
 * These values are used in [AppMaterialTheme] to compose the [AppTheme].
 */
object Colors {

    val appColorPalette = AppColorPalette(
        primary = Color(0xFF1d1b21),
        primaryDark = Color(0xFF16151A),
        primaryLight = Color(0xFF1F291F),
        primaryDisabled = Color(0xFF547260),
        primaryBackground = Color(0xFF221f27),
        onPrimary = Color(0xFFebecfe),
        secondary = Color(0xFF00cb5f),
        secondaryDark = Color(0xFF00cb5f),
        secondaryLight = Color(0xFF00cb5f),
        secondaryDisabled = Color(0xFF522501),
        secondaryBackground = Color(0xFF221f27),
        tertiary = Color(0xFF43aa00),
        tertiaryDark = Color(0xFF007400),
        tertiaryLight = Color(0xFF84d749),
        tertiaryDisabled = Color(0x4043aa00),
        tertiaryBackground = Color(0xFF221f27),
        onSecondary = Color(0xFF001f25),
        surface = Color(0xFF36343c),
        onSurface = Color(0xFFFFFFFF),
        link = Color(0xFF296afa),
        linkDark = Color(0xFF004b93),
        linkLight = Color(0xFF6994ff),
        alert = Color(0xFFee001d),
        alertBackground = Color(0xFFfcebed),
        warning = Color(0xFFe96800),
        warningBackground = Color(0xFFfef3eb),
        success = Color(0xFF47b900),
        successBackground = Color(0xFFf1faeb),
        black = Color(0xFF000000),
        blackDisabled = Color(0x40000000),
        greyDarker = Color(0xFF212427),
        greyDarkerInactive = Color(0xb3212427),
        greyDarkerDisabled = Color(0x40212427),
        greyDark = Color(0xFF444a4f),
        greyMedium = Color(0xFF7c8084),
        greyLight = Color(0xFFc5c7c8),
        greyLighter = Color(0xFFecedec),
        greyLightest = Color(0xFFf5f5f6),
        white = Color(0xFFffffff),
        whiteInactive = Color(0xb3ffffff),
        whiteDisabled = Color(0x40ffffff),
        topAppBar = Color(0xFF1d1b21),
        topAppBarText = Color(0xFFFFFFFF),
        systemBarColor = Color(0xFF1d1b21),
    )

    val LightColorPalette = lightColors(
        primary = Color(0xFF1d1b21),
        primaryVariant = Color(0xFF1d1b21),
        secondary = Color(0xFF00cb5f),
        secondaryVariant = Color(0xFF547260),
        background = Color(0xFF221f27),
        surface = Color(0xFF36343c),
        onError = Color(0xFFFFFFFF),
        onPrimary = Color(0xFFebecfe),
        onSecondary = Color(0xFF001f25),
        error = Color(0xFFba1a1a),
        onBackground = Color(0xFFebecfe),
        onSurface = Color(0xFFebecfe)
    )

    val DarkColorPalette = lightColors(
        primary = Color(0xFF1d1b21),
        primaryVariant = Color(0xFF1F291F),
        secondary = Color(0xFF00cb5f),
        secondaryVariant = Color(0xFF547260),
        background = Color(0xFF221f27),
        surface = Color(0xFF36343c),
        onError = Color(0xFFFFFFFF),
        onPrimary = Color(0xFFebecfe),
        onSecondary = Color(0xFF001f25),
        error = Color(0xFFba1a1a),
        onBackground = Color(0xFFebecfe),
        onSurface = Color(0xFFebecfe)
    )
}
