package com.pereyrarg11.navigation.account.presentation.verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pereyrarg11.navigation.R
import com.pereyrarg11.navigation.account.domain.verification.ValidateOtpUseCase
import com.pereyrarg11.navigation.core.presentation.designsystem.components.AppButton
import com.pereyrarg11.navigation.core.presentation.designsystem.components.AppDialog
import com.pereyrarg11.navigation.core.presentation.designsystem.components.AppOtpField
import com.pereyrarg11.navigation.core.presentation.tools.ObserveAsEvents
import com.pereyrarg11.navigation.core.presentation.tools.UiText

@Composable
fun VerificationScreen(
    viewModel: VerificationViewModel,
    modifier: Modifier = Modifier,
    onSuccessfulVerification: () -> Unit,
) {
    var errorMessage by remember { mutableStateOf<UiText?>(null) }
    // event listener
    ObserveAsEvents(viewModel.events) { otpEvent ->
        when (otpEvent) {
            VerificationEvent.SuccessfulVerification -> onSuccessfulVerification()
            is VerificationEvent.ShowError -> errorMessage = otpEvent.errorMessage
        }
    }
    // focus management
    val focusRequesters = remember {
        List(ValidateOtpUseCase.OTP_LENGTH) { FocusRequester() }
    }
    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current
    // hide keyboard once all digits have been entered
    LaunchedEffect(viewModel.state.code, keyboardManager) {
        val allDigitsEntered = viewModel.state.code.none { it == null }
        if (allDigitsEntered) {
            focusRequesters.forEach {
                it.freeFocus()
            }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }
    // request focus on active input
    LaunchedEffect(viewModel.state.focusedIndex) {
        viewModel.state.focusedIndex?.let { focusedIndex ->
            focusRequesters.getOrNull(focusedIndex)?.requestFocus()
        }
    }
    // display content
    VerificationScreenContent(
        state = viewModel.state,
        focusRequesters = focusRequesters,
        onAction = { action ->
            if (action is VerificationAction.OnEnterDigit) {
                if (action.digit != null) {
                    focusRequesters[action.index].freeFocus()
                }
            }
            viewModel.onAction(action)
        },
        modifier = modifier,
    )

    errorMessage?.let { message ->
        AppDialog(
            title = stringResource(R.string.core_label_error),
            description = message.asString(),
            primaryButton = {
                AppButton(
                    label = stringResource(R.string.core_action_accept),
                ) {
                    errorMessage = null
                }
            }
        ) {
            errorMessage = null
        }
    }
}

@Composable
fun VerificationScreenContent(
    state: VerificationState,
    focusRequesters: List<FocusRequester>,
    onAction: (VerificationAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        ) {
            state.code.forEachIndexed { index, digit ->
                AppOtpField(
                    digit = digit,
                    focusRequester = focusRequesters[index],
                    onFocusChanged = { isFocused ->
                        if (isFocused) {
                            onAction(VerificationAction.OnChangeFieldFocused(index))
                        }
                    },
                    onDigitChanged = { newDigit ->
                        onAction(VerificationAction.OnEnterDigit(newDigit, index))
                    },
                    onKeyboardBack = {
                        onAction(VerificationAction.OnKeyboardBack)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }
        }
        state.isValidCode?.let { isValid ->
            if (!isValid) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = state.codeErrorMessage?.asString().orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
        Spacer(modifier = Modifier.height(36.dp))
        AppButton(
            label = stringResource(R.string.account_action_continue),
            modifier = Modifier.fillMaxWidth(),
            isEnabled = !state.isSubmitting,
        ) {
            onAction(VerificationAction.OnClickSubmit)
        }
    }
}
