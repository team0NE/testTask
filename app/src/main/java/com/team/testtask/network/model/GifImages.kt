package com.team.testtask.network.model

import com.google.gson.annotations.SerializedName

data class GifImages (
    @SerializedName("original") var original   : Original?   = Original(),
    @SerializedName("fixed_width") var fixedWidth : FixedWidth? = FixedWidth()
)

data class Original (
    @SerializedName("url") var url: String? = null,
)

data class FixedWidth (
    @SerializedName("url") var url: String? = null,
)