package com.scan.cureencybase.view

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scan.cureencybase.utils.AppGeneralEventsBus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : UiState>(
    appGeneralEventsBus: AppGeneralEventsBus,
    defaultUiState: STATE
) : ViewModel() {

    val appEventsFlow = appGeneralEventsBus.eventsFlow

    private val _uiStateFlow = MutableStateFlow(defaultUiState)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _errorMsgSingleEventFlow = MutableSharedFlow<ErrorMessage>()
    val errorMsgSingleEventFlow = _errorMsgSingleEventFlow.asSharedFlow()

    fun sendErrorMsgSingleEvent(message: String?) {
        message?.let {
            viewModelScope.launch {
                _errorMsgSingleEventFlow.emit(ErrorMessage.StringErrorMessage(message))
            }
        }
    }

    fun sendErrorMsgSingleEvent(@StringRes messageResId: Int) {
        viewModelScope.launch {
            _errorMsgSingleEventFlow.emit(ErrorMessage.StringResErrorMessage(messageResId))
        }
    }

    fun updateState(state: STATE) {
        _uiStateFlow.value = state
    }

    fun currentState() = uiStateFlow.value


    sealed class ErrorMessage {
        data class StringErrorMessage(val message: String) : ErrorMessage()
        data class StringResErrorMessage(@StringRes val messageResId: Int) : ErrorMessage()
    }

}
