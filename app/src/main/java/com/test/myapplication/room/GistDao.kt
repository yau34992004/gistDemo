package com.test.myapplication.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.test.myapplication.api.response.Gist
import io.reactivex.rxjava3.core.Observable

@Dao
interface GistDao {

    @Query("select * from Gist")
    fun queryAll(): Observable<List<Gist>>

    @Query("select * from Gist where userName = :name")
    fun queryByName(name: String): Observable<List<Gist>>

    @Insert(onConflict = IGNORE)
    fun insert(gists: List<Gist>)

    @Update
    fun updateFavourite(gistEntity: Gist)

}