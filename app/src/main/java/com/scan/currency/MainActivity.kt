package com.scan.currency

import com.scan.cureencybase.view.BaseActivity
import com.scan.currency.databinding.ActivityMainBinding
import com.scan.currency.presentation.CurrenctViewModel
import com.scan.currency.presentation.CurrencyConversionState
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<ActivityMainBinding,CurrencyConversionState,CurrenctViewModel>() {

    override fun onViewAttach() {
        super.onViewAttach()
        binding.details.setOnClickListener {
            viewModel.convert(binding.firstCurrency.text.toString(),binding.firstCurrencyInput.text.toString().toDouble(),binding.secondCurrency.text.toString())
        }
    }
    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun getVM(): CurrenctViewModel = getViewModel()
    override fun renderState(it: CurrencyConversionState) {
        when {
            it.success -> {
                binding.secondCurrencyInput.setText(it.currencyResponse?.result.toString())
            }
        }
    }

    override fun getToolbarTitle(): Any? = null

}