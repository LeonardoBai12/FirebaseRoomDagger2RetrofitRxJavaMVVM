package io.lb.firebaseexample.ui.notConnected

import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.databinding.ActivityNotConnectedBinding
import io.lb.firebaseexample.ui.splash.SplashActivity

class NotConnectedActivity: DaggerAppCompatActivity() {
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