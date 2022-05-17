package com.scan.currency

import android.widget.ArrayAdapter
import com.scan.cureencybase.view.BaseActivity
import com.scan.currency.databinding.ActivityMainBinding
import com.scan.currency.presentation.CurrenctViewModel
import com.scan.currency.presentation.CurrencyConversionState
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<ActivityMainBinding,CurrencyConversionState,CurrenctViewModel>() {

    override fun onViewAttach() {
        super.onViewAttach()
        setupSpinners()
        attachListener()
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

    private fun setupSpinners() {
        val currencies = resources.getStringArray(R.array.currencies)
        val fromAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)
        val toAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)
        binding.firstCurrency.adapter = fromAdapter
        binding.secondCurrency.adapter = toAdapter

    }

    private fun attachListener() {
        binding.details.setOnClickListener {
            viewModel.convert(
                binding.firstCurrency.selectedItem.toString(),
                binding.firstCurrencyInput.text.toString().toDouble(),
                binding.secondCurrency.selectedItem.toString()
            )
        }
    }
}