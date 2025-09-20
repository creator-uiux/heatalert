package com.example.heatalert.Splash

import android.os.Handler
import android.os.Looper

class SplashPresenter(private val view: SplashView) {
    private val handler = Handler(Looper.getMainLooper())
    private val splashDelay: Long = 3000 // 3 seconds

    fun startLoading() {
        handler.postDelayed({
            view.navigateToLogin()
        }, splashDelay)
    }

    fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
    }
}