package io.parrotsoftware.qatest.ui.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.parrotsoftware.qa_data.repositories.UserRepository
import io.parrotsoftware.qatest.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), LifecycleObserver {


    //expose not mutable variable to the view
    private val _viewState = MutableStateFlow<LoginViewState>(LoginViewState.Idle)
    val viewState: StateFlow<LoginViewState> get() = _viewState

    val loginData = MutableStateFlow(LoginData())


    fun initView() {
        viewModelScope.launch {
            val response = userRepository.userExists()
            if (response.isError) {
                _viewState.value = LoginViewState.LoginError
                return@launch
            }

            if (response.requiredResult) {
                _viewState.value = LoginViewState.LoginSuccess
            }
        }
    }

    fun onLoginPortraitClicked() {
        viewModelScope.launch {
            val response = userRepository.login(loginData.value.email, loginData.value.password)
            if (response.isError) {
                _viewState.value = LoginViewState.LoginError
            } else {
                _viewState.value = LoginViewState.LoginSuccess
            }
        }
    }

    fun navigated() {
        _viewState.value = LoginViewState.Idle
    }

    data class LoginData(
        var email: String = BuildConfig.US3R_D3F4U1T,
        var password: String = BuildConfig.P4SS_D3F4U1T
    )


}