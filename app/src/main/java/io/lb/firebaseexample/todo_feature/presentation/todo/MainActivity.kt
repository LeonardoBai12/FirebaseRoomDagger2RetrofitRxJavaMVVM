package io.lb.firebaseexample.todo_feature.presentation.todo

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.R
import io.lb.firebaseexample.databinding.ActivityMainBinding
import io.lb.firebaseexample.settings_feature.presentation.MainSettingsFragment
import io.lb.firebaseexample.settings_feature.presentation.SettingsViewModel
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
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivityMainBinding
    private var id = 0

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainTodosViewModel by viewModels {
        viewModelFactory
    }

    private val settingsViewModel: SettingsViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupResultLauncher()

        setupViewModel()
        setupUiEvents()
        setupBindings()

        setupNavController()
    }

    private fun setupResultLauncher() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            viewModel.getTodos()
        }
    }

    private fun setupViewModel() {
        viewModel.todos.observe(this) {
            id = it.size
        }

        settingsViewModel.getAllowRestartTodo()
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
                        onAddClicked()
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

    private fun onLogoutClicked(): Boolean {
        viewModel.onEvent(MainTodosEvent.PressedLogout)
        return true
    }

    private fun onAddClicked() {
        val i = Intent(this, TodoDetailsActivity::class.java)
        i.putExtra(TodoDetailsActivity.ID, id)
        resultLauncher.launch(i)
    }

    private fun onTodoClicked(id: Int, todo: Todo) {
        val i = Intent(this, TodoDetailsActivity::class.java)
        i.putExtra(TodoDetailsActivity.ID, id)
        i.putExtra(TodoDetailsActivity.TODO, todo)
        resultLauncher.launch(i)
    }

    private fun setupNavController() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        onFragmentChangeListener(menu)
        return true
    }

    private fun onFragmentChangeListener(menu: Menu) {
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            menu.getItem(0).isVisible =
                destination.label != getString(R.string.action_settings)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> onSettingsClicked()
            R.id.action_logout -> onLogoutClicked()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onSettingsClicked(): Boolean {
        findNavController(R.id.nav_host_fragment_content_main)
            .navigate(R.id.action_MainTodosFragment_to_MainSettingsFragment)
        return true
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