package io.lb.firebaseexample.user_feature.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
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

    override fun insertUserToDatabase(user: User) {
        dao.insertRecord(user)
    }

    override fun getUser(email: String, password: String): Task<AuthResult> {
        return dataSource.loginFirebaseUser(email, password)
    }
}