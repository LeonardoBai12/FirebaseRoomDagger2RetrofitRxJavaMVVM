package io.lb.firebaseexample.splash_feature.presentation

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.databinding.ActivitySplashBinding
import io.lb.firebaseexample.not_connected_feature.presentation.NotConnectedActivity
import io.lb.firebaseexample.user_feature.presentation.login.LoginActivity
import io.lb.firebaseexample.todo_feature.presentation.todo.MainActivity
import io.lb.firebaseexample.util.NetworkHelper
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openActivityAccordingToUser()
    }

    private fun openActivityAccordingToUser() {
        val i = if (!NetworkHelper.isOnline(this)) {
            Intent(this, NotConnectedActivity::class.java)
        } else if (auth.currentUser != null) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }
        startActivity(i)
        finish()
    }
}