package com.example.heatalert.reset

interface ResetView {
    fun showResetSuccessMessage()
    fun showEmailError(message: String)
    fun navigateToLogin()
}