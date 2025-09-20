package com.example.heatalert.Login

import android.content.Context
import android.util.Patterns

class LoginPresenter(private val view: LoginView, private val context: Context) {

    fun validateLogin(email: String, password: String) {
        when {
            email.isEmpty() -> view.showLoginError("Email is required")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> view.showLoginError("Invalid email format")
            password.isEmpty() -> view.showLoginError("Password is required")
            password.length < 6 -> view.showLoginError("Password must be at least 6 characters")
            else -> {
                // Simulate login validation (replace with actual authentication)
                if (isValidCredentials(email, password)) {
                    view.showLoginSuccess()
                    view.navigateToHome()
                } else {
                    view.showLoginError("Invalid email or password")
                }
            }
        }
    }

    private fun isValidCredentials(email: String, password: String): Boolean {
        // Mock validation - replace with actual authentication logic
        return email == "test@example.com" && password == "123456"
    }

    fun onSignUpClicked() {
        view.navigateToRegister()
    }
}