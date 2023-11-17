package com.durdinstudios.goonwarscollector.domain.persistence.settings

import com.durdinstudios.goonwarscollector.domain.persistence.Element

/**
 * Keys used as identifier for the different stored elements on the actual persistence system.
 */
object Keys {
    object Version: Element.Key
    object UserWallets: Element.Key
}

