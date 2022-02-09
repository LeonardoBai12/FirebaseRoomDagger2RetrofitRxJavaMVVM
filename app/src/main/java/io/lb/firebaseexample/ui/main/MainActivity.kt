package io.lb.firebaseexample.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.R
import io.lb.firebaseexample.databinding.ActivityMainBinding
import io.lb.firebaseexample.ui.login.LoginActivity
import io.lb.firebaseexample.ui.todo.TodoDetailsActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setupNavController()
        setupAddButton()
    }

    private fun setupAddButton() {
        binding.fabAddNewTodo.setOnClickListener { view ->
            val i = Intent(this, TodoDetailsActivity::class.java)
            startActivity(i)
        }
    }

    private fun setupNavController() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_logout -> onClickLogout()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onClickLogout(): Boolean {
        auth.signOut()
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        finish()
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}