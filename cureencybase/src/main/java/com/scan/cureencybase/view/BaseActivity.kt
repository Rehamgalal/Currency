package com.scan.cureencybase.view

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.gturedi.views.StatefulLayout
import com.scan.cureencybase.R

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseActivity<DB : ViewDataBinding, STATE : UiState, VM : BaseViewModel<STATE>> :
    AppCompatActivity() {


    @LayoutRes
    abstract fun getLayoutRes(): Int


    lateinit var viewModel: VM

    abstract fun getVM(): VM

    private var _binding: DB? = null

    protected val binding: DB
        get() {
            return _binding ?: throw IllegalStateException(
                "data binding should not be null"
            )
        }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, getLayoutRes()) as DB
        viewModel = getVM()

        setGenericToolBar()
        subscribeBaseEvents()
        onViewAttach()
        subscribeUiState()
    }

    private fun subscribeUiState() {
        viewModel.uiStateFlow
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                renderState(it)
            }
            .launchIn(lifecycleScope)
    }

    abstract fun renderState(it: STATE)

    private fun subscribeBaseEvents() {
        viewModel.errorMsgSingleEventFlow
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                // applicationContext not activity context to avoid activity leak on back pressed while toast is still showing
                when (it) {
                    is BaseViewModel.ErrorMessage.StringErrorMessage -> Toast.makeText(
                        applicationContext,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    is BaseViewModel.ErrorMessage.StringResErrorMessage -> Toast.makeText(
                        applicationContext,
                        it.messageResId,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun onStart() {
        super.onStart()
        onViewRefresh()
    }

    // OnStart
    open fun onViewRefresh() {

    }

    // OnCreate
    open fun onViewAttach() {

    }

    abstract fun getToolbarTitle(): Any?

    private fun setGenericToolBar() {

        val title = getToolbarTitle()

        title?.let {

            val toolbar = binding.root.findViewById<Toolbar>(R.id.toolbar)

            setSupportActionBar(toolbar)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val titleTxt = when (it) {
                is String -> it
                is Int -> resources.getString(it)
                else -> throw UnsupportedOperationException("Title must be a string resource or a plain string")
            }

            supportActionBar?.title = titleTxt
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)

    }

    fun showLoading(stateFullLayout: StatefulLayout) {
        stateFullLayout.showLoading()
    }

    fun showEmpty(stateFullLayout: StatefulLayout) {
        stateFullLayout.showEmpty()
    }

    fun showError(stateFullLayout: StatefulLayout, error: String?, retry: () -> Unit) {
        stateFullLayout.showError(error ?: getString(R.string.something_went_wrong)) { retry() }
    }

    fun showContent(stateFullLayout: StatefulLayout) {
        stateFullLayout.showContent()
    }

}
