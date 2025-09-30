package com.pereyrarg11.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pereyrarg11.navigation.account.domain.AuthRepository
import com.pereyrarg11.navigation.core.domain.session.SessionStorage
import com.pereyrarg11.navigation.core.domain.util.AppResult
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionStorage: SessionStorage,
    private val authRepository: AuthRepository,
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(isCheckingAuth = true)
            if (sessionStorage.get() != null) {
                getUser()
            } else {
                state = state.copy(isCheckingAuth = false)
            }
        }
    }

    private suspend fun getUser() {
        val result = authRepository.getUser()
        val isLoggedIn = when (result) {
            is AppResult.Error -> false
            is AppResult.Success -> true
        }
        state = state.copy(
            isCheckingAuth = false,
            isLoggedIn = isLoggedIn
        )
    }

    fun onSessionUpdated(isActive: Boolean) {
        viewModelScope.launch {
            state = state.copy(
                isLoggedIn = isActive
            )
        }
    }
}
