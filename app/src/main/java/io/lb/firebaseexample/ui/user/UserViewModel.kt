package io.lb.firebaseexample.ui.user

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import io.lb.firebaseexample.model.user.User
import io.lb.firebaseexample.network.user.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    fun createFirebaseUser(
        context: AppCompatActivity,
        email: String,
        password: String,
        onCompleted: (Task<AuthResult>) -> Unit
    ): Task<AuthResult> {
        return repository.createFirebaseUser(context, email, password, onCompleted)
    }

    fun insertUser(
        uid: String,
        typedName: String,
        onCompleted: (Boolean, Exception?) -> Unit
    ): Task<Void> {
        val user = User(
            id = 0,
            userUID = uid,
            name = typedName
        )

        return repository.insertUser(user, onCompleted)
    }

    fun loadUser(): Task<DataSnapshot> {
        return repository.loadUsers()
    }

    fun loadUsersListener(onDataChanged: (User) -> Unit): ValueEventListener {
        return repository.loadUserListener(onDataChanged)
    }
}