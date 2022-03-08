package io.lb.firebaseexample.user_feature.domain.use_case

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import io.lb.firebaseexample.user_feature.domain.model.InvalidUserException
import io.lb.firebaseexample.user_feature.domain.repository.UserRepository

class LoginFirebaseUserUseCase(
    private val repository: UserRepository,
) {
    @Throws(InvalidUserException::class)
    operator fun invoke(email: String?, password: String?): Task<AuthResult> {
        if (email.isNullOrBlank()) {
            throw InvalidUserException("Por favor, digite seu usuário")
        }
        if (password.isNullOrBlank()) {
            throw InvalidUserException("Por favor, digite sua senha")
        }

        return repository.getUser(email, password)
    }
}