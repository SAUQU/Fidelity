package com.example.fidelity.viewmodel.animedetails

import androidx.lifecycle.ViewModel
import com.example.fidelity.model.AnimeModel
import com.example.fidelity.util.Utils.dateFormatYYYYMMDD
import com.example.fidelity.util.formatTo
import com.example.fidelity.util.toDate

class AnimeDetailsViewModel : ViewModel() {

    var selectedData: AnimeModel? = null

    val formattedDate: String
        get() = selectedData?.start_date?.toDate()?.formatTo(dateFormatYYYYMMDD) ?: ""

}