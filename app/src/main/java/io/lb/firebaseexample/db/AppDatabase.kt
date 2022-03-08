package io.lb.firebaseexample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.lb.firebaseexample.todo_feature.data.data_source.TodoDAO
import io.lb.firebaseexample.user_feature.data.data_source.UserDAO
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.user_feature.domain.model.User

@Database(
    entities = [User::class, Todo::class],
    version = 1,
)
abstract class AppDatabase: RoomDatabase() {
    abstract val userDao: UserDAO
    abstract val todoDao: TodoDAO

    companion object {
        const val DATABASE_NAME = "lb_todos_db"
    }
}