package io.lb.firebaseexample.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.databinding.ActivitySignInBinding
import io.lb.firebaseexample.ui.main.MainActivity
import io.lb.firebaseexample.ui.user.UserViewModel
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class SignInActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    @Inject
    lateinit var auth: FirebaseAuth

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

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        onSignInSuccess()
                    } else {
                        onSignInFailure(task.exception)
                    }
                }
        }
    }

    private fun onSignInSuccess() {
        Timber.d("createUser:success")
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("CURRENT_USER", auth.currentUser)
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