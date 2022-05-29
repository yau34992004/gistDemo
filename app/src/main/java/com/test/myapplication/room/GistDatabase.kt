package com.test.myapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.myapplication.api.response.Gist
import com.test.myapplication.api.response.Owner

@Database(entities = [Gist::class], version = 1, exportSchema = false)
@TypeConverters
abstract class GistDatabase : RoomDatabase() {
    abstract fun gistDao(): GistDao
}