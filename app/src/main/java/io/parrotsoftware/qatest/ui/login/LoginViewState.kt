package io.parrotsoftware.qatest.ui.login

sealed class LoginViewState {

    object Idle : LoginViewState()
    object LoginError : LoginViewState()
    object LoginSuccess : LoginViewState()
}
