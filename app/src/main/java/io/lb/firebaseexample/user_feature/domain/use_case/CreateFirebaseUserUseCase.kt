package io.lb.firebaseexample.user_feature.domain.use_case

import io.lb.firebaseexample.user_feature.domain.model.InvalidUserException
import io.lb.firebaseexample.user_feature.domain.repository.UserRepository

class CreateFirebaseUserUseCase (
    private val repository: UserRepository,
) {
    @Throws(InvalidUserException::class)
    operator fun invoke(email: String?, password: String?) {
        if (email.isNullOrBlank()) {
            throw InvalidUserException("Por favor, digite seu usuário")
        }
        if (password.isNullOrBlank()) {
            throw InvalidUserException("Por favor, digite sua senha")
        }
        repository.createFirebaseUser(email, password)
    }
}