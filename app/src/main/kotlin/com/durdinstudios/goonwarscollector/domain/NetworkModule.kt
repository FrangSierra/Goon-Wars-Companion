package com.durdinstudios.goonwarscollector.domain

import android.R
import android.content.res.Resources
import com.durdinstudios.goonwarscollector.domain.opensea.OpenSeaApi
import com.durdinstudios.goonwarscollector.domain.opensea.OpenSeaRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DataModule {
    fun create() = DI.Module("DataModule") {
        bind<Moshi>() with singleton { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }

        bind<GoonsApi>() with singleton {
            Retrofit.Builder()
                .client(OkHttpClient.Builder().build())
                // .baseUrl(BuildConfig.API_HOST)
                .baseUrl("https://api.balatroon.io")
                .addConverterFactory(MoshiConverterFactory.create(instance()))
                .build().create(GoonsApi::class.java)
        }

        bind<OpenSeaApi>() with singleton {
            Retrofit.Builder()
                .client(OkHttpClient.Builder().addInterceptor(getAuthInterceptor()).build())
                // .baseUrl(BuildConfig.API_HOST)
                .baseUrl("https://api.opensea.io")
                .addConverterFactory(MoshiConverterFactory.create(instance()))
                .build().create(OpenSeaApi::class.java)
        }

        bind<GoonsRepository>() with singleton { GoonsRepository(instance()) }
        bind<OpenSeaRepository>() with singleton { OpenSeaRepository(instance()) }

    }

    private fun getAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original: Request = chain.request()
            val hb: Headers.Builder = original.headers.newBuilder()
            hb.add("x-api-key", "OPEN-SEA-TOKEN")
            chain.proceed(original.newBuilder().headers(hb.build()).build())
        }
    }
}