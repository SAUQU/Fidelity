package com.example.fidelity.network.network_response

import android.os.Parcelable
import com.example.fidelity.model.AnimeModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoreNetworkResponse(
    var request_hash: String? = null,
    var request_cached: Boolean? = null,
    var request_cache_expiry: Long? = null,
    var results: List<AnimeModel>? = null
) : Parcelable