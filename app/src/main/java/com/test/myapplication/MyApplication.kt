package com.test.myapplication

import android.app.Application
import com.test.myapplication.data.source.DatabaseSource
import com.test.myapplication.data.source.GistDataSource
import com.test.myapplication.useCase.FavouriteGistUseCase
import com.test.myapplication.useCase.FetchGistListUseCase
import com.test.myapplication.viewModel.DetailViewModel
import com.test.myapplication.viewModel.GistListViewModel

class MyApplication : Application() {

    lateinit var gistDataSource: GistDataSource
    lateinit var databaseSource: DatabaseSource

    lateinit var fetchGistListUseCase: FetchGistListUseCase
    lateinit var favouriteGistUseCase: FavouriteGistUseCase

    lateinit var gistListViewModel: GistListViewModel
    var detailViewModel: DetailViewModel? = null

    override fun onCreate() {
        super.onCreate()

        gistDataSource = GistDataSource()
        databaseSource = DatabaseSource(this)

        fetchGistListUseCase = FetchGistListUseCase(gistDataSource, databaseSource)

        favouriteGistUseCase = FavouriteGistUseCase(databaseSource)

        gistListViewModel = GistListViewModel(fetchGistListUseCase, favouriteGistUseCase)


    }


}