package com.durdinstudios.goonwarscollector.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.durdinstudios.goonwarscollector.core.arch.rememberSelector
import com.durdinstudios.goonwarscollector.domain.persistence.PersistenceController
import com.durdinstudios.goonwarscollector.domain.persistence.settings.Keys
import com.durdinstudios.goonwarscollector.domain.wallet.articlesSelector
import com.durdinstudios.goonwarscollector.domain.wallet.userWallets
import kotlinx.coroutines.delay
import org.kodein.di.compose.rememberInstance

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit = {},
    navigateToOnBoarding: () -> Unit = {},
) {
    val selectorWallets = rememberSelector(selector = userWallets)

    LaunchedEffect(Unit) {
        if (selectorWallets.isEmpty()){
            navigateToOnBoarding()
        } else {
            navigateToHome()
        }
    }
}