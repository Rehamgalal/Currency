package com.scan.currency.di

import com.scan.cureencybase.utils.AppGeneralEventsBus
import org.koin.dsl.module

val utilsModule = module {

    single { AppGeneralEventsBus() }
}