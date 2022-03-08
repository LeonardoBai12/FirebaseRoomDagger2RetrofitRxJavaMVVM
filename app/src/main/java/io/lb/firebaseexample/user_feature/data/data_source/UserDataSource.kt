package io.lb.firebaseexample.user_feature.data.data_source

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
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

    fun loadUsers(): Task<DataSnapshot> {
        return database.getReference("user").get()
    }

    fun loadUserListener(onDataChanged: (User) -> Unit): ValueEventListener {
        return database.getReference("user").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hashMap = snapshot.getValue<HashMap<String, ArrayList<User>>>()
                    val user = hashMap?.get(auth.uid)
                    onDataChanged(user!![0])
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }
        )
    }

    fun createFirebaseUser(
        context: AppCompatActivity,
        email: String,
        password: String,
        onCompleted: (Task<AuthResult>) -> Unit
    ): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(context) {
                onCompleted(it)
            }
    }
}