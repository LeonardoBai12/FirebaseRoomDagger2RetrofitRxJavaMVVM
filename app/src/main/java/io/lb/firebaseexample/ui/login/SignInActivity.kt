package io.lb.firebaseexample.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.lb.firebaseexample.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}