package br.com.raphael.heroismarvel.remote

import br.com.raphael.heroismarvel.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BackendService {
    @GET("v1/public/characters")
    suspend fun getHerois(
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Response

    @GET("v1/public/characters/{id}")
    suspend fun getHeroi(
        @Path("id") id: Int,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Response
}