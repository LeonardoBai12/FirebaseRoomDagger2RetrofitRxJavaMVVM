package io.lb.firebaseexample.model.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
class Todo {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var title: String? = null
    var description: String? = null
    var date: String? = null
    var deadline: String? = null
    var isCompleted: Boolean = false
}