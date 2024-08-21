package com.example.spice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.spice.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("794262091891-fd5nbc5vc3dnds7ffu2uatdu1d6rbagu.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        with(binding) {
            btnGoogle.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            }

            btnLogin.setOnClickListener {
                val userEmail = etEmail.text.toString().trim()
                val userPassword = etPassword.text.toString().trim()
                if (userEmail.isBlank() || userPassword.isBlank()) {
                    Toast.makeText(this@SignIn, "Please fill all the blanks", Toast.LENGTH_SHORT).show()
                } else {
                    auth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(this@SignIn) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this@SignIn, "Login Successful", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@SignIn, MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this@SignIn, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }

            txSignUp.setOnClickListener {
                startActivity(Intent(this@SignIn, MainActivity::class.java))
            }
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Google Sign-In Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Google Sign-In Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
