package com.durdinstudios.goonwarscollector.domain.opensea

import com.durdinstudios.goonwarscollector.domain.wallet.CardOwnership
import com.prof18.rssparser.RssParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.Date

class OpenSeaRepository(private val api: OpenSeaApi) : CoroutineScope by CoroutineScope(Job()) {

    suspend fun getCollectionStats() = api.getCollectionStats()
    suspend fun getCollectionLastSales(limit : Int) = api.getCollectionSales(limit)
    suspend fun getCollectionListing(next: String? = "") = api.getCollectionListing(next = next)
    suspend fun getNftInfo(id: String) = api.getNftInfo(identifier = id)
}
