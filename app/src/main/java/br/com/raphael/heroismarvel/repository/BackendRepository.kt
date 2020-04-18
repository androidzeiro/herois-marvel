package br.com.raphael.heroismarvel.repository

import android.content.SharedPreferences
import br.com.raphael.heroismarvel.model.Heroi
import br.com.raphael.heroismarvel.remote.BackendService
import javax.inject.Inject

class BackendRepository @Inject constructor(
    private val backendService: BackendService,
    private val preferences: SharedPreferences
) {

    suspend fun getBannersAsync(): List<Heroi> =
        backendService.getBanners()
}
