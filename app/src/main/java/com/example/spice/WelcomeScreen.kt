package com.example.spice

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.spice.databinding.ActivityWelcomeScreenBinding

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var binding: ActivityWelcomeScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_welcome_screen)
        with(binding){
            btnStart.setOnClickListener {
                startActivity(Intent(this@WelcomeScreen, SignIn::class.java))
            }
        }

    }
}