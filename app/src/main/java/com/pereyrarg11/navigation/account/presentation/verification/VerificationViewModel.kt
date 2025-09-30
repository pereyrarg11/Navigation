package com.pereyrarg11.navigation.account.presentation.verification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pereyrarg11.navigation.account.domain.AuthRepository
import com.pereyrarg11.navigation.account.domain.verification.ValidateOtpUseCase
import com.pereyrarg11.navigation.account.presentation.signin.toUiText
import com.pereyrarg11.navigation.core.domain.util.AppResult
import com.pereyrarg11.navigation.core.domain.util.DataError
import com.pereyrarg11.navigation.core.presentation.tools.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class VerificationViewModel(
    private val phoneNumber: String,
    private val validateCode: ValidateOtpUseCase,
    private val repository: AuthRepository,
) : ViewModel() {
    var state by mutableStateOf(VerificationState())
        private set
    private val eventChannel = Channel<VerificationEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: VerificationAction) {
        when (action) {
            is VerificationAction.OnChangeFieldFocused -> changeFieldFocus(action.index)
            VerificationAction.OnClickSubmit -> onClickSubmit()
            is VerificationAction.OnEnterDigit -> onEnterDigit(action.digit, action.index)
            VerificationAction.OnKeyboardBack -> onKeyboardBack()
        }
    }

    private fun changeFieldFocus(focusedIndex: Int) {
        state = state.copy(
            focusedIndex = focusedIndex,
        )
    }

    private fun onClickSubmit() {
        validateForm()
        if (state.isValidCode == true) {
            validateOtp()
        }
    }

    private fun onEnterDigit(digit: Int?, atIndex: Int) {
        val newCode = state.code.mapIndexed { index, currentDigit ->
            if (atIndex == index) {
                digit
            } else {
                currentDigit
            }
        }
        val wasDigitRemoved = digit == null
        state = state.copy(
            code = newCode,
            focusedIndex = if (wasDigitRemoved || state.code.getOrNull(atIndex) != null) {
                state.focusedIndex
            } else {
                getNextFocusedTextFieldIndex(
                    currentCode = state.code,
                    currentFocusedIndex = state.focusedIndex
                )
            },
        )
    }

    private fun onKeyboardBack() {
        val previousIndex = getPreviousFocusedIndex(state.focusedIndex)
        state = state.copy(
            code = state.code.mapIndexed { index, digit ->
                if (index == previousIndex) {
                    null
                } else {
                    digit
                }
            },
            focusedIndex = previousIndex,
        )
    }

    private fun validateForm() {
        val codeValidation = validateCode(state.code)
        state = state.copy(
            isValidCode = codeValidation == null,
            codeErrorMessage = codeValidation?.toUiText(),
        )
    }

    private fun validateOtp() {
        viewModelScope.launch {
            state = state.copy(
                isSubmitting = true,
            )
            val result = repository.getTokenByOtp(
                code = state.code.filterNotNull().joinToString(""),
                phoneNumber = phoneNumber
            )
            when (result) {
                is AppResult.Error -> onResultError(result.error)
                is AppResult.Success -> startSession()
            }
        }
    }

    private suspend fun startSession() {
        when (val result = repository.getUser()) {
            is AppResult.Error -> onResultError(result.error)
            is AppResult.Success -> {
                state = state.copy(
                    isSubmitting = false,
                )
                eventChannel.send(VerificationEvent.SuccessfulVerification)
            }
        }
    }

    private suspend fun onResultError(error: DataError.Network) {
        state = state.copy(isSubmitting = false)
        eventChannel.send(VerificationEvent.ShowError(error.toUiText()))
    }

    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedTextFieldIndex(
        currentCode: List<Int?>,
        currentFocusedIndex: Int?,
    ): Int? {
        if (currentFocusedIndex == null) {
            return null
        }
        if (currentFocusedIndex == currentCode.lastIndex) {
            return currentFocusedIndex
        }
        return getFirstEmptyFieldIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        code: List<Int?>,
        currentFocusedIndex: Int,
    ): Int {
        code.forEachIndexed { index, digit ->
            if (index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if (digit == null) {
                return index
            }
        }
        return currentFocusedIndex
    }
}
