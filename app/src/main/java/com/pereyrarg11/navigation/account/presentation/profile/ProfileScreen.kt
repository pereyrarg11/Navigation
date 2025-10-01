package com.pereyrarg11.navigation.account.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pereyrarg11.navigation.R
import com.pereyrarg11.navigation.core.presentation.designsystem.AppTheme
import com.pereyrarg11.navigation.core.presentation.designsystem.components.AppButton
import com.pereyrarg11.navigation.core.presentation.designsystem.components.AppDialog
import com.pereyrarg11.navigation.core.presentation.designsystem.components.AppOutlinedButton
import com.pereyrarg11.navigation.core.presentation.tools.ObserveAsEvents
import com.pereyrarg11.navigation.core.presentation.tools.UiText
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    onSessionClosed: () -> Unit,
    onAccountDeleted: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel(),
) {
    var errorMessage by remember { mutableStateOf<UiText?>(null) }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ProfileEvent.AccountDeleted -> onAccountDeleted()
            ProfileEvent.SessionClosed -> onSessionClosed()
            is ProfileEvent.ShowError -> errorMessage = event.errorMessage
        }
    }

    ProfileScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction,
        modifier = modifier,
    )

    errorMessage?.let { message ->
        AppDialog(
            title = stringResource(R.string.core_label_error),
            description = message.asString(),
            primaryButton = {
                AppButton(
                    label = stringResource(R.string.core_action_accept)
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
private fun ProfileScreenContent(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = state.userName,
        )
        Spacer(modifier = Modifier.height(32.dp))
        AppButton(
            label = stringResource(R.string.account_action_logout),
            isEnabled = !state.isSubmitting,
            modifier = Modifier.fillMaxWidth(),
        ) {
            showLogoutDialog = true
        }
        Spacer(modifier = Modifier.height(16.dp))
        AppOutlinedButton(
            label = stringResource(R.string.account_action_delete_account),
            isEnabled = !state.isSubmitting,
            modifier = Modifier.fillMaxWidth(),
        ) {
            showDeleteAccountDialog = true
        }
    }

    if (showLogoutDialog) {
        AppDialog(
            title = stringResource(R.string.account_action_logout),
            description = stringResource(R.string.account_message_logout),
            primaryButton = {
                AppButton(
                    label = stringResource(R.string.account_action_continue),
                ) {
                    onAction(ProfileAction.OnClickLogout)
                    showLogoutDialog = false
                }
            },
            secondaryButton = {
                AppOutlinedButton(
                    label = stringResource(R.string.account_action_cancel),
                ) {
                    showLogoutDialog = false
                }
            },
        ) {
            showLogoutDialog = false
        }
    }

    if (showDeleteAccountDialog) {
        AppDialog(
            title = stringResource(R.string.account_action_delete_account),
            description = stringResource(R.string.account_message_delete_account),
            primaryButton = {
                AppButton(
                    label = stringResource(R.string.account_action_continue),
                ) {
                    onAction(ProfileAction.OnClickDeleteAccount)
                    showDeleteAccountDialog = false
                }
            },
            secondaryButton = {
                AppOutlinedButton(
                    label = stringResource(R.string.account_action_cancel),
                ) {
                    showDeleteAccountDialog = false
                }
            },
        ) {
            showDeleteAccountDialog = false
        }
    }
}

@Preview
@Composable
private fun ProfileScreenContentPreview() {
    AppTheme {
        Surface {
            ProfileScreenContent(
                state = ProfileState(
                    userName = "Gabriel Pereyra",
                    isSubmitting = false,
                ),
                onAction = {},
            )
        }
    }
}
