package com.example.spice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.spice.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        // Check if the user is signed in
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // If the user is not signed in, redirect to SignIn activity
            startActivity(Intent(this, SignIn::class.java))
            finish() // Close the MainActivity
        }

        // Your other code for MainActivity...
    }
}
