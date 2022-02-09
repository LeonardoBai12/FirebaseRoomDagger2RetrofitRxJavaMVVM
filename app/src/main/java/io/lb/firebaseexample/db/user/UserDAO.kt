package io.lb.firebaseexample.db.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.lb.firebaseexample.model.user.User
import io.reactivex.rxjava3.core.Flowable

@Dao
interface UserDAO {
    @Query("SELECT * FROM user")
    fun getAllRecords(): Flowable<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(user: User)

    @Query("DELETE FROM user")
    fun deleteAllRecords()
}