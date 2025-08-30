package com.example.heatalert

import android.content.Context
import android.util.Patterns

class RegisterPresenter(private val context: Context) {

    fun validateRegister(username: String, email: String, password: String): String {
        if (username.isEmpty()) return "Username is required"
        if (email.isEmpty()) return "Email is required"
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return "Invalid email"
        if (password.isEmpty()) return "Password is required"
        if (password.length < 6) return "Password must be at least 6 characters"
        return "success"
    }
}
