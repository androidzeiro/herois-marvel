package br.com.raphael.heroismarvel.di.module

import br.com.raphael.heroismarvel.App
import br.com.raphael.heroismarvel.remote.util.PrintingEventListener
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class RemoteModule(private val app: App) {

    companion object {
        private const val CACHE_SIZE = (5 * 1024 * 1024).toLong() // 5MB
        private const val CACHE_TIME = 60 * 60 * 24 * 7 // 7 dias
    }

    private val cache = Cache(app.applicationContext.cacheDir, CACHE_SIZE)

    @Provides
    @Singleton
    fun providesOkHttpClientBuilder(): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .protocols(arrayListOf(Protocol.HTTP_1_1))
            .eventListenerFactory {
                PrintingEventListener(
                    PrintingEventListener.nextCallId.getAndIncrement(),
                    System.nanoTime()
                )
            }

    @Provides
    @Singleton
    fun provideOkHttp(
        builder: OkHttpClient.Builder, logging: HttpLoggingInterceptor
    ): OkHttpClient {

        val b = builder
        return b.addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()

            requestBuilder.header("Cache-Control", "private, max-age=1")

            chain.proceed(requestBuilder.build())
        }
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}