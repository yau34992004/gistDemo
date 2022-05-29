package com.test.myapplication.state

import com.test.myapplication.api.response.Gist

data class GistListContentAvailableState(
    val gistList: List<Gist>
)
