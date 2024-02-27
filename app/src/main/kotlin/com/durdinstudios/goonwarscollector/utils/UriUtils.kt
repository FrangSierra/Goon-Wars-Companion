package com.durdinstudios.goonwarscollector.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.minikorp.grove.Grove

/**
 * Tries to open an URL [Uri] if possible.
 *
 * @param onOpenSuccess Lambda to be called if an intent to open the URL could be found and prompted to the user to open it.
 * @param onOpenFailed Lambda to be called if an intent to to open the URL could not be found or any other exception happened.
 */
fun openUrl(context: Context,
            url: Uri,
            onOpenSuccess: () -> Unit = {},
            onOpenFailed: (Throwable) -> Unit = {}) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, url).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
        context.startActivity(intent)
        onOpenSuccess()
    } catch (e: ActivityNotFoundException) {
        Grove.e{"URL could not be opened in any app: $url"}
        onOpenFailed(e)
    } catch (e: Exception) {
        Grove.e{"URL could not be opened: ${e.message}"}
        onOpenFailed(e)
    }
}