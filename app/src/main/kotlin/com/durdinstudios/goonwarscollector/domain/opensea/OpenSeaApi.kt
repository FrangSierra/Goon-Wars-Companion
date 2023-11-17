package com.durdinstudios.goonwarscollector.domain.opensea

import org.intellij.lang.annotations.Identifier
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenSeaApi {
    @GET("api/v2/collections/goonsofbalatroon/stats")
    suspend fun getCollectionStats(): NetworkCollectionStats

    @GET("api/v2/events/collection/goonsofbalatroon")
    suspend fun getCollectionSales(
        @Query("limit") limit: Int = 3,
        @Query("event_type") filter: String = "sale"
    ): NetworkCollectionSalesResponse

    @GET("api/v2/chain/ethereum/contract/0x8442dd3e5529063b43c69212d64d5ad67b726ea6/nfts/{identifier}")
    suspend fun getNftInfo(@Path("identifier") identifier: String): NetworkNftInfoResponse

    @GET("api/v2/listings/collection/goonsofbalatroon/all")
    suspend fun getCollectionListing(
        @Query("limit") limit: Int = 100,
        @Query("next") next: String?
    ): NetworkListingResponse
}

data class NetworkNftInfoResponse(val nft: NetworkNftInfo)

data class NetworkNftInfo(
    val identifier: String,
    val name: String,
    val image_url: String
)