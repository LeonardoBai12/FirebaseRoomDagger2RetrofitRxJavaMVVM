package io.lb.firebaseexample.todo_feature.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import io.lb.firebaseexample.todo_feature.domain.model.InvalidTodoException
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class TodoDataSource(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth,
) {
    fun insertTodo(id: Int, todo: Todo): Task<Void> {
        return auth.currentUser?.let {
            database.reference
                .child("headset")
                .child(it.uid)
                .child(id.toString())
                .setValue(todo)
        } ?: throw InvalidTodoException("Houve um erro ao salvar a tarefa!")
    }

    fun logout() {
        auth.signOut()
    }

    suspend fun getTodos() : List<Todo> = suspendCancellableCoroutine {
        database.getReference("todo").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hashMap = snapshot.getValue<HashMap<String, List<Todo>>>()
                    if (it.isActive) {
                        it.resume(hashMap?.get(auth.uid) ?: listOf())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }
        )
    }
}