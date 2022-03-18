package io.lb.firebaseexample.todo_feature.presentation.todo

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
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.R
import io.lb.firebaseexample.databinding.ActivityMainBinding
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.user_feature.presentation.login.LoginActivity
import io.lb.firebaseexample.todo_feature.presentation.todo_details.TodoDetailsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var id = 0

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainTodosViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupUiEvents()
        setupBindings()

        setupNavController()
        setupAddButton()
    }

    private fun setupViewModel() {
        viewModel.todos.observe(this) {
            id = it.size
        }
    }

    private fun setupBindings() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    private fun setupUiEvents() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is MainTodosViewModel.UiEvent.OnLogoutSuccess -> {
                        onLogoutSuccess()
                    }
                    is MainTodosViewModel.UiEvent.OnPressedAdd -> {
                        onAddClicked(event.id)
                    }
                    is MainTodosViewModel.UiEvent.OnPressedSettings -> {

                    }
                    is MainTodosViewModel.UiEvent.OnTodoClicked -> {
                        onTodoClicked(event.id, event.todo)
                    }
                }
            }
        }
    }

    private fun setupAddButton() {
        binding.fabAddNewTodo.setOnClickListener {
            viewModel.onEvent(MainTodosEvent.PressedAdd(id))
        }
    }

    private fun onLogoutClicked(): Boolean {
        viewModel.onEvent(MainTodosEvent.PressedLogout)
        return true
    }

    private fun onAddClicked(id: Int) {
        val i = Intent(this, TodoDetailsActivity::class.java)
        i.putExtra(TodoDetailsActivity.ID, id)
        startActivity(i)
    }

    private fun onTodoClicked(id: Int, todo: Todo) {
        val i = Intent(this, TodoDetailsActivity::class.java)
        i.putExtra(TodoDetailsActivity.ID, id)
        i.putExtra(TodoDetailsActivity.TODO, todo)
        startActivity(i)
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
            R.id.action_logout -> onLogoutClicked()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onLogoutSuccess() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}