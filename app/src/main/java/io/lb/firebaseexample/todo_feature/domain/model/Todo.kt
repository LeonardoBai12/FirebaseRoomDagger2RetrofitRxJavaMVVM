package io.lb.firebaseexample.todo_feature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var date: String? = null,
    var deadline: String? = null,
    var isCompleted: Boolean = false
) : Serializable

class InvalidTodoException(message: String) : Exception(message)
