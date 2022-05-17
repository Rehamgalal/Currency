package com.scan.currencydata.di

import com.scan.currencydata.BuildConfig
import com.scan.currencydata.network.api.Api
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    fun provideOkHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    fun provideAccessKeyInterceptor() = Interceptor {
        var original = it.request()
        val url = original.url.newBuilder()
            .addQueryParameter("access_key", "3iZ32BS1RGtBXdn71CLm745hEvW3NRFf").build()
        original = it.request().newBuilder().url(url).build()
        return@Interceptor it.proceed(original)
    }
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        accessKeyInterceptor: Interceptor,
        timeout: Long
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(accessKeyInterceptor)
            .build()
    }

    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single(named(DI_BASE_URL)) { BASE_URL }

    single { provideOkHttpLoggingInterceptor() }

    single { provideAccessKeyInterceptor() }

    single { (timeout: Long) -> provideOkHttpClient(get(),get(), timeout) }

    single(named(DI_RETROFIT)) { (timeout: Long) ->
        provideRetrofit(
            get(named(DI_BASE_URL)),
            get(parameters = { parametersOf(timeout) })
        )
    }

    factory {
        get<Retrofit>(
            named(DI_RETROFIT),
            parameters = { parametersOf(DEFAULT_NETWORK_TIMEOUT_SECONDS) }
        ).create(Api::class.java)
    }
}
    const val DI_BASE_URL = "BASE_URL"
    const val BASE_URL = "https://data.fixer.io/api/"
    const val DI_RETROFIT = "RETROFIT"
    const val DEFAULT_NETWORK_TIMEOUT_SECONDS = 60L

