package io.lb.firebaseexample.user_feature.domain.use_case

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import io.lb.firebaseexample.user_feature.domain.model.InvalidUserException
import io.lb.firebaseexample.user_feature.domain.repository.UserRepository

class CreateFirebaseUserUseCase (
    private val repository: UserRepository,
) {
    @Throws(InvalidUserException::class)
    operator fun invoke(email: String?, password: String?, repeatPassword: String?): Task<AuthResult> {
        if (email.isNullOrBlank()) {
            throw InvalidUserException("Por favor, digite seu usuário")
        }
        if (password.isNullOrBlank()) {
            throw InvalidUserException("Por favor, digite sua senha")
        }
        if (repeatPassword.isNullOrBlank()) {
            throw InvalidUserException("As senhas digitadas não conferem")
        }
        return repository.createFirebaseUser(email, password)
    }
}