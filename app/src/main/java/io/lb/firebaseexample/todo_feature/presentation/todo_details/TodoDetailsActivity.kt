package io.lb.firebaseexample.todo_feature.presentation.todo_details

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import io.lb.firebaseexample.databinding.ActivityTodoDetailsBinding
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.util.datePickerDialog
import io.lb.firebaseexample.util.dateToString
import io.lb.firebaseexample.util.setupDebounceEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoDetailsActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityTodoDetailsBinding
    private var todo: Todo? = null
    private var id = 0

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: TodoDetailsViewModel by viewModels {
        viewModelFactory
    }

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
            binding.included.tvTodoTitle.editText?.setText(it.title)
            binding.included.tvTodoDescription.editText?.setText(it.description)
            binding.included.tvTodoDate.editText?.setText(it.date)
            binding.included.tvTodoDeadline.editText?.setText(it.deadline)
        }
    }

    private fun setupTitleTextChanged() {
        binding.included.tvTodoTitle.editText?.let {
            setupDebounceEditText(it)
        }?.subscribe {
            viewModel.onEvent(TodoDetailsEvent.EnteredTitle(it))
        }
    }

    private fun setupDescriptionTextChanged() {
        binding.included.tvTodoDescription.editText?.let {
            setupDebounceEditText(it)
        }?.subscribe {
            viewModel.onEvent(TodoDetailsEvent.EnteredDescription(it))
        }
    }

    private fun setupDateTextChanged() {
        binding.included.tvTodoDate.editText?.doAfterTextChanged {
            viewModel.onEvent(TodoDetailsEvent.EnteredDate(it.toString()))
        }
    }

    private fun setupDeadlineTextChanged() {
        binding.included.tvTodoDeadline.editText?.doAfterTextChanged {
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
            binding.included.tvTodoDeadline.editText?.setText("")
            binding.included.tvTodoDeadline.editText?.isEnabled = !isChecked
        }
    }

    private fun setupDeadlineClick() {
        binding.included.tvTodoDeadline.editText?.setOnClickListener {
            val typedDate = binding.included.tvTodoDeadline.editText?.text.toString()
            val datePickerDialog = datePickerDialog(
                this,
                typedDate,
                onDeadlineDateSet()
            )
            datePickerDialog.show()
        }
    }

    private fun setupDateClick() {
        binding.included.tvTodoDate.editText?.setOnClickListener {
            val typedDate = binding.included.tvTodoDate.editText?.text.toString()
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
            binding.included.tvTodoDate.editText?.setText(
                date
            )
        }
    }

    private fun onDeadlineDateSet(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = dateToString(day, month, year)
            binding.included.tvTodoDeadline.editText?.setText(
                date
            )
        }
    }

    companion object {
        const val ID = "io.lb.firebaseexample.todo_feature.presentation.todo_details.ID"
        const val TODO = "io.lb.firebaseexample.todo_feature.presentation.todo_details.TODO"
    }
}