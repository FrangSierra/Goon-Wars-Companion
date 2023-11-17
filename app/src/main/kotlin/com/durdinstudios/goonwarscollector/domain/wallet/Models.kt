package com.durdinstudios.goonwarscollector.domain.wallet

import androidx.annotation.Keep

@Keep
data class CardOwnership(
    val id: String,
    val ownedRegular: Int,
    val ownedShiny: Int
)

@Keep
data class GobCard(
    val id: String,
    val name: String,
    val series: String,
    val element: String,
    val rarity: String,
    val type: String,
    val attack: Int,
    val health: Int,
    val ability: String,
    val regularSupply: String,
    val shinySupply: String,
    val regularNftUrl: String,
    val shinyNftUrl: String
) {
    override fun toString(): String {
        return "$id-$name"
    }
}

fun NetworkCardOwnership.toOwnership() = CardOwnership(custom_id, edition_regular, edition_shiny)

@Keep
fun NetworkCard.toGobCard() = GobCard(
    custom_id,
    name,
    series,
    element,
    rarity,
    type,
    attack,
    health,
    ability,
    regular_supply,
    shiny_supply,
    regular_nft_url,
    shiny_nft_url
)