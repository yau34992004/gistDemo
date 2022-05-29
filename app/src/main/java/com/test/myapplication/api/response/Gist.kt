package com.test.myapplication.api.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.JsonObject
import com.test.myapplication.room.GistConverter

@Entity
data class Gist(
    val url: String,
    @PrimaryKey
    val id: String,
    @TypeConverters(GistConverter::class)
    val owner: Owner,
    val userName: String?,
    val fileName: String?,
    val hasFavourite: Boolean?,
    @TypeConverters(GistConverter::class)
    val files: JsonObject,
    val count: Int
) {

}
