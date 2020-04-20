package br.com.raphael.heroismarvel.di

import br.com.raphael.heroismarvel.di.module.AppModule
import br.com.raphael.heroismarvel.di.module.BackendModule
import br.com.raphael.heroismarvel.di.module.RemoteModule
import dagger.Component
import br.com.raphael.heroismarvel.ui.DetalhesHeroiFragmentTest
import br.com.raphael.heroismarvel.ui.ListagemHeroisFragmentTest
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (BackendModule::class), (RemoteModule::class)])
interface TestComponent : AppComponent {
    fun inject(test: ListagemHeroisFragmentTest)
    fun inject(test: DetalhesHeroiFragmentTest)
}