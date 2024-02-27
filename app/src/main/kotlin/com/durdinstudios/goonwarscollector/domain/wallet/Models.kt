package com.durdinstudios.goonwarscollector.domain.wallet

import androidx.annotation.Keep
import com.durdinstudios.goonwarscollector.domain.opensea.NetworkNftInfo

@Keep
enum class Rarity {
    Common, Rare, Epic, Legendary
}

@Keep
enum class Type {
    Spell, Mystery, Creature
}

@Keep
enum class Element {
    Fire, Water, Earth, Electric, Neutral
}

@Keep
data class CardOwnership(
    val id: String,
    val ownedRegular: Int,
    val ownedShiny: Int
)

@Keep
data class Nft(
    val id: String,
    val name: String,
    val imageUrl: String,
    val collection: String
)

@Keep
data class GobCard(
    val id: String,
    val name: String,
    val series: String,
    val element: Element,
    val rarity: Rarity,
    val type: Type,
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
    Element.valueOf(element),
    Rarity.valueOf(rarity),
    Type.valueOf(type),
    attack,
    health,
    ability,
    regular_supply,
    shiny_supply,
    regular_nft_url,
    shiny_nft_url
)

@Keep
fun NetworkNftInfo.toNft() = Nft(
    name = this.name!!,
    id = this.identifier!!,
    imageUrl = this.image_url!!,
    collection = this.collection!!
)