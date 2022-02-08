package io.lb.firebaseexample.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.lb.firebaseexample.databinding.ContentSignInBinding
import io.lb.firebaseexample.ui.main.MainActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ContentSignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ContentSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFinishButton()
    }

    private fun setupFinishButton() {
        binding.btFinish.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}