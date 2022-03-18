package io.lb.firebaseexample.user_feature.domain.use_case

data class UserUseCases(
    val loginUserUseCase: LoginFirebaseUserUseCase,
    val createUserUseCase: CreateFirebaseUserUseCase,
    val insertUserUseCase: InsertUserUseCase,
    val getUserUseCase: GetUserUseCase,
)