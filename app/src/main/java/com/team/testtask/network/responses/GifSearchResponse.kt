package com.team.testtask.network.responses

import com.google.gson.annotations.SerializedName
import com.team.testtask.network.model.GifData

data class GifSearchResponse (
    @SerializedName("data"       ) var data       : ArrayList<GifData> = arrayListOf(),
    @SerializedName("pagination" ) var pagination : Pagination?     = Pagination(),
    @SerializedName("meta"       ) var meta       : Meta?           = Meta()
)

data class Pagination (

    @SerializedName("total_count" ) var totalCount : Int? = null,
    @SerializedName("count"       ) var count      : Int? = null,
    @SerializedName("offset"      ) var offset     : Int? = null

)

data class Meta (

    @SerializedName("status"      ) var status     : Int?    = null,
    @SerializedName("msg"         ) var msg        : String? = null,
    @SerializedName("response_id" ) var responseId : String? = null

)