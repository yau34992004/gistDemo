package com.test.myapplication.api.response

import com.google.gson.annotations.SerializedName

data class GistFiles(
    val fileName: String = "",
    val type: String = "",
    val language: String = "",
    @SerializedName("raw_url")
    val rawUrl: String = "",
    val size: Int = 0
)