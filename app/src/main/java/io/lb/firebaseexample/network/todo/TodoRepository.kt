package io.lb.firebaseexample.network.todo

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import io.lb.firebaseexample.model.todo.Todo

class TodoRepository(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth,
) {
    fun insertTodo(todo: Todo, onCompleted: (Boolean, Exception?) -> Unit): Task<Void> {
        return database.reference
            .child("todo")
            .child(auth.currentUser!!.uid)
            .child(todo.id.toString())
            .setValue(todo).addOnCompleteListener {
                onCompleted(it.isSuccessful, it.exception)
            }
    }

    fun loadTodos(): Task<DataSnapshot> {
        return database.getReference("todo").get()
    }

    fun loadTodosListener(onDataChanged: (ArrayList<Todo>) -> Unit): ValueEventListener {
        return database.getReference("todo").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hashMap = snapshot.getValue<HashMap<String, ArrayList<Todo>>>()
                    val todos = hashMap?.get(auth.uid)
                    onDataChanged(todos ?: arrayListOf())
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }
        )
    }
}