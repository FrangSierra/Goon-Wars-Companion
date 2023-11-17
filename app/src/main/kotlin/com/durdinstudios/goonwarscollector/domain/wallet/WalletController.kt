package com.durdinstudios.goonwarscollector.domain.wallet

import com.durdinstudios.goonwarscollector.core.arch.Maybe
import com.durdinstudios.goonwarscollector.core.arch.maybeCatching
import com.durdinstudios.goonwarscollector.domain.Article
import com.durdinstudios.goonwarscollector.domain.GoonsRepository
import com.durdinstudios.goonwarscollector.domain.opensea.NetworkListing
import com.durdinstudios.goonwarscollector.domain.opensea.OpenSeaRepository
import com.minikorp.grove.Grove
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.math.abs

interface WalletController {
    suspend fun getGobCards(): Maybe<List<GobCard>>

    suspend fun getGobArticles(): Maybe<List<Article>>

    suspend fun getWalletCardsOwnership(wallet: List<String>): Maybe<List<CardOwnership>>
    suspend fun getStats(): Maybe<MarketStats>
}

class WalletControllerImpl(val repository: GoonsRepository, val openSeaRepository: OpenSeaRepository) :
    WalletController {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override suspend fun getGobCards(): Maybe<List<GobCard>> =
        maybeCatching {
            val cards = repository.getCards()
            cards.mapNotNull {
                try {
                    it.toGobCard()
                } catch (e: Exception) {
                    null
                }
            }
        }

    override suspend fun getGobArticles(): Maybe<List<Article>> =
        maybeCatching {
            withContext(Dispatchers.IO) {
                val articles = repository.getArticles()
                articles
            }
        }

    override suspend fun getStats(): Maybe<MarketStats> =
        maybeCatching {
            withContext(Dispatchers.IO) {
                val stats = async { openSeaRepository.getCollectionStats() }
                val nftSales = async { openSeaRepository.getCollectionLastSales(3).asset_events }

                val listing = mutableListOf<NetworkListing>()
                var list = openSeaRepository.getCollectionListing(null)

                listing.addAll(list.listings)
                while (list.next != null) {
                    list = openSeaRepository.getCollectionListing(list.next!!)
                    listing.addAll(list.listings)
                }

                val listingInfo = listing
                    .sortedByDescending { Date(it.protocol_data.parameters.startTime.toLong() * 1000L) }
                    .subList(0, 10)

                val nftListingInfos = listingInfo
                    .map {
                        scope.async {
                            openSeaRepository.getNftInfo(it.protocol_data.parameters.offer.first().identifierOrCriteria)
                        }
                    }.awaitAll()

                MarketStats(
                    floorPrice = stats.await().total.floor_price.toString(),
                    lastSales = nftSales.await().map { sale ->
                        val matchNft = sale.nft
                        MarketSale(
                            price = "%.3f".format(sale.payment.getPrice().toFloat()),
                            url = matchNft.image_url,
                            id = matchNft.identifier,
                            name = matchNft.name,
                            token = sale.payment.symbol,
                            from = sale.seller,
                            to = sale.buyer,
                            date = Date(sale.closing_date * 1000)
                        )
                    },
                    listing = listingInfo.map {
                        val id = it.protocol_data.parameters.offer.first().identifierOrCriteria
                        val matchNft = nftListingInfos.first { nft -> nft.nft.identifier == id }.nft
                        MarketListing(
                            price = "%.3f".format(it.price.current.getPrice().toFloat()),
                            url = matchNft.image_url,
                            id = id,
                            name = matchNft.name
                        )
                    })
            }
        }

    override suspend fun getWalletCardsOwnership(wallet: List<String>): Maybe<List<CardOwnership>> =
        maybeCatching {
            withContext(Dispatchers.IO) {
                val ownership = mutableListOf<CardOwnership>()
                wallet.map { scope.async { repository.getCardOwnership(it) } }.awaitAll().forEach { list ->
                    list.forEach { card ->
                        val index = ownership.indexOfFirst { it.id == card.custom_id }
                        if (index != -1) {
                            val element = ownership[index]
                            ownership.add(
                                index, element.copy(
                                    ownedRegular = element.ownedRegular.plus(card.edition_regular),
                                    ownedShiny = element.ownedShiny.plus(card.edition_shiny)
                                )
                            )
                        } else {
                            ownership.add(card.toOwnership())
                        }
                    }
                }
                ownership
            }
        }
}

data class MarketStats(val floorPrice: String, val lastSales: List<MarketSale>, val listing: List<MarketListing>)

data class MarketSale(
    val price: String,
    val url: String,
    val id: String,
    val name: String,
    val token: String,
    val from: String,
    val to: String,
    val date: Date
)

data class MarketListing(val price: String, val url: String, val id: String, val name: String)
