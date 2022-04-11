package io.lb.firebaseexample.user_feature.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.lb.firebaseexample.R
import io.lb.firebaseexample.databinding.ActivityLoginBinding
import io.lb.firebaseexample.todo_feature.presentation.todo.MainActivity
import io.lb.firebaseexample.user_feature.presentation.sign_in.SignInActivity
import io.lb.firebaseexample.util.setupDebounceEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FirebaseExample_NoActionBar)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUiEvents()
        setupLoginButton()
        setupSignInButton()
        setupOnEmailEntered()
        setupOnPasswordEntered()
    }

    private fun setupUiEvents() {
        CoroutineScope(Main).launch {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is LoginViewModel.UiEvent.ShowToast -> {
                        toastMakeText(event.message)
                    }
                    is LoginViewModel.UiEvent.Login -> {
                        onSignInSuccess()
                    }
                    is LoginViewModel.UiEvent.OpenSignInScreen -> {
                        openSignInScreen()
                    }
                }
            }
        }
    }

    private fun openSignInScreen() {
        val i = Intent(this, SignInActivity::class.java)
        startActivity(i)
    }

    private fun onSignInSuccess() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finishAffinity()
    }

    private fun setupLoginButton() {
        binding.included.btLogin.setOnClickListener {
            viewModel.onEvent(LoginEvent.PressedLogin)
        }
    }

    private fun setupSignInButton() {
        binding.included.btSignIn.setOnClickListener {
            viewModel.onEvent(LoginEvent.PressedSignIn)
        }
    }

    private fun setupOnEmailEntered() {
        binding.included.tilLoginEmail.editText?.let {
            setupDebounceEditText(it)
        }?.subscribe {
            viewModel.onEvent(LoginEvent.EnteredEmail(it))
        }
    }

    private fun setupOnPasswordEntered() {
        binding.included.tilLoginPassword.editText?.let {
            setupDebounceEditText(it)
        }?.subscribe {
            viewModel.onEvent(LoginEvent.EnteredPassword(it))
        }
    }

    private fun toastMakeText(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}