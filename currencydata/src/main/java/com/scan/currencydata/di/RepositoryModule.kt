package com.scan.currencydata.di

import com.scan.currencydata.repository.CurrencyDataSource
import com.scan.currencydata.repository.CurrencyRepo
import com.scan.currencydata.repository.CurrencyRepoImplementation
import com.scan.currencydata.repository.RemoteDataSource
import org.koin.dsl.module

val repositoryModule = module {

    // Config Repo
    factory<RemoteDataSource> { CurrencyDataSource(get()) }

    factory<CurrencyRepo> { CurrencyRepoImplementation(get(), get(), get()) }

}