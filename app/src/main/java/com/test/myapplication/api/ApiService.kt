package com.test.myapplication.api

import com.test.myapplication.api.response.Gist
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/gists/public?since")
    fun getGist(): Observable<List<Gist>>

    @GET("/users/{userName}/gists?since")
    fun getGistByUser(@Path("userName") userName: String): Observable<List<Gist>>
}