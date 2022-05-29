package com.test.myapplication.useCase

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.test.myapplication.api.response.Gist
import com.test.myapplication.api.response.GistFiles
import com.test.myapplication.data.source.DatabaseSource
import com.test.myapplication.data.source.GistDataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class FetchGistListUseCase(
    val dataSource: GistDataSource,
    val databaseSource: DatabaseSource
) {

    fun start(): Observable<List<Gist>> {
//        if(true){
//            return databaseSource.queryAll().observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//        }// don't call network if rate limit exceed
        return dataSource.getGist().mapToLocal()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    private fun Observable<List<Gist>>.mapToLocal(): Observable<List<Gist>> {
        return this.map {
            val arrayList = ArrayList<Gist>()
            it.forEach {
                val copy = Gist(
                    id = it.id,
                    url = it.url,
                    owner = it.owner,
                    userName = it.owner.login,
                    fileName = it.files.convertJsonObjectToFiles().fileName,
                    hasFavourite = it.hasFavourite,
                    files = it.files,
                    count = it.count
                )
                arrayList.add(copy)
            }
            return@map arrayList
        }.map {
            databaseSource.insert(it)
            return@map it
        }.map { list ->
//            if (true) {
//                return@map list
//            }// don't call network if rate limit exceed

            list.forEach { item ->
                item.userName?.let { name ->
                    dataSource.getGistByUser(name).subscribe({
                        val gistCount = it.size
                        databaseSource.queryByName(name).subscribe({

                            val firstItem = it.first()
                            val copy = Gist(
                                id = firstItem.id,
                                url = firstItem.url,
                                owner = firstItem.owner,
                                userName = firstItem.owner.login,
                                fileName = firstItem.files.convertJsonObjectToFiles().fileName,
                                hasFavourite = firstItem.hasFavourite,
                                files = firstItem.files,
                                count = gistCount
                            )
                            databaseSource.updateFavourite(copy)
                        }, {
                            it.printStackTrace()
                        })
                    }, {
                        it.printStackTrace()
                    })
                }
            }
            return@map list
        }.flatMap {
            return@flatMap databaseSource.queryAll()
        }
    }

    fun JsonObject.convertJsonObjectToFiles(): GistFiles {
        val keys = this.keySet()
        keys.first()?.let {
            val item: JsonObject = this.get(it) as JsonObject
            val gistFiles = Gson().fromJson(item, GistFiles::class.java)
            return gistFiles
        }
        return GistFiles()
    }


    fun GistFiles.convertFilesToJsonObject(): JsonObject {
        val jsonObject = JsonObject()
        jsonObject.addProperty("fileName", this.fileName)
        jsonObject.addProperty("type", this.type)
        jsonObject.addProperty("language", this.language)
        jsonObject.addProperty("raw_url", this.rawUrl)
        jsonObject.addProperty("size", this.size)
        val jsonObject2 = JsonObject()
        jsonObject2.add(this.fileName, jsonObject)
        return jsonObject2
    }

}