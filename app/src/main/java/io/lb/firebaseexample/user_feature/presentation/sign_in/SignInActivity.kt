package io.lb.firebaseexample.user_feature.presentation.sign_in

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.databinding.ActivitySignInBinding
import io.lb.firebaseexample.todo_feature.presentation.todo.MainActivity
import io.lb.firebaseexample.user_feature.presentation.login.LoginViewModel
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class SignInActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: LoginViewModel by viewModels {
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
            val name = binding.included.tvFullName.editText?.text.toString()
            val email = binding.included.tvSignInEmail.editText?.text.toString()
            val password = binding.included.tvSignInPassword.editText?.text.toString()
            val repeatPassword = binding.included.tvSignInRepeatPassword.editText?.text.toString()

            if (name.isEmpty() && email.isEmpty() &&
                password.isEmpty() && repeatPassword.isEmpty()) {
                toastMakeText("Existem campos não preenchidos")
                return@setOnClickListener
            } else if (password != repeatPassword) {
                toastMakeText("As senhas digitadas não conferem")
                return@setOnClickListener
            }

            viewModel.createFirebaseUser(
                this,
                email,
                password,
            ) { task ->
                if (task.isSuccessful) {
                    task.result.user?.let {
                        insertUser(it, name)
                    }
                } else {
                    onSignInFailure(task.exception)
                }
            }
        }
    }

    private fun insertUser(
        it: FirebaseUser,
        name: String
    ) = viewModel.insertUser(it.uid, name) { isSuccessful, exception ->
        if (isSuccessful) {
            onSignInSuccess()
        } else {
            onSignInFailure(exception)
        }
    }

    private fun onSignInSuccess() {
        Timber.d("createUser:success")
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finishAffinity()
    }

    private fun onSignInFailure(task: Exception?) {
        Timber.e("createUser:failure $task")
        toastMakeText("Authentication failed. $task")
    }

    private fun toastMakeText(text: String) {
        Toast.makeText(baseContext, text, Toast.LENGTH_LONG).show()
    }
}