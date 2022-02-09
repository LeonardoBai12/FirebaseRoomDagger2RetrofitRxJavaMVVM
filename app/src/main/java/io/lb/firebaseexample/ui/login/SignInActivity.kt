package io.lb.firebaseexample.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.lb.firebaseexample.databinding.ActivitySignInBinding
import io.lb.firebaseexample.ui.main.MainActivity
import io.lb.firebaseexample.ui.user.UserViewModel
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val auth = Firebase.auth

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
            val email = binding.included.tvSignInEmail.editText?.text.toString()
            val password = binding.included.tvSignInPassword.editText?.text.toString()

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
        Toast.makeText(
            baseContext,
            "Authentication failed. $task",
            Toast.LENGTH_LONG
        ).show()
    }
}