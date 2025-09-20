package com.example.heatalert.view

interface LoginView {
    fun showLoginSuccess()
    fun showLoginError(message: String)
    fun navigateToHome()
    fun navigateToRegister()
}