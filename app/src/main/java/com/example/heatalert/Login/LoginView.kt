package com.example.heatalert.Login

interface LoginView {
    fun showLoginSuccess()
    fun showLoginError(message: String)
    fun navigateToHome()
    fun navigateToRegister()
    fun navigateToReset()
}
