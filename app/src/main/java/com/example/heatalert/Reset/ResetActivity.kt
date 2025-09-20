package com.example.heatalert.reset

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.heatalert.Login.LoginActivity
import com.example.heatalert.R


class ResetActivity : AppCompatActivity(), ResetView {

    private lateinit var presenter: ResetPresenter
    private lateinit var etEmail: EditText
    private lateinit var btnReset: Button
    private lateinit var btnLoginBack: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        presenter = ResetPresenter(this)

        etEmail = findViewById(R.id.etEmail)
        btnReset = findViewById(R.id.btnResetPassword)
        btnLoginBack = findViewById(R.id.btnLoginBack)

        btnReset.setOnClickListener {
            val email = etEmail.text.toString()
            presenter.sendResetEmail(email)
        }

        btnLoginBack.setOnClickListener {
            presenter.onLoginClicked()
        }
    }

    override fun showResetSuccessMessage() {
        Toast.makeText(this, "Password reset email sent!", Toast.LENGTH_LONG).show()
    }

    override fun showEmailError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}