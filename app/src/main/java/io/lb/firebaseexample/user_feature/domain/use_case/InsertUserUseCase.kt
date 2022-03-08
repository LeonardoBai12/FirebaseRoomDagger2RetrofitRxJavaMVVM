package io.lb.firebaseexample.user_feature.domain.use_case

import io.lb.firebaseexample.user_feature.domain.model.User
import io.lb.firebaseexample.user_feature.domain.repository.UserRepository

class InsertUserUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke(user: User) {
        repository.insertUserToDatabase(user)
    }
}