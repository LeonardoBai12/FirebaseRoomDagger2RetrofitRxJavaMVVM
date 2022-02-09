package io.lb.firebaseexample.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.lb.firebaseexample.databinding.ActivitySplashBinding
import io.lb.firebaseexample.ui.login.LoginActivity
import io.lb.firebaseexample.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openActivityAccordingToUser()
    }

    private fun openActivityAccordingToUser() {
        val i: Intent

        if (auth.currentUser != null) {
            i = Intent(this, MainActivity::class.java)
            i.putExtra("CURRENT_USER", auth.currentUser)
        } else {
            i = Intent(this, LoginActivity::class.java)
        }
        startActivity(i)
        finish()
    }
}