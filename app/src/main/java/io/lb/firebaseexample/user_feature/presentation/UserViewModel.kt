package io.lb.firebaseexample.user_feature.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import io.lb.firebaseexample.user_feature.domain.model.User
import io.lb.firebaseexample.user_feature.data.data_source.UserDataSource
import io.lb.firebaseexample.user_feature.domain.use_case.UserUseCases
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val useCases: UserUseCases
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
}