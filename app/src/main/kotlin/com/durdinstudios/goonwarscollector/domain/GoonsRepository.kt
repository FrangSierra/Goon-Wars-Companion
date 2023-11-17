package com.durdinstudios.goonwarscollector.domain

import com.durdinstudios.goonwarscollector.domain.wallet.NetworkCard
import com.durdinstudios.goonwarscollector.domain.wallet.NetworkCardOwnership
import com.prof18.rssparser.RssParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class GoonsRepository(private val api: GoonsApi) : CoroutineScope by CoroutineScope(Job()) {

    val parser = RssParser()

    suspend fun getCards(): List<NetworkCard> {
        return withContext(Dispatchers.IO) {
            val cards = api.getCards()
            cards
        }
    }

    suspend fun getArticles(): List<Article> {
        return withContext(Dispatchers.IO) {
            parser.getRssChannel("https://medium.com/feed/@GoonsofBalatroon")
                .items
                .mapNotNull {
                    if (it.title != null
                        && it.link != null
                        && it.content != null
                        && it.image != null
                    ) Article(it.title, it.link, it.content, it.image) else null
                }
        }
    }

    suspend fun getCardOwnership(wallet: String): List<NetworkCardOwnership> {
        return withContext(Dispatchers.IO) {
            val cards = api.getCardsOwnership(wallet)
            cards
        }
    }
}

data class Article(
    val title: String?,
    val link: String?,
    val content: String?,
    val image: String?
) {
    override fun toString(): String {
        return "$title"
    }
}