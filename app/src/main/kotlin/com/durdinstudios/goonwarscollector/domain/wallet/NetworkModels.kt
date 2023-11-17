package com.durdinstudios.goonwarscollector.domain.wallet

import androidx.annotation.Keep

@Keep
data class NetworkCardOwnership(val custom_id: String, val edition_shiny: Int, val edition_regular: Int)

@Keep
data class NetworkCard(
    val custom_id: String,
    val name: String,
    val series: String,
    val element: String,
    val rarity: String,
    val type: String,
    val attack: Int,
    val health: Int,
    val ability: String,
    val regular_supply: String,
    val shiny_supply: String,
    val regular_nft_url: String,
    val shiny_nft_url: String
)

