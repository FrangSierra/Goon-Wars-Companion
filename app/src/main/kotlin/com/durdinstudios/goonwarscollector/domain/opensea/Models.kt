package com.durdinstudios.goonwarscollector.domain.opensea

import kotlin.math.abs

data class NetworkNftModel(
    val identifier: String,
    val collection: String,
    val contract: String,
    val token_standard: String,
    val name: String,
    val description: String,
    val image_url: String,
    val metadata_url: String,
    val is_disabled: Boolean,
    val is_nsfw: Boolean,
)

data class NetworkPayment(val quantity: String, val token_address: String, val decimals: Int, val symbol: String) {
    fun getPrice(): String {
        val decimals = decimals - quantity.length
        return when {
            decimals > 0 -> "0.".plus(arrayOf(abs(decimals)).joinToString { "0" }
                .plus(quantity))

            else -> String(quantity.toMutableList()
                .apply { this.add(abs(decimals), '.') }
                .toCharArray())

        }
    }
}

data class NetworkListPrice(val currency: String, val decimals: Long, val value: String) {
    fun getPrice(): String {
        val decimals = decimals - value.length
        return when {
            decimals > 0 -> "0.".plus(arrayOf(abs(decimals)).joinToString { "0" }
                .plus(value))

            else -> String(value.toMutableList()
                .apply { this.add(abs(decimals.toInt()), '.') }
                .toCharArray())

        }
    }
}
data class NetworkListCurrency(val current: NetworkListPrice)
data class NetworkListing(
    val order_hash: String,
    val chain: String,
    val type: String,
    val price: NetworkListCurrency,
    val protocol_data: NetworkListingProtocolData
)

data class NetworkListingProtocolData(val parameters: NetworkListingParameters)
data class NetworkListingParameters(
    val offerer: String,
    val offer: List<NetworkListingOffer>,
    val startTime: String,
    val endTime: String
)

data class NetworkListingOffer(
    val itemType: String,
    val token: String,
    val identifierOrCriteria: String,
    val startAmount: String,
    val endAmount: String
)

data class NetworkListingResponse(val listings: List<NetworkListing>, val next: String?)
data class NetworkCollectionOrderResponse(val asset_events: List<OrderEvent>, val next: String?)
data class NetworkCollectionSalesResponse(val asset_events: List<SaleEvent>, val next: String?)

data class NetworkCollectionEvent(
    val event_type: String,
    val order_hash: String,
    val chain: String,
    val protocol_address: String,
    val closing_date: Long,
    val nft: NetworkNftModel,
    val quantity: Int,
    val seller: String,
    val buyer: String,
    val payment: NetworkPayment,
    val transaction: String
)

data class SaleEvent(
    val event_type: String,
    val order_hash: String,
    val protocol_address: String,
    val closing_date: Long,
    val nft: NetworkNftModel,
    val quantity: Long,
    val seller: String,
    val buyer: String,
    val payment: NetworkPayment,
    val event_timestamp: Long,
    val transaction: String
)

data class OrderEvent(
    val event_type: String,
    val order_hash: String,
    val order_type: String,
    val chain: String,
    val protocol_address: String,
    val start_date: Long,
    val expiration_date: Long,
    val asset: NetworkNftModel,
    val quantity: Long,
    val maker: String,
    val taker: String,
    val payment: NetworkPayment,
    val event_timestamp: Long,
    val is_private_listing: Boolean
)

data class CancelEvent(
    val event_type: String,
    val order_hash: String,
    val maker: String,
    val event_timestamp: Long,
    val nft: NetworkNftModel,
)

data class NetworkTotalStats(
    val volume: Double,
    val sales: Int,
    val average_price: Double,
    val num_owners: Int,
    val market_cap: Double,
    val floor_price: Double,
    val floor_price_symbol: String
)

data class NetworkCollectionStats(val total: NetworkTotalStats)