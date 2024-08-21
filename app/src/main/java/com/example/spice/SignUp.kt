package com.example.spice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.spice.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var binding: ActivitySignUpBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        with(binding) {
            auth = FirebaseAuth.getInstance()
            btnSignUp.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                //check if any field is blank
                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(
                        this@SignUp,
                        "please fill all the blanks",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else{
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this@SignUp) { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this@SignUp,
                                        "Sign Up Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
                                    val editor=sharedPreferences.edit()
                                    editor.putBoolean("loggedIn",true)
                                    editor.apply()


                                    val user= User(email,password)

                                    val db= Firebase.firestore
                                    db.collection("users")
                                        .add(user)
                                        .addOnSuccessListener { documentReference ->
                                            startActivity(Intent(this@SignUp,MainActivity::class.java))
                                            finish()
                                            Log.d(
                                                "response",
                                                "DocumentSnapshot added with ID: ${documentReference.id}"
                                            )
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w("response", "Error adding document", e)
                                        }

                                } else{
                                    Toast.makeText(
                                        this@SignUp,
                                        "Sign Up Failed",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }


                            }


                    }

                }
            }

        }
    }
