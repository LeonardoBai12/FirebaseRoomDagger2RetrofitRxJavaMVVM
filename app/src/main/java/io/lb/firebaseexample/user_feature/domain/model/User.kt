package io.lb.firebaseexample.user_feature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var userUID: String? = null,
    var name: String? = null,
)

class InvalidUserException(message: String) : Exception(message)
