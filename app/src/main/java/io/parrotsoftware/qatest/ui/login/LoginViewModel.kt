package io.parrotsoftware.qatest.ui.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.parrotsoftware.qatest.data.managers.UserManager
import io.parrotsoftware.qatest.data.repositories.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository) : ViewModel(),
    LifecycleObserver {
    private val viewState = MutableLiveData<LoginViewState>()
    fun getViewState() = viewState

    var isProgressBar = MutableLiveData(false)
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
        isProgressBar.value = true
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