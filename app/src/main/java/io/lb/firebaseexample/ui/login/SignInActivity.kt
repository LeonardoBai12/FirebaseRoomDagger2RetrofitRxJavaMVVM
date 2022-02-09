package io.lb.firebaseexample.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.lb.firebaseexample.databinding.ActivitySignInBinding
import io.lb.firebaseexample.ui.main.MainActivity
import io.lb.firebaseexample.ui.user.UserViewModel
import javax.inject.Inject

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: UserViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFinishButton()
    }

    private fun setupFinishButton() {
        binding.included.btFinish.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}