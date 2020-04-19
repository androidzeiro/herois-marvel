package br.com.raphael.heroismarvel.di.module

import br.com.raphael.heroismarvel.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule (private val app: App) {

    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    open fun provideContext() = app.applicationContext!!

    @Provides
    @Singleton
    open fun provideResources() = app.resources!!
}