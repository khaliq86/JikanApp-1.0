package com.example.jikanconnect.network

import com.example.jikanconnect.AnimeResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {
    private const val BASE_URL = "https://api.jikan.moe/v4/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun createApiService(): ApiService {
        val retrofit = createRetrofit()
        return retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("anime")
    fun getAnime(@Query("q") page: String): Call<AnimeResponse>
    @GET("anime")
    fun getAnimeStatus(@Query("status") status: String): Call<AnimeResponse>
}
