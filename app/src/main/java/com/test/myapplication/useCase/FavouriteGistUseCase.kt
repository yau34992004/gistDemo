package com.test.myapplication.useCase

import com.test.myapplication.api.response.Gist
import com.test.myapplication.data.source.DatabaseSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers

class FavouriteGistUseCase(
    val databaseSource: DatabaseSource
) {

    fun updateFavourite(gistEntity: Gist): Observable<Boolean> {
        return Observable.create(ObservableOnSubscribe<Boolean>() {
            databaseSource.updateFavourite(gistEntity)
            it.onNext(true)
            it.onComplete()
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}