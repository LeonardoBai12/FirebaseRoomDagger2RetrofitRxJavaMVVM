package io.lb.firebaseexample.user_feature.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import io.lb.firebaseexample.user_feature.domain.model.User

interface UserRepository {
    fun createFirebaseUser(email: String, password: String): Task<AuthResult>
    fun insertUserToDatabase(user: User)
    fun getUser(email: String, password: String): Task<AuthResult>
}