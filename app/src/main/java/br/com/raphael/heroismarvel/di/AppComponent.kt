package br.com.raphael.heroismarvel.di

import br.com.raphael.heroismarvel.App
import br.com.raphael.heroismarvel.MainActivity
import br.com.raphael.heroismarvel.di.module.AppModule
import br.com.raphael.heroismarvel.di.module.BackendModule
import br.com.raphael.heroismarvel.di.module.RemoteModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RemoteModule::class,
        BackendModule::class
    ]
)
interface AppComponent {

    fun inject(app: App)

    fun inject(activity: MainActivity)

   // fun inject(viewModel: CarrinhoViewModel)

}