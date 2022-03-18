package io.lb.firebaseexample.user_feature.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import io.lb.firebaseexample.user_feature.domain.model.User

interface UserRepository {
    fun createFirebaseUser(email: String, password: String): Task<AuthResult>
    fun insertUser(firebaseUser: FirebaseUser, name: String): Task<Void>
    fun getFirebaseUser(email: String, password: String): Task<AuthResult>
    suspend fun getUser(user: FirebaseUser): User
}