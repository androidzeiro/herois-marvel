package br.com.raphael.heroismarvel.repository

import android.content.Context
import br.com.raphael.heroismarvel.App
import br.com.raphael.heroismarvel.BuildConfig
import br.com.raphael.heroismarvel.extensions.fromJson
import br.com.raphael.heroismarvel.model.Personagem
import br.com.raphael.heroismarvel.model.Response
import br.com.raphael.heroismarvel.remote.BackendService
import com.google.gson.Gson
import javax.inject.Inject

class BackendRepository @Inject constructor(
    private val backendService: BackendService,
    private val app: App
) {

    suspend fun getHeroisAsync(): Response {
        return backendService.getHerois(BuildConfig.TS, BuildConfig.APIKEY, BuildConfig.HASH)
    }

    suspend fun getHeroiAsync(id: Int): Response {
        return backendService.getHeroi(id, BuildConfig.TS, BuildConfig.APIKEY, BuildConfig.HASH)
    }

    private fun readAsset(context: Context, fileName: String): String =
        context.assets.open(fileName).bufferedReader().use { it.readText() }

    fun getAvengers(): List<Personagem>? =
        Gson().fromJson<List<Personagem>>(readAsset(app, "avengers.json"))
}
