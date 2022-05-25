package io.parrotsoftware.qatest.ui.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.parrotsoftware.qatest.data.datasources.local.user.UserManager
import io.parrotsoftware.qatest.data.repositories.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(), LifecycleObserver {

    lateinit var userManager: UserManager
    lateinit var userRepository: UserRepository

    private val viewState = MutableLiveData<LoginViewState>()
    fun getViewState() = viewState

    val email = MutableLiveData("android-challenge@parrotsoftware.io")
    val password = MutableLiveData("8mngDhoPcB3ckV7X")

    fun initView() {
        viewModelScope.launch {
            val response = userRepository.userExists()
            if (response.isError) {
                viewState.value = LoginViewState.LoginError
                return@launch
            }

            if (response.requiredResult) {
                viewState.value = LoginViewState.LoginSuccess
            }
        }
    }

    fun onLoginPortraitClicked() {
        viewModelScope.launch {
            val response = userRepository.login(email.value!!, password.value!!)
            if (response.isError) {
                viewState.value = LoginViewState.LoginError
            } else {
                viewState.value = LoginViewState.LoginSuccess
            }
        }
    }

    fun navigated() {
        viewState.value = LoginViewState.Idle
    }
}