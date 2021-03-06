package io.lb.firebaseexample.todo_feature.domain.use_case

data class TodoUseCases(
    val getTodosUseCase: GetTodosUseCase,
    val updateTodo: UpdateTodo,
    val saveTodoUseCase: SaveTodoUseCase,
    val logoutUseCase: LogoutUseCase,
    val getUserUseCase: GetUserUseCase,
)