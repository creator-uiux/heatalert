package com.example.heatalert.view

interface RegisterView {
    fun showRegisterSuccess()
    fun showRegisterError(message: String)
    fun navigateToLogin()
}