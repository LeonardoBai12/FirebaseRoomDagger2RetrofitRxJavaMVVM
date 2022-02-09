package io.lb.firebaseexample.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.lb.firebaseexample.R
import io.lb.firebaseexample.databinding.ActivityLoginBinding
import io.lb.firebaseexample.ui.main.MainActivity
import io.lb.firebaseexample.ui.user.UserViewModel
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth = Firebase.auth

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: UserViewModel by viewModels {
        viewModelFactory
    }

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
        i.putExtra("CURRENT_USER", auth.currentUser)
        startActivity(i)
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