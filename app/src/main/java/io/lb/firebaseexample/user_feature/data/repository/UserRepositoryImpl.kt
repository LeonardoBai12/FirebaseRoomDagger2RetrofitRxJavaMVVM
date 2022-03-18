package io.lb.firebaseexample.user_feature.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import io.lb.firebaseexample.user_feature.data.data_source.UserDAO
import io.lb.firebaseexample.user_feature.data.data_source.UserDataSource
import io.lb.firebaseexample.user_feature.domain.model.User
import io.lb.firebaseexample.user_feature.domain.repository.UserRepository

class UserRepositoryImpl(
    private val dataSource: UserDataSource,
    private val dao: UserDAO
): UserRepository {
    override fun createFirebaseUser(email: String, password: String): Task<AuthResult> {
        return dataSource.createFirebaseUser(email, password)
    }

    override fun insertUser(firebaseUser: FirebaseUser, name: String): Task<Void> {
        val user = User(0, firebaseUser.uid, name)
        dao.insertRecord(user)
        return dataSource.insertUser(user)
    }

    override fun getFirebaseUser(email: String, password: String): Task<AuthResult> {
        return dataSource.loginFirebaseUser(email, password)
    }

    override suspend fun getUser(user: FirebaseUser): User {
        return dataSource.getUser(user)[0]
    }
}