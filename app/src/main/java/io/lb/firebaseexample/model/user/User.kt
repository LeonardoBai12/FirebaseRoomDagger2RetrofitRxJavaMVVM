package io.lb.firebaseexample.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var userUID: String? = null
    var name: String? = null
}