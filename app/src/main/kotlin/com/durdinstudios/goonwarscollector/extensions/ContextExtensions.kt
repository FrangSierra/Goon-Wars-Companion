package com.durdinstudios.goonwarscollector.extensions

import android.Manifest
import android.app.Activity
import android.app.AppOpsManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.content.pm.PackageManager.ApplicationInfoFlags
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.os.Process
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.File
import java.util.*

/**
 * Get a color from resources.
 */
@ColorInt
fun Context.compatColor(@ColorRes colorId: Int): Int = ContextCompat.getColor(this, colorId)

/**
 * Get a [ColorStateList] from resources.
 */
fun Context.compatColorStateList(@ColorRes colorStateListId: Int): ColorStateList? =
    ContextCompat.getColorStateList(this, colorStateListId)

/**
 * Gets permission status.
 */
fun Context.compatCheckSelfPermission(permission: String): Int {
    return ContextCompat.checkSelfPermission(this, permission)
}

/**
 * Gets if permission is granted.
 */
fun Context.isPermissionGranted(permission: String): Boolean {
    if (permission == Manifest.permission.PACKAGE_USAGE_STATS) return appsStatsIsGranted(this)
    // ACCESS_BACKGROUND_LOCATION doesn't exist on SDK < 29, so location will work on foreground
    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        return compatCheckSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
    }
    return compatCheckSelfPermission(permission) == PERMISSION_GRANTED
}

private fun appsStatsIsGranted(context: Context): Boolean {
    // We need to check this with AppOpsManager, not with the usual permission checks
    // Check this thread for reference:
    // https://stackoverflow.com/questions/28921136/how-to-check-if-android-permission-package-usage-stats-permission-is-given
    val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.packageName)

    return if (mode == AppOpsManager.MODE_DEFAULT) {
        context.checkCallingOrSelfPermission(Manifest.permission.PACKAGE_USAGE_STATS) == PERMISSION_GRANTED
    } else {
        mode == AppOpsManager.MODE_ALLOWED
    }
}

/**
 * Copy text to clipboard.
 */
fun Context.copyToClipboard(clipLabel: String, text: CharSequence) {
    val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
    clipboard?.setPrimaryClip(ClipData.newPlainText(clipLabel, text))

    toast("Copied $clipLabel")
}

/**
 * Creates a toast of a given text.
 */
fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

/**
 * Returns the [File] that backs a [SharedPreferences].
 */
fun Context.getSharedPreferencesFile(name: String) = File(File(filesDir.parentFile, "shared_prefs"), "$name.xml")

/**
 * Creates a copy of the current [Context] with localized language.
 */
fun Context.withLocale(locale: Locale): Context {
    val newConfig = Configuration(resources.configuration).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setLocales(LocaleList(locale))
        } else {
            setLocale(locale)
        }
    }

    return createConfigurationContext(newConfig) ?: this
}

/**
 * Retrieve context's actitivy.
 */
fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

/**
 * Checks if a given package is installed.
 *
 * Remember to add the package to the queries section of your manifest!
 *
 * <manifest package="com.example.game">
 *   <queries>
 *     <!-- Specific apps you interact with, eg: -->
 *     <package android:name="com.example.store" />
 *     <package android:name="com.example.service" />
 *     <!-- Specific intents you query for, eg: for a custom share UI -->
 *     <intent>
 *       <action android:name="android.intent.action.SEND" />
 *       <data android:mimeType="image/jpeg" />
 *     </intent>
 *   </queries>
 *   ...
 * </manifest>
 */
fun Context.isPackageInstalled(packageName: String, flags: Long = 0): Boolean {
    val packageManager = this.packageManager
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getApplicationInfo(packageName, ApplicationInfoFlags.of(flags))
        } else {
            packageManager.getApplicationInfo(packageName, flags.toInt())
        }.enabled
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

/**
 * Checks if a given package is installed.
 *
 * Remember to add the package to the queries section of your manifest!
 */
fun Context.isGooglePlayStoreInstalled(): Boolean = isPackageInstalled("com.android.vending")
