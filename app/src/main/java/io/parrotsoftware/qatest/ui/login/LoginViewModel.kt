package io.parrotsoftware.qatest.ui.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.parrotsoftware.qatest.data.managers.UserManager
import io.parrotsoftware.qatest.data.managers.impl.UserManagerImpl
import io.parrotsoftware.qatest.data.models.Result
import io.parrotsoftware.qatest.data.repositories.UserRepository
import io.parrotsoftware.qatest.usecase.authentication.LoginUseCase
import io.parrotsoftware.qatest.usecase.authentication.UserExistsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userExistsUseCase: UserExistsUseCase
): ViewModel(), LifecycleObserver {

    private val viewState = MutableLiveData<LoginViewState>()
    fun getViewState() = viewState

    val email = MutableLiveData("android-challenge@parrotsoftware.io")
    val password = MutableLiveData("8mngDhoPcB3ckV7X")

    fun initView() {
        userExists()
    }

    fun onLoginPortraitClicked() {
        viewModelScope.launch {
            when(val result = loginUseCase(email.value!!, password.value!!)) {
                is Result.Success -> {
                    userExists()
                }
                is Result.Error -> {
                    viewState.value = LoginViewState.LoginError
                }
            }
        }
    }

    private fun userExists() {
        viewModelScope.launch {
            when(val result = userExistsUseCase()) {
                is Result.Success -> {
                    viewState.value = LoginViewState.LoginSuccess
                }
                is Result.Error -> {
                    viewState.value = LoginViewState.LoginError
                    return@launch
                }
            }
        }
    }

    fun navigated() {
        viewState.value = LoginViewState.Idle
    }
}
