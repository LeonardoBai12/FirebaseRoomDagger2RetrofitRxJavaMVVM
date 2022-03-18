package io.lb.firebaseexample.todo_feature.presentation.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.lb.firebaseexample.todo_feature.domain.use_case.TodoUseCases

class MainTodosViewModel(
    app: Application,
    private val useCases: TodoUseCases
): AndroidViewModel(app) {
}