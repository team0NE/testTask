package com.team.testtask.network.model

import com.google.gson.annotations.SerializedName

data class GifData (
    @SerializedName("id"     ) var id     : String? = null,
    @SerializedName("title"  ) var title  : String? = null,
    @SerializedName("images" ) var images : GifImages? = null
)