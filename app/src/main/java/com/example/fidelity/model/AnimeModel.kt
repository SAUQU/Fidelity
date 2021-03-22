package com.example.fidelity.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnimeModel(
    var mal_id: Int? = null,
    var url: String? = null,
    var image_url: String? = null,
    var title: String? = null,
    var airing: Boolean? = null,
    var synopsis: String? = null,
    var type: String? = null,
    var episodes: String? = null,
    var score: String? = null,
    var start_date: String? = null,
    var end_date: String? = null,
    var members: Long? = null,
    var rated: String? = null
) : Parcelable