package com.example.elearning

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var signupButton: Button
    private lateinit var firebaseAuth: FirebaseAuth // FirebaseAuth instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Enable the ActionBar back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize UI components
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        signupButton = findViewById(R.id.signupButton)

        // Set up button click listener
        signupButton.setOnClickListener {
            handleSignUp()
        }
    }

    // Handle back button press in the ActionBar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // This will navigate back to the previous activity
        return true
    }

    private fun handleSignUp() {
        val name = nameInput.text.toString()
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val confirmPassword = confirmPasswordInput.text.toString()

        // Validate inputs
        when {
            name.isEmpty() -> showToast("Please enter your name.")
            email.isEmpty() -> showToast("Please enter your email.")
            password.isEmpty() -> showToast("Please enter your password.")
            confirmPassword.isEmpty() -> showToast("Please confirm your password.")
            password != confirmPassword -> showToast("Passwords do not match.")
            else -> {
                // Create new user with Firebase Authentication
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Send email verification
                            firebaseAuth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { verifyTask ->
                                    if (verifyTask.isSuccessful) {
                                        showToast("Sign Up Successful! Please check your email for verification.")

                                        // Navigate to LoginActivity after successful sign-up and email verification
                                        val intent = Intent(this, LoginActivity::class.java)
                                        startActivity(intent)
                                        finish() // Optionally finish the sign-up activity
                                    } else {
                                        showToast("Failed to send verification email: ${verifyTask.exception?.message}")
                                    }
                                }
                        } else {
                            // Sign up failed, display error message
                            showToast("Sign Up Failed: ${task.exception?.message}")
                        }
                    }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
