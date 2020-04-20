package br.com.raphael.heroismarvel

import br.com.raphael.heroismarvel.di.*
import br.com.raphael.heroismarvel.di.module.*

class TestApp : App() {
    override val component: AppComponent by lazy {
        DaggerTestComponent.builder()
            .appModule(TestAppModule(this))
            .backendModule(BackendModule())
            .remoteModule(RemoteModule(this))
            .build()
    }
}