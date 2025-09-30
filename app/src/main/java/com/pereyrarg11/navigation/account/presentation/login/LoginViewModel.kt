package com.pereyrarg11.navigation.account.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pereyrarg11.navigation.account.domain.AuthRepository
import com.pereyrarg11.navigation.account.domain.signin.ValidatePhoneNumberUseCase
import com.pereyrarg11.navigation.account.presentation.signin.toUiText
import com.pereyrarg11.navigation.core.domain.util.AppResult
import com.pereyrarg11.navigation.core.domain.util.DataError
import com.pereyrarg11.navigation.core.presentation.tools.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validatePhoneNumber: ValidatePhoneNumberUseCase,
    private val repository: AuthRepository,
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set
    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnClickSubmit -> submitForm()
        }
    }

    private fun submitForm() {
        val phoneNumberValidation = validatePhoneNumber(state.phoneNumber.text.toString().trim())
        state = state.copy(
            isValidPhoneNumber = phoneNumberValidation == null,
            phoneNumberError = phoneNumberValidation?.toUiText(),
        )
        if (state.isValidPhoneNumber) {
            viewModelScope.launch {
                getTokenByPhoneNumber()
            }
        }
    }

    private suspend fun getTokenByPhoneNumber() {
        state = state.copy(
            isSubmitting = true,
        )
        val result = repository.getTokenByPhoneNumber(
            phoneNumber = ValidatePhoneNumberUseCase.PHONE_NUMBER_PREFIX + state.phoneNumber.text.toString()
                .trim(),
        )
        when (result) {
            is AppResult.Error -> onResultError(result.error)
            is AppResult.Success -> startSession()
        }
    }

    private suspend fun startSession() {
        when (val result = repository.getUser()) {
            is AppResult.Error -> onResultError(result.error)
            is AppResult.Success -> {
                // change UI state
                state = state.copy(
                    isSubmitting = false,
                )
                // notify to channel observers
                eventChannel.send(LoginEvent.LoginSuccess)
            }
        }
    }

    private suspend fun onResultError(error: DataError.Network) {
        state = state.copy(isSubmitting = false)
        eventChannel.send(LoginEvent.ShowError(error.toUiText()))
    }
}
