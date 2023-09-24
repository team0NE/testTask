package com.team.testtask.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GifImage(
    var id: String?  = null,
    var title: String? = null,
    var originalUrl: String? = null,
    var fixWidthUrl: String? = null,
): Parcelable