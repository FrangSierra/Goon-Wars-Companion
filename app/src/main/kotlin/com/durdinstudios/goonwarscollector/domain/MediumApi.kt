package com.durdinstudios.goonwarscollector.domain

import com.durdinstudios.goonwarscollector.domain.wallet.NetworkCard
import retrofit2.Response
import retrofit2.http.GET

interface MediumApi {
    @GET("https://api.medium.com/v1/users{{authorId}}/posts")
    suspend fun getPost() : Response<List<NetworkCard>>
}