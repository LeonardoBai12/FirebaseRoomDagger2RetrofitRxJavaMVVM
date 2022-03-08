package io.lb.firebaseexample.user_feature.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.lb.firebaseexample.user_feature.domain.model.User

class UserDataSource(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth,
) {
    fun insertUser(user: User, onCompleted: (Boolean, Exception?) -> Unit): Task<Void> {
        return database.reference
            .child("user")
            .child(user.userUID!!)
            .child(user.id.toString())
            .setValue(user).addOnCompleteListener {
                onCompleted(it.isSuccessful, it.exception)
            }
    }

    fun createFirebaseUser(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun loginFirebaseUser(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }
}