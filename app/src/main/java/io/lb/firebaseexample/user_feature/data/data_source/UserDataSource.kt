package io.lb.firebaseexample.user_feature.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import io.lb.firebaseexample.todo_feature.domain.model.InvalidTodoException
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.user_feature.domain.model.User
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class UserDataSource(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth,
) {
    fun insertUser(user: User): Task<Void> {
        return auth.currentUser?.let {
            database.reference
                .child("user")
                .child(it.uid)
                .child("0")
                .setValue(user)
        } ?: throw InvalidTodoException("Houve um erro ao salvar o usuário!")
    }

    suspend fun getUser(firebaseUser: FirebaseUser) : List<User> = suspendCancellableCoroutine {
        database.getReference("user").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hashMap = snapshot.getValue<HashMap<String, List<User>>>()
                    if (it.isActive) {
                        it.resume(hashMap?.get(firebaseUser.uid) ?: listOf())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }
        )
    }

    fun createFirebaseUser(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun loginFirebaseUser(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }
}