package br.com.raphael.heroismarvel.di.module

import br.com.raphael.heroismarvel.remote.BackendService
import org.mockito.Mockito
import retrofit2.Retrofit

class TestBackendModule : BackendModule() {
    override fun providesBackendService(retrofit: Retrofit): BackendService =
        Mockito.mock(BackendService::class.java)
}