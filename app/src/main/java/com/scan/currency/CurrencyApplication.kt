package com.scan.currency

import android.app.Application
import androidx.databinding.library.BuildConfig
import com.scan.currency.di.featuresModule
import com.scan.currency.di.utilsModule
import com.scan.currencydata.di.DataUtilsModule
import com.scan.currencydata.di.networkModule
import com.scan.currencydata.di.repositoryModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CurrencyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@CurrencyApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    featuresModule,
                    utilsModule,
                    DataUtilsModule
                )
            )
        }
    }
}