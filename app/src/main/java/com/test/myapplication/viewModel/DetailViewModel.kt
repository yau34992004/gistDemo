package com.test.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.myapplication.api.response.Gist
import com.test.myapplication.state.GistDetailState
import com.test.myapplication.useCase.FavouriteGistUseCase
import com.test.myapplication.useCase.FetchGistListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    val fetchGistListUseCase: FetchGistListUseCase,
    val favouriteGistUseCase: FavouriteGistUseCase,
    val gist: Gist
) : ViewModel() {

    var flow: MutableStateFlow<GistDetailState> = MutableStateFlow(
        GistDetailState(
            gist
        )
    )

    val onFavourite: (Gist) -> Unit = {
        Log.i("test","checkFavourite")
        val copy = Gist(
            id = it.id,
            url = it.url,
            owner = it.owner,
            userName = it.userName,
            fileName = it.fileName,
            hasFavourite = true,
            files = it.files,
            count = it.count
        )
        update(copy)
    }

    val onUnFavourite: (Gist) -> Unit = {
        Log.i("test","checkUnFavourite")
        val copy = Gist(
            id = it.id,
            url = it.url,
            owner = it.owner,
            userName = it.userName,
            fileName = it.fileName,
            hasFavourite = false,
            files = it.files,
            count = it.count
        )
        update(copy)
    }


    fun update(gist: Gist) {
        Log.i("test","checkStartUpdateDatabase")
        favouriteGistUseCase.updateFavourite(gist).subscribe {
            Log.i("test", "checkUpdatedFavourite")
            viewModelScope.launch {
                val copy = Gist(
                    id = gist.id,
                    url = gist.url,
                    owner = gist.owner,
                    userName = gist.userName,
                    fileName = gist.fileName,
                    hasFavourite = gist.hasFavourite,
                    files = gist.files,
                    count = gist.count
                )

                updateFlow(copy)

            }
        }
    }

    suspend fun updateFlow(gist: Gist) {
        flow.emit(GistDetailState(gist))
    }
}