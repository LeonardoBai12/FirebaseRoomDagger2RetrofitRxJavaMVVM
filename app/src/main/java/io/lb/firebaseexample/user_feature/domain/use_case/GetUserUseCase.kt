package io.lb.firebaseexample.user_feature.domain.use_case

import com.google.firebase.auth.FirebaseUser
import io.lb.firebaseexample.user_feature.domain.model.User
import io.lb.firebaseexample.user_feature.domain.repository.UserRepository

class GetUserUseCase(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(user: FirebaseUser): User {
        return repository.getUser(user)
    }
}