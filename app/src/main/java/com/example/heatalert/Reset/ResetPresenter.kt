package com.example.heatalert.reset

import android.os.Handler
import android.os.Looper

class ResetPresenter(private val view: ResetView) {

    fun sendResetEmail(email: String) {
        if (email.isEmpty()) {
            view.showEmailError("Email cannot be empty.")
            return
        }

        // In a real app, you would make an API call here.
        // This is a simulated delay for demonstration.
        Handler(Looper.getMainLooper()).postDelayed({
            view.showResetSuccessMessage()
            view.navigateToLogin()
        }, 1500)
    }

    fun onLoginClicked() {
        view.navigateToLogin()
    }
}