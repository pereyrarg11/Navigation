package com.pereyrarg11.navigation.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pereyrarg11.navigation.R
import com.pereyrarg11.navigation.auth.domain.AuthRepository
import com.pereyrarg11.navigation.auth.domain.register.ValidateEmailUseCase
import com.pereyrarg11.navigation.auth.domain.register.ValidateNameUseCase
import com.pereyrarg11.navigation.auth.domain.register.ValidatePhoneNumberUseCase
import com.pereyrarg11.navigation.core.domain.util.AppResult
import com.pereyrarg11.navigation.core.domain.util.DataError
import com.pereyrarg11.navigation.core.presentation.tools.UiText
import com.pereyrarg11.navigation.core.presentation.tools.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val validateName: ValidateNameUseCase,
    private val validateLastName: ValidateNameUseCase,
    private val validatePhoneNumber: ValidatePhoneNumberUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val repository: AuthRepository,
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set
    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnClickSubmit -> submitForm()
            RegisterAction.OnDismissError -> dismissError()
        }
    }

    private fun dismissError() {
        state = state.copy(
            isBadRequest = false,
            badRequestMessage = null,
        )
    }

    private fun submitForm() {
        validateForm()
        if (state.isValidName && state.isValidLastName && state.isValidEmail && state.isValidPhoneNumber) {
            registerUser()
        }
    }

    private fun registerUser() {
        viewModelScope.launch {
            state = state.copy(
                isSubmitting = true,
            )
            val result = repository.registerUser(
                name = state.name.text.toString().trim(),
                lastName = state.lastName.text.toString().trim(),
                phoneNumber = state.phoneNumber.text.toString().trim(),
                email = state.email.text.toString().trim(),
            )
            state = state.copy(
                isSubmitting = false,
            )
            when (result) {
                is AppResult.Error -> {
                    val message = when (result.error) {
                        DataError.Network.CONFLICT -> UiText.StringResource(R.string.auth_register_error_duplicated)
                        else -> result.error.toUiText()
                    }
                    state = state.copy(
                        isBadRequest = true,
                        badRequestMessage = message,
                    )
                }

                is AppResult.Success -> {
                    eventChannel.send(RegisterEvent.RegistrationSuccess)
                }
            }
        }
    }

    private fun validateForm() {
        val nameValidation = validateName(state.name.text.toString().trim())
        val lastNameValidation = validateLastName(state.lastName.text.toString().trim())
        val phoneNumberValidation = validatePhoneNumber(state.phoneNumber.text.toString().trim())
        val emailValidation = validateEmail(state.email.text.toString().trim())
        state = state.copy(
            isValidName = nameValidation == null,
            nameError = nameValidation?.toUiText(),
            isValidLastName = lastNameValidation == null,
            lastNameError = lastNameValidation?.toUiText(),
            isValidEmail = emailValidation == null,
            emailError = emailValidation?.toUiText(),
            isValidPhoneNumber = phoneNumberValidation == null,
            phoneNumberError = phoneNumberValidation?.toUiText(),
        )
    }
}
