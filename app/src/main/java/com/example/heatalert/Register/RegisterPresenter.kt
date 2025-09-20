package com.example.heatalert.Register

import android.content.Context
import android.util.Patterns

class RegisterPresenter(private val view: RegisterView, private val context: Context) {

    fun validateRegister(username: String, email: String, password: String) {
        when {
            username.isEmpty() -> view.showRegisterError("Username is required")
            username.length < 3 -> view.showRegisterError("Username must be at least 3 characters")
            email.isEmpty() -> view.showRegisterError("Email is required")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> view.showRegisterError("Invalid email format")
            password.isEmpty() -> view.showRegisterError("Password is required")
            password.length < 6 -> view.showRegisterError("Password must be at least 6 characters")
            else -> {
                // Simulate registration (replace with actual registration logic)
                registerUser(username, email, password)
            }
        }
    }

    private fun registerUser(username: String, email: String, password: String) {
        // Mock registration - replace with actual registration logic
        try {
            // Simulate successful registration
            view.showRegisterSuccess()
            view.navigateToLogin()
        } catch (e: Exception) {
            view.showRegisterError("Registration failed: ${e.message}")
        }
    }

    fun onLoginClicked() {
        view.navigateToLogin()
    }
}

