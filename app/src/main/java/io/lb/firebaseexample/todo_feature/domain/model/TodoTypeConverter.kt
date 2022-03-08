package io.lb.firebaseexample.todo_feature.domain.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TodoTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): Todo? {
        if (data.isNullOrEmpty()) {
            return null
        }
        val listType = object : TypeToken<Todo?>() {}.type
        return gson.fromJson<Todo?>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObject: Todo?): String {
        return gson.toJson(someObject)
    }
}