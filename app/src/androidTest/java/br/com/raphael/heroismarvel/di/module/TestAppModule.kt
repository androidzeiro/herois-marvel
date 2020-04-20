package br.com.raphael.heroismarvel.di.module

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import br.com.raphael.heroismarvel.App

class TestAppModule(app : App): AppModule(app) {
    override fun provideContext(): Context =
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
}