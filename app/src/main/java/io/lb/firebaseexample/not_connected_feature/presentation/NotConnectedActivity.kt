package io.lb.firebaseexample.not_connected_feature.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.lb.firebaseexample.databinding.ActivityNotConnectedBinding
import io.lb.firebaseexample.splash_feature.presentation.SplashActivity

class NotConnectedActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNotConnectedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotConnectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.included.btTryAgain.setOnClickListener {
            openSplashActivity()
        }
    }

    private fun openSplashActivity() {
        val i = Intent(this, SplashActivity::class.java)
        startActivity(i)
        finish()
    }
}