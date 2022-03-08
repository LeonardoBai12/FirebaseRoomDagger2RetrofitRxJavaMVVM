package io.lb.firebaseexample.user_feature.domain.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): User? {
        if (data.isNullOrEmpty()) {
            return null
        }
        val listType = object : TypeToken<User?>() {}.type
        return gson.fromJson<User?>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObject: User?): String {
        return gson.toJson(someObject)
    }
}