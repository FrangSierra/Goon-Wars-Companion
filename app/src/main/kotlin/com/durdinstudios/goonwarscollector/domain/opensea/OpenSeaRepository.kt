package com.durdinstudios.goonwarscollector.domain.opensea

import com.durdinstudios.goonwarscollector.domain.wallet.CardOwnership
import com.prof18.rssparser.RssParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.Date

const val CHAIN_ETH = "ethereum"

class OpenSeaRepository(private val api: OpenSeaApi) : CoroutineScope by CoroutineScope(Job()) {

    suspend fun getCollectionStats() = api.getCollectionStats()
    suspend fun getCollectionLastSales(limit: Int) = api.getCollectionSales(limit)
    suspend fun getCollectionOrders(limit: Int) = api.getCollectionSales(limit, filter = "")
    suspend fun getCollectionListing(next: String? = "") = api.getCollectionListing(next = next)
    suspend fun getWalletGoons(
        address: String,
        limit: Int = 5,
        next: String? = ""
    ) : NetworkNftListingResponse=
        api.getWalletNfts(
            address = address,
            limit = limit,
            collection = "goonsofbalatroon",
            chain = CHAIN_ETH,
            next = next
        )

    suspend fun getWalletLands(
        address: String,
        limit: Int = 5,
        next: String? = ""
    ) : NetworkNftListingResponse=
        api.getWalletNfts(
            address = address,
            limit = limit,
            collection = "goonbods",
            chain = CHAIN_ETH,
            next = next
        )

    suspend fun getWalletBods(
        address: String,
        limit: Int = 5,
        next: String? = ""
    ): NetworkNftListingResponse=
        api.getWalletNfts(
            address = address,
            limit = limit,
            collection = "goonbods",
            chain = CHAIN_ETH,
            next = next
        )

    suspend fun getNftInfo(id: String) = api.getNftInfo(identifier = id)
}
