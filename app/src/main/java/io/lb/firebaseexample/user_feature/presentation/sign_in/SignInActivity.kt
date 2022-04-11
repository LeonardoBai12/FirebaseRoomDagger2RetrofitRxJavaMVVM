package io.lb.firebaseexample.user_feature.presentation.sign_in

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.lb.firebaseexample.databinding.ActivitySignInBinding
import io.lb.firebaseexample.todo_feature.presentation.todo.MainActivity
import io.lb.firebaseexample.util.setupDebounceEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUiEvents()
        setupFinishButton()
        setupOnNameEntered()
        setupOnEmailEntered()
        setupOnPasswordEntered()
        setupOnRepeatPasswordEntered()
    }

    private fun setupUiEvents() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is SignInViewModel.UiEvent.ShowToast -> {
                        toastMakeText(event.message)
                    }
                    is SignInViewModel.UiEvent.SingIn -> {
                        onSignInSuccess()
                    }
                }
            }
        }
    }

    private fun setupOnEmailEntered() {
        binding.included.tilSignInEmail.editText?.let {
            setupDebounceEditText(it)
        }?.subscribe {
            viewModel.onEvent(SignInEvent.EnteredEmail(it))
        }
    }

    private fun setupOnPasswordEntered() {
        binding.included.tilSignInPassword.editText?.let {
            setupDebounceEditText(it)
        }?.subscribe {
            viewModel.onEvent(SignInEvent.EnteredPassword(it))
        }
    }

    private fun setupOnRepeatPasswordEntered() {
        binding.included.tilSignInRepeatPassword.editText?.let {
            setupDebounceEditText(it)
        }?.subscribe {
            viewModel.onEvent(SignInEvent.EnteredRepeatPassword(it))
        }
    }

    private fun setupOnNameEntered() {
        binding.included.tilSignInFullName.editText?.let {
            setupDebounceEditText(it)
        }?.subscribe {
            viewModel.onEvent(SignInEvent.EnteredName(it))
        }
    }

    private fun setupFinishButton() {
        binding.included.btFinish.setOnClickListener {
            viewModel.onEvent(SignInEvent.PressedSignIn)
        }
    }

    private fun onSignInSuccess() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finishAffinity()
    }

    private fun toastMakeText(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}