package com.durdinstudios.goonwarscollector.core

import com.durdinstudios.goonwarscollector.domain.Article
import com.durdinstudios.goonwarscollector.domain.wallet.MarketListing
import com.durdinstudios.goonwarscollector.domain.wallet.MarketSale
import com.durdinstudios.goonwarscollector.domain.wallet.MarketStats
import com.durdinstudios.goonwarscollector.domain.wallet.Nft
import java.util.Date

object FakeData {
    object MarketStats {
        val marketSale = MarketSale("0.1", "", "1", "Goon 1", "", "", "", Date())
        val marketListing = MarketListing("0.1", "", "1", "Goon #001")
        val marketStats = MarketStats(
            "0.1",
            listOf(
                marketSale,
                marketSale.copy(id = "2", name = "Goon 2"),
                marketSale.copy(id = "3", name = "Goon 3"),
            ),
            listOf(
                marketListing,
                marketListing.copy(id = "2", name = "Goon 2"),
                marketListing.copy(id = "3", name = "Goon 3"),
            )
        )
    }

    object Articles {
        val articles = listOf(
            Article("Random Article", null, null, null),
            Article("Random Article 2", null, null, null),
            Article("Random Article 3", null, null, null),
        )
    }

    object Profile {
        val nfts = listOf(
            Nft("1", "Potato", "", "goonsofbalatroon"),
            Nft("2", "Potato 2", "", "goonsofbalatroon"),
            Nft("3", "Potato 3", "", "goonsofbalatroon"),
            Nft("4", "Potato 4", "", "goonsofbalatroon"),
        )
    }
}