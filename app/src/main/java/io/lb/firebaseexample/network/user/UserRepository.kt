package io.lb.firebaseexample.network.user

import io.lb.firebaseexample.network.RetrofitServiceInterface

class UserRepository(
    private val retrofitServiceInterface: RetrofitServiceInterface,
) {
    //fun makeReactiveQueryForTodos(): Flowable<ArrayList<User>> {
    //    return retrofitServiceInterface.getToDos().subscribeOn(Schedulers.io())
    //        .observeOn(AndroidSchedulers.mainThread())
    //}
}