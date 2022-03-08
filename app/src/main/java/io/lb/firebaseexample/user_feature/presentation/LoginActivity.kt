package io.lb.firebaseexample.user_feature.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.R
import io.lb.firebaseexample.databinding.ActivityLoginBinding
import io.lb.firebaseexample.todo_feature.presentation.todo.MainActivity
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FirebaseExample_NoActionBar)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLoginButton()
        setupSignInButton()
    }

    private fun setupLoginButton() {
        binding.included.btLogin.setOnClickListener {
            val email = binding.included.tvLoginEmail.editText?.text.toString()
            val password = binding.included.tvLoginPassword.editText?.text.toString()

            auth.signInWithEmailAndPassword(email, password)
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
        Timber.d("signIn:success")
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun onSignInFailure(task: Exception?) {
        Timber.e("signIn:failure $task")
        Toast.makeText(
            baseContext,
            "Authentication failed. $task",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun setupSignInButton() {
        binding.included.btSignIn.setOnClickListener {
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
        }
    }
}