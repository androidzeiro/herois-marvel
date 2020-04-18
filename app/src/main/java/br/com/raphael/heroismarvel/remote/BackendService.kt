package br.com.raphael.heroismarvel.remote

import br.com.raphael.heroismarvel.model.Heroi
import retrofit2.http.GET

interface BackendService {
    @GET("v1/public/characters?ts=1587161371&apikey=c49dc91d685059db51865942bb18db4d&hash=9c455fecf6436f7ff6e8a842f05c7987")
    suspend fun getBanners(): List<Heroi>
}