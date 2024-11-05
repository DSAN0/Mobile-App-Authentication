package com.example.elearning

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPasswordLink: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Enable the ActionBar back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize UI components
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink)

        // Set up button click listeners
        loginButton.setOnClickListener {
            handleLogin()
        }

        // Navigate to ForgotPasswordActivity
        forgotPasswordLink.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    // Handle back button press in the ActionBar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun handleLogin() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        // Validate inputs
        when {
            email.isEmpty() -> {
                showToast("Please enter your email.")
            }
            password.isEmpty() -> {
                showToast("Please enter your password.")
            }
            else -> {
                // Authenticate with Firebase
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null && user.isEmailVerified) {
                                // Login successful and email is verified
                                showToast("Login successful for $email!")
                                val intent = Intent(this, MainPageActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // Email is not verified
                                showToast("Please verify your email before logging in.")
                                auth.signOut()
                            }
                        } else {
                            // If login fails
                            showToast("Authentication failed: ${task.exception?.message}")
                        }
                    }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
