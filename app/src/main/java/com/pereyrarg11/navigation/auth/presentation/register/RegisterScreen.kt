package com.pereyrarg11.navigation.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.pereyrarg11.navigation.R
import com.pereyrarg11.navigation.core.presentation.designsystem.AppTheme
import com.pereyrarg11.navigation.core.presentation.designsystem.components.AppButton
import com.pereyrarg11.navigation.core.presentation.designsystem.components.AppDialog
import com.pereyrarg11.navigation.core.presentation.designsystem.components.AppTextField
import com.pereyrarg11.navigation.core.presentation.tools.ObserveAsEvents
import com.pereyrarg11.navigation.core.presentation.tools.UiText
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    onSuccessfulRegistration: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        keyboardController?.hide()
        when (event) {
            RegisterEvent.RegistrationSuccess -> {
                Toast.makeText(
                    context,
                    R.string.auth_message_registration_success,
                    Toast.LENGTH_SHORT
                ).show()
                onSuccessfulRegistration()
            }
        }
    }

    RegisterScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

@Composable
fun RegisterScreenContent(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(20.dp),
    ) {
        AppTextField(
            state = state.name,
            label = stringResource(R.string.auth_label_name),
            modifier = Modifier.fillMaxWidth(),
            errorMessage = state.nameError?.asString(),
            imeAction = ImeAction.Next,
        )
        Spacer(modifier = Modifier.height(28.dp))
        AppTextField(
            state = state.lastName,
            label = stringResource(R.string.auth_label_last_name),
            modifier = Modifier.fillMaxWidth(),
            errorMessage = state.lastNameError?.asString(),
            imeAction = ImeAction.Next,
        )
        Spacer(modifier = Modifier.height(28.dp))
        AppTextField(
            state = state.phoneNumber,
            label = stringResource(R.string.auth_label_phone_number),
            modifier = Modifier.fillMaxWidth(),
            errorMessage = state.phoneNumberError?.asString(),
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next,
        )
        Spacer(modifier = Modifier.height(28.dp))
        AppTextField(
            state = state.email,
            label = stringResource(R.string.auth_label_email),
            modifier = Modifier.fillMaxWidth(),
            errorMessage = state.emailError?.asString(),
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
        ) {
            onAction(RegisterAction.OnClickSubmit)
        }
        Spacer(modifier = Modifier.height(36.dp))
        AppButton(
            label = stringResource(R.string.auth_action_continue),
            modifier = Modifier.fillMaxWidth(),
            isEnabled = !state.isSubmitting,
        ) {
            onAction(RegisterAction.OnClickSubmit)
        }
    }

    if (state.isBadRequest && state.badRequestMessage != null) {
        AppDialog(
            title = stringResource(R.string.core_label_error),
            description = state.badRequestMessage.asString(),
            primaryButton = {
                AppButton(
                    label = stringResource(R.string.core_action_accept)
                ) {
                    onAction(RegisterAction.OnDismissError)
                }
            },
        ) { }
    }
}

@Preview
@PreviewLightDark
@Composable
private fun RegisterScreenContentPreview() {
    AppTheme {
        RegisterScreenContent(
            state = RegisterState(
                name = TextFieldState(initialText = "Gabriel"),
                lastName = TextFieldState(initialText = "Pereyra_!"),
                lastNameError = UiText.StaticString("Special characters are not allowed"),
            ),
            onAction = {},
        )
    }
}
