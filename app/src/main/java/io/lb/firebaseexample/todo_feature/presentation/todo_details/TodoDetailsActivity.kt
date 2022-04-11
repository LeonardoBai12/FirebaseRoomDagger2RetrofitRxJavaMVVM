package io.lb.firebaseexample.todo_feature.presentation.todo_details

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import dagger.hilt.android.AndroidEntryPoint
import io.lb.firebaseexample.databinding.ActivityTodoDetailsBinding
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.util.datePickerDialog
import io.lb.firebaseexample.util.dateToString
import io.lb.firebaseexample.util.setupDebounceEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoDetailsBinding
    private var todo: Todo? = null
    private var id = 0
    private val viewModel: TodoDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUiEvents()
        setupFinishButton()

        setupTitleTextChanged()
        setupDescriptionTextChanged()
        setupDateTextChanged()
        setupDeadlineTextChanged()

        setupTexts()

        setupDateClick()
        setupDeadlineClick()
        setupHasDeadlineCheckbox()
    }

    private fun setupTexts() {
        id = intent.getSerializableExtra(ID) as Int
        todo = intent.getSerializableExtra(TODO) as Todo?

        todo?.let {
            binding.included.tilTodoTitle.editText?.setText(it.title)
            binding.included.tilTodoDescription.editText?.setText(it.description)
            binding.included.tilTodoDate.editText?.setText(it.date)
            binding.included.tilTodoDeadline.editText?.setText(it.deadline)
            disableIfCompleted(it.isCompleted)
        }
    }

    private fun disableIfCompleted(completed: Boolean) {
        binding.included.tilTodoTitle.editText?.isEnabled = !completed
        binding.included.tilTodoDescription.editText?.isEnabled = !completed
        binding.included.tilTodoDate.editText?.isEnabled = !completed
        binding.included.tilTodoDeadline.editText?.isEnabled = !completed
        binding.included.chkWithoutDeadline.isEnabled = !completed

        binding.included.btTodoFinish.visibility =
            if (completed) {
                View.GONE
            } else {
                View.VISIBLE
            }
    }

    private fun setupTitleTextChanged() {
        binding.included.tilTodoTitle.editText?.let {
            setupDebounceEditText(it)
        }?.subscribe {
            viewModel.onEvent(TodoDetailsEvent.EnteredTitle(it))
        }
    }

    private fun setupDescriptionTextChanged() {
        binding.included.tilTodoDescription.editText?.let {
            setupDebounceEditText(it)
        }?.subscribe {
            viewModel.onEvent(TodoDetailsEvent.EnteredDescription(it))
        }
    }

    private fun setupDateTextChanged() {
        binding.included.tilTodoDate.editText?.doAfterTextChanged {
            viewModel.onEvent(TodoDetailsEvent.EnteredDate(it.toString()))
        }
    }

    private fun setupDeadlineTextChanged() {
        binding.included.tilTodoDeadline.editText?.doAfterTextChanged {
            viewModel.onEvent(TodoDetailsEvent.EnteredDeadline(it.toString()))
        }
    }

    private fun setupUiEvents() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is TodoDetailsViewModel.UiEvent.ShowToast -> {
                        toastMakeText(event.message)
                    }
                    is TodoDetailsViewModel.UiEvent.Finish -> {
                        finish()
                    }
                }
            }
        }
    }

    private fun setupHasDeadlineCheckbox() {
        binding.included.chkWithoutDeadline.setOnCheckedChangeListener { _, isChecked ->
            binding.included.tilTodoDeadline.editText?.setText("")
            binding.included.tilTodoDeadline.editText?.isEnabled = !isChecked
        }
    }

    private fun setupDeadlineClick() {
        binding.included.tilTodoDeadline.editText?.setOnClickListener {
            val typedDate = binding.included.tilTodoDeadline.editText?.text.toString()
            val datePickerDialog = datePickerDialog(
                this,
                typedDate,
                onDeadlineDateSet()
            )
            datePickerDialog.show()
        }
    }

    private fun setupDateClick() {
        binding.included.tilTodoDate.editText?.setOnClickListener {
            val typedDate = binding.included.tilTodoDate.editText?.text.toString()
            val datePickerDialog = datePickerDialog(
                this,
                typedDate,
                onDateSet()
            )
            datePickerDialog.show()
        }
    }

    private fun setupFinishButton() {
        binding.included.btTodoFinish.setOnClickListener {
            viewModel.onEvent(TodoDetailsEvent.PressedFinish(id))
        }
    }

    private fun toastMakeText(text: String) {
        Toast.makeText(baseContext, text, Toast.LENGTH_LONG).show()
    }

    private fun onDateSet() : DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = dateToString(day, month, year)
            binding.included.tilTodoDate.editText?.setText(
                date
            )
        }
    }

    private fun onDeadlineDateSet(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = dateToString(day, month, year)
            binding.included.tilTodoDeadline.editText?.setText(
                date
            )
        }
    }

    companion object {
        const val ID = "io.lb.firebaseexample.todo_feature.presentation.todo_details.ID"
        const val TODO = "io.lb.firebaseexample.todo_feature.presentation.todo_details.TODO"
    }
}