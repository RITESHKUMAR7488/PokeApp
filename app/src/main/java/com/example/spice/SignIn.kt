package com.example.spice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.spice.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var binding: ActivitySignInBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        with(binding) {
            btnLogin.setOnClickListener {
                auth = FirebaseAuth.getInstance()
                val userEmail = etEmail.text.toString().trim()
                val userPassword = etPassword.text.toString().trim()
                if (userEmail.isBlank() || userPassword.isBlank()) {
                    Toast.makeText(this@SignIn, "please fill all the blanks", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    auth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(this@SignIn) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this@SignIn, "Login Successful", Toast.LENGTH_SHORT)
                                    .show()

                                val sharedPref =getSharedPreferences("poke", MODE_PRIVATE)
                                val editor=sharedPref.edit()
                                editor.putBoolean("loggedIn",true)
                                editor.apply()
                                startActivity(Intent(this@SignIn, MainActivity::class.java))
                                finish()

                            }else{
                                Toast.makeText(this@SignIn, "Login Failed : ${task.exception?.message}", Toast.LENGTH_SHORT).show()

                            }

                        }
                }
                txSignUp.setOnClickListener {
                    startActivity(Intent(this@SignIn, MainActivity::class.java))

                }
            }

        }
    }
}