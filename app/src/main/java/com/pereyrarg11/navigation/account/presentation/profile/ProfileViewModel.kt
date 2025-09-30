package com.pereyrarg11.navigation.account.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pereyrarg11.navigation.account.domain.AuthRepository
import com.pereyrarg11.navigation.core.domain.util.AppResult
import com.pereyrarg11.navigation.core.presentation.tools.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: AuthRepository,
) : ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set
    private val eventChannel = Channel<ProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.OnClickDeleteAccount -> onClickDeleteAccount()
            ProfileAction.OnClickLogout -> onClickLogout()
        }
    }

    private fun onClickDeleteAccount() {
        viewModelScope.launch {
            val result = repository.delete()
            state = state.copy(isSubmitting = false)
            when (result) {
                is AppResult.Error -> eventChannel.send(
                    ProfileEvent.ShowError(result.error.toUiText())
                )

                is AppResult.Success -> eventChannel.send(
                    ProfileEvent.AccountDeleted
                )
            }
        }
    }

    private fun onClickLogout() {
        viewModelScope.launch {
            repository.logout()
            eventChannel.send(ProfileEvent.SessionClosed)
        }
    }
}
