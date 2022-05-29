package com.test.myapplication.data.source

import android.content.Context
import androidx.room.Room
import com.test.myapplication.api.response.Gist
import com.test.myapplication.room.GistDao
import com.test.myapplication.room.GistDatabase
import io.reactivex.rxjava3.core.Observable

class DatabaseSource(val context: Context) {

    private var gistDatabase: GistDatabase
    private var gistDao: GistDao

    init {

        gistDatabase = Room.databaseBuilder(context, GistDatabase::class.java, "gist").build()
        gistDao = gistDatabase.gistDao()
    }

    fun insert(gists: List<Gist>) {
        gistDao.insert(gists)
    }

    fun queryAll(): Observable<List<Gist>> {
        return gistDao.queryAll()
    }

    fun queryByName(name: String): Observable<List<Gist>> {
        return gistDao.queryByName(name)
    }

    fun updateFavourite(gist: Gist) {
        gistDao.updateFavourite(gist)
    }

}