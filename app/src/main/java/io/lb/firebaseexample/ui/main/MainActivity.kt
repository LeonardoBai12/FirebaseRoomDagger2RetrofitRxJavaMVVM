package io.lb.firebaseexample.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.R
import io.lb.firebaseexample.databinding.ActivityMainBinding
import io.lb.firebaseexample.ui.login.LoginActivity
import io.lb.firebaseexample.ui.todo.TodoDetailsActivity
import io.lb.firebaseexample.ui.todo.TodoViewModel
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: TodoViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupBindings()

        setupNavController()
        setupAddButton()
    }

    private fun setupBindings() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    private fun setupViewModel() {
        viewModel.loadTodos()
    }

    private fun setupAddButton() {
        binding.fabAddNewTodo.setOnClickListener {
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