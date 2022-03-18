package io.lb.firebaseexample.user_feature.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.lb.firebaseexample.user_feature.domain.model.User
import io.reactivex.rxjava3.core.Flowable

@Dao
interface UserDAO {
    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(user: User)

    @Query("DELETE FROM user")
    fun deleteAllRecords()
}