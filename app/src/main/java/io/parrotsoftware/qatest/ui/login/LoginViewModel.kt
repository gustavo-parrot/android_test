package io.parrotsoftware.qatest.ui.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.parrotsoftware.qa_data.repositories.UserRepositoryD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val userRepositoryD: UserRepositoryD
) : ViewModel(), LifecycleObserver {


    //expose not mutable variable to the view
    private val _viewState = MutableStateFlow<LoginViewState>(LoginViewState.Idle)
    val viewState: StateFlow<LoginViewState> get() = _viewState

    val loginData = MutableStateFlow(LoginData())


    fun initView() {
        viewModelScope.launch {
            val response = userRepositoryD.userExists()
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
            //avoid to use !!
            val response = userRepositoryD.login(loginData.value.email, loginData.value.password)
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
        var email: String = "android-challenge@parrotsoftware.io",
        var password: String = "8mngDhoPcB3ckV7X"
    )


}