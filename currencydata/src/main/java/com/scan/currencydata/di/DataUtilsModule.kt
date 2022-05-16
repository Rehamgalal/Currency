package com.scan.currencydata.di

import com.scan.cureencybase.utils.NetworkConnectivityHelper
import org.koin.dsl.module

val  DataUtilsModule = module {
    single { NetworkConnectivityHelper() }
}