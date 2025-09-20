package com.example.heatalert.Register

interface RegisterView {
    fun showRegisterSuccess()
    fun showRegisterError(message: String)
    fun navigateToLogin()
}