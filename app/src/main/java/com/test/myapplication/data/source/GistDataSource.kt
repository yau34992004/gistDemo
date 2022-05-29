package com.test.myapplication.data.source

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.myapplication.api.ApiService
import com.test.myapplication.api.response.Gist
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GistDataSource {

    private var apiService: ApiService
    var gson: Gson

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
        gson = GsonBuilder().setPrettyPrinting().create()
    }

    fun getGist(): Observable<List<Gist>> {
        return apiService.getGist()
    }

    fun getGistByUser(userName: String): Observable<List<Gist>> {
        return apiService.getGistByUser(userName)
    }

}