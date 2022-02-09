package io.lb.firebaseexample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.lb.firebaseexample.db.todo.TodoDAO
import io.lb.firebaseexample.db.user.UserDAO
import io.lb.firebaseexample.model.todo.Todo
import io.lb.firebaseexample.model.todo.TodoTypeConverter
import io.lb.firebaseexample.model.user.User
import io.lb.firebaseexample.model.user.UserTypeConverter

@Database(
    entities = [User::class, Todo::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    UserTypeConverter::class,
    TodoTypeConverter::class
)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getUserDao(): UserDAO
    abstract fun getTodoDao(): TodoDAO

    companion object {
        private var dbInstance: AppDataBase? = null

        fun getAppDataBaseInstance(context: Context): AppDataBase {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "APP_DB"
                ).build()
            }
            return dbInstance!!
        }
    }
}