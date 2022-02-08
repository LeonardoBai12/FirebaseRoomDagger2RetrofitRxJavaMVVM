package io.lb.firebaseexample.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.lb.firebaseexample.R
import io.lb.firebaseexample.databinding.ContentLoginBinding
import io.lb.firebaseexample.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ContentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FirebaseExample_NoActionBar)

        binding = ContentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLoginButton()
        setupSignInButton()
    }

    private fun setupLoginButton() {
        binding.btLogin.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun setupSignInButton() {
        binding.btSignIn.setOnClickListener {
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
        }
    }
}