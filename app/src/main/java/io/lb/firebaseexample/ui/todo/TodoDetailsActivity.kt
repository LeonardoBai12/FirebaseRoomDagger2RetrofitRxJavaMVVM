package io.lb.firebaseexample.ui.todo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.databinding.ActivityTodoDetailsBinding
import io.lb.firebaseexample.model.todo.Todo
import timber.log.Timber
import javax.inject.Inject

class TodoDetailsActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityTodoDetailsBinding
    private var id = 0

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var database: FirebaseDatabase

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: TodoViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupFinishButton()
    }

    private fun setupFinishButton() {
        binding.included.btTodoFinish.setOnClickListener {
            val todo = createTodoAccordingToFields()

            if (viewModel.validateTodo(todo)) {
                database.reference
                    .child("todo")
                    .child(auth.currentUser!!.uid)
                    .child(todo.id.toString())
                    .setValue(todo).addOnCompleteListener {
                        if (it.isSuccessful) {
                            viewModel.insertTodo(todo)
                            finish()
                        } else {
                            Timber.e(it.exception)
                            toastMakeText("Falha ao salvar tarefa. ${it.exception}")
                        }
                    }
            }
        }
    }

    private fun setupViewModel() {
        viewModel.loadTodosListener { todos ->
            id = todos.size
        }
        viewModel.loadTodos()
    }

    private fun toastMakeText(text: String) {
        Toast.makeText(baseContext, text, Toast.LENGTH_LONG).show()
    }

    private fun createTodoAccordingToFields(): Todo {
        val title = binding.included.tvTodoTitle.editText?.text.toString()
        val description = binding.included.tvTodoDescription.editText?.text.toString()
        val date = binding.included.tvTodoDate.editText?.text.toString()
        val deadline = binding.included.tvTodoDeadline.editText?.text.toString()
        return viewModel.createTodo(
            id,
            title,
            description,
            date,
            deadline
        )
    }
}