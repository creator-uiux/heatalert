package com.example.heatalert

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.heatalert.presenter.RegisterPresenter
import com.example.heatalert.view.RegisterView

class RegisterActivity : AppCompatActivity(), RegisterView {

    private lateinit var presenter: RegisterPresenter
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(this, this)

        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        val btnSignup = findViewById<Button>(R.id.btnSignup)
        val btnLogin = findViewById<TextView>(R.id.btnLogin)

        btnSignup.setOnClickListener {
            presenter.validateRegister(
                etUsername.text.toString().trim(),
                etEmail.text.toString().trim(),
                etPassword.text.toString().trim()
            )
        }

        btnLogin.setOnClickListener {
            presenter.onLoginClicked()
        }
    }

    override fun showRegisterSuccess() {
        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
    }

    override fun showRegisterError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}