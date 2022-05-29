package com.test.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.myapplication.api.response.Gist
import com.test.myapplication.state.GistListContentAvailableState
import com.test.myapplication.useCase.FavouriteGistUseCase
import com.test.myapplication.useCase.FetchGistListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GistListViewModel(
    val fetchGistListUseCase: FetchGistListUseCase,
    val favouriteGistUseCase: FavouriteGistUseCase
) : ViewModel() {


    var flow: MutableStateFlow<GistListContentAvailableState> = MutableStateFlow(
        GistListContentAvailableState(
            emptyList()
        )
    )

    val onFavourite: (Gist) -> Unit = {
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

    fun start() {
        fetchGistListUseCase.start().subscribe({
            viewModelScope.launch {
                updateFlow(it)
            }
        }, {
            it.printStackTrace()
        })
    }

    suspend fun updateFlow(list: List<Gist>) {
        flow.emit(GistListContentAvailableState(list))
    }

    fun update(gist: Gist) {
        favouriteGistUseCase.updateFavourite(gist).subscribe {
            start()
        }
    }

}