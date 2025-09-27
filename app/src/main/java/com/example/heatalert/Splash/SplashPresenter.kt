package com.example.heatalert.Splash

import android.os.Handler
import android.os.Looper

class SplashPresenter(private val view: SplashView) {

    private var progress = 0
    private val handler = Handler(Looper.getMainLooper())

    fun startLoading() {
        progress = 0
        updateProgressBar()
    }

    private fun updateProgressBar() {
        handler.postDelayed({
            progress += 10
            view.updateProgress(progress)

            if (progress < 100) {
                updateProgressBar()
            } else {
                view.navigateToNextScreen()
            }
        }, 300) // 0.3 second delay between updates
    }
}
