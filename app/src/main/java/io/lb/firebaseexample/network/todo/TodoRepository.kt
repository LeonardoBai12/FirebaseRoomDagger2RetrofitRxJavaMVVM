package io.lb.firebaseexample.network.todo

import io.lb.firebaseexample.network.RetrofitServiceInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers

class TodoRepository(
    private val retrofitServiceInterface: RetrofitServiceInterface,
) {
    //fun makeReactiveQueryForTodos(): Flowable<ArrayList<Todo>> {
    //    return retrofitServiceInterface.getToDos().subscribeOn(Schedulers.io())
    //        .observeOn(AndroidSchedulers.mainThread())
    //}
}