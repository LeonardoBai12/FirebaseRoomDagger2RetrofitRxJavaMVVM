package io.lb.firebaseexample.util

import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun setupDebounceSearchTil(searchView: SearchView): Observable<String> {
    return Observable.create<String> { emitter ->
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String?): Boolean {
                if (!emitter.isDisposed) {
                    emitter.onNext(text ?: "")
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
    }.debounce(500, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun setupDebounceEditText(editText: EditText): Observable<String> {
    return Observable.create<String> { emitter ->
        editText.addTextChangedListener {
            if (!emitter.isDisposed) {
                emitter.onNext(it.toString())
            }
        }
    }.debounce(500, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}