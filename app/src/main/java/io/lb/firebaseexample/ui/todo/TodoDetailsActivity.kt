package io.lb.firebaseexample.ui.todo

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.databinding.ActivityTodoDetailsBinding
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.util.DateHelper
import timber.log.Timber
import javax.inject.Inject

class TodoDetailsActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityTodoDetailsBinding
    private var id = 0

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: TodoViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupErrorLiveData()
        setupViewModel()
        setupFinishButton()
        setupDateButton()
        setupDeadlineButton()
        setupHasDeadlineCheckbox()
    }

    private fun setupHasDeadlineCheckbox() {
        binding.included.chkWithoutDeadline.setOnCheckedChangeListener { _, isChecked ->
            binding.included.tvTodoDeadline.editText?.setText("")
            binding.included.tvTodoDeadline.editText?.isEnabled = !isChecked
        }
    }

    private fun setupDeadlineButton() {
        binding.included.tvTodoDeadline.editText?.setOnClickListener {
            val typedDate = binding.included.tvTodoDeadline.editText?.text.toString()
            val datePickerDialog = DateHelper.datePickerDialog(
                this,
                typedDate,
                onDeadlineDateSet()
            )
            datePickerDialog.show()
        }
    }

    private fun setupDateButton() {
        binding.included.tvTodoDate.editText?.setOnClickListener {
            val typedDate = binding.included.tvTodoDate.editText?.text.toString()
            val datePickerDialog = DateHelper.datePickerDialog(
                this,
                typedDate,
                onDateSet()
            )
            datePickerDialog.show()
        }
    }

    private fun setupErrorLiveData() {
        viewModel.error.observe(this) { error ->
            toastMakeText(error)
        }
    }

    private fun setupFinishButton() {
        binding.included.btTodoFinish.setOnClickListener {
            val todo = createTodoAccordingToFields()
            val hasDeadline = !binding.included.chkWithoutDeadline.isChecked

            if (viewModel.validateTodo(todo, hasDeadline)) {
                viewModel.insertTodo(todo) { isSuccessful, exception ->
                    if (isSuccessful) {
                        finish()
                    } else {
                        Timber.e(exception)
                        toastMakeText("Falha ao salvar tarefa. $exception")
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel.loadTodosListener { todos ->
            id = todos.size
        }
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

    private fun onDateSet() : DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = DateHelper.dateToString(day, month, year)
            binding.included.tvTodoDate.editText?.setText(
                date
            )
        }
    }

    private fun onDeadlineDateSet(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = DateHelper.dateToString(day, month, year)
            binding.included.tvTodoDeadline.editText?.setText(
                date
            )
        }
    }
}