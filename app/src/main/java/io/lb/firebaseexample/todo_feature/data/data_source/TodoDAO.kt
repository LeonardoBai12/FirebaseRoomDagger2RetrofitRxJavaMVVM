package io.lb.firebaseexample.todo_feature.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.reactivex.rxjava3.core.Flowable

@Dao
interface TodoDAO {
    @Query("SELECT * FROM todo")
    fun getAllRecords(): Flowable<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(todo: Todo)

    @Query("DELETE FROM todo")
    fun deleteAllRecords()
}