package com.test.myapplication.room

import androidx.room.TypeConverter
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.test.myapplication.api.response.Owner

class GistConverter {

    @TypeConverter
    fun convertToOwner(content: String): Owner {
        return Owner(content)
    }

    @TypeConverter
    fun convertToString(owner: Owner): String {
        return owner.login
    }

    @TypeConverter
    fun convertJsonObjectToString(jsonObject: JsonObject): String {
        return jsonObject.toString()
    }

    @TypeConverter
    fun convertStringToJsonObject(string: String): JsonObject {
        return JsonParser.parseString(string).asJsonObject ?: JsonObject()
    }


}