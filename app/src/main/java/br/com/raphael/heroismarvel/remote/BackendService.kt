package br.com.raphael.heroismarvel.remote

import br.com.raphael.heroismarvel.model.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BackendService {
    @GET("v1/public/characters?ts=1587161371&apikey=c49dc91d685059db51865942bb18db4d&hash=9c455fecf6436f7ff6e8a842f05c7987")
    suspend fun getHerois(): Response

    @GET("v1/public/characters/{id}?ts=1587161371&apikey=c49dc91d685059db51865942bb18db4d&hash=9c455fecf6436f7ff6e8a842f05c7987")
    suspend fun getHeroi(
        @Path("id") id: Int
    ): Response
}