package com.example.heatalert.Splash

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.heatalert.Login.LoginActivity
import com.example.heatalert.R

class SplashActivity : AppCompatActivity(), SplashView {

    private lateinit var presenter: SplashPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var logoImage: ImageView
    private lateinit var appNameText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.progress_bar)
        logoImage = findViewById(R.id.logo_image)
        appNameText = findViewById(R.id.app_name_text)

        presenter = SplashPresenter(this)
        presenter.startLoading()
    }

    override fun updateProgress(progress: Int) {
        progressBar.progress = progress
    }

    override fun navigateToNextScreen() {
        // After splash -> go to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // prevent going back to splash
    }
}
