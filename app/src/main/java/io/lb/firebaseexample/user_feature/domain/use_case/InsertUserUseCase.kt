package io.lb.firebaseexample.user_feature.domain.use_case

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import io.lb.firebaseexample.user_feature.domain.repository.UserRepository

class InsertUserUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke(user: FirebaseUser, name: String): Task<Void> {
        return repository.insertUser(user, name)
    }
}