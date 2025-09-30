package com.pereyrarg11.navigation.account.presentation.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pereyrarg11.navigation.account.domain.AuthRepository
import com.pereyrarg11.navigation.account.domain.signin.User
import com.pereyrarg11.navigation.account.domain.signin.ValidateEmailUseCase
import com.pereyrarg11.navigation.account.domain.signin.ValidateNameUseCase
import com.pereyrarg11.navigation.account.domain.signin.ValidatePhoneNumberUseCase
import com.pereyrarg11.navigation.core.domain.util.AppResult
import com.pereyrarg11.navigation.core.domain.util.DataError
import com.pereyrarg11.navigation.core.presentation.tools.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SigninViewModel(
    private val validateName: ValidateNameUseCase,
    private val validateLastName: ValidateNameUseCase,
    private val validatePhoneNumber: ValidatePhoneNumberUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val repository: AuthRepository,
) : ViewModel() {
    var state by mutableStateOf(SigninState())
        private set
    private val eventChannel = Channel<SigninEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: SigninAction) {
        when (action) {
            SigninAction.OnClickSubmit -> submitForm()
        }
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
                phoneNumber = ValidatePhoneNumberUseCase.PHONE_NUMBER_PREFIX + state.phoneNumber.text.toString()
                    .trim(),
                email = state.email.text.toString().trim(),
            )
            when (result) {
                is AppResult.Error -> onResultError(result.error)
                is AppResult.Success -> onRegisterUserSuccess(result.data)
            }
        }
    }

    private suspend fun onResultError(error: DataError.Network) {
        state = state.copy(isSubmitting = false)
        eventChannel.send(SigninEvent.ShowError(error.toUiText()))
    }

    /**
     * Once user has been registered successfully, then request an OTP.
     */
    private suspend fun onRegisterUserSuccess(user: User) {
        when (val result = repository.sendSms(user.phone)) {
            is AppResult.Error -> onResultError(result.error)
            is AppResult.Success -> {
                // change status
                state = state.copy(
                    isSubmitting = false,
                )
                // notify to channel observers
                eventChannel.send(
                    SigninEvent.RegistrationSuccess(
                        phoneNumber = state.phoneNumber.text.toString().trim()
                    )
                )
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
