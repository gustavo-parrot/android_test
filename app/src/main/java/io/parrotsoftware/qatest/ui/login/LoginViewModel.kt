package io.parrotsoftware.qatest.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.parrotsoftware.qatest.data.managers.UserManager
import io.parrotsoftware.qatest.data.repositories.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val userManager: UserManager,
    private val userRepository: UserRepository
) : AndroidViewModel(application), LifecycleObserver {

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
