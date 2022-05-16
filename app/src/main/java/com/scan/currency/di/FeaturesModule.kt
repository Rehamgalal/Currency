package com.scan.currency.di

import com.scan.currency.presentation.CurrenctViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featuresModule = module {

    viewModel { CurrenctViewModel(get(), get()) }

}