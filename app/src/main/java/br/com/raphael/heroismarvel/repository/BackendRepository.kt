package br.com.raphael.heroismarvel.repository

import br.com.raphael.heroismarvel.model.Response
import br.com.raphael.heroismarvel.remote.BackendService
import javax.inject.Inject

class BackendRepository @Inject constructor(
    private val backendService: BackendService
) {

    suspend fun getHeroisAsync(): Response =
        backendService.getHerois()

    suspend fun getHeroiAsync(id: Int): Response =
        backendService.getHeroi(id)
}
