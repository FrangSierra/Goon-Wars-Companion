package com.durdinstudios.goonwarscollector.domain

import com.durdinstudios.goonwarscollector.domain.wallet.NetworkCard
import com.durdinstudios.goonwarscollector.domain.wallet.NetworkCardOwnership
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

typealias CardsResponse = Response<List<NetworkCard>>
typealias EmptyResponse = Response<Unit>

interface GoonsApi {
    @GET("/token/cards")
    suspend fun getCards(): List<NetworkCard>

    @GET("api/assets/cards/u/{wallet}")
    suspend fun getCardsOwnership(@Path("wallet") wallet: String): List<NetworkCardOwnership>
}