package io.lb.firebaseexample.ui.splash

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.databinding.ActivitySplashBinding
import io.lb.firebaseexample.ui.login.LoginActivity
import io.lb.firebaseexample.ui.main.MainActivity
import io.lb.firebaseexample.ui.notConnected.NotConnectedActivity
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