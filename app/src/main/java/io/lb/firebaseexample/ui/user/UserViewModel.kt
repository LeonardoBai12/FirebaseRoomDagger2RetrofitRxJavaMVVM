package io.lb.firebaseexample.ui.user

import androidx.lifecycle.ViewModel
import io.lb.firebaseexample.network.user.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    //fun makeQuery(): LiveData<ArrayList<User>> {
    //    // Um exemplo caso quiséssemos transformar um observável em LiveData
    //    return repository.makeReactiveQueryForTodos().toLiveData()
    //}
}