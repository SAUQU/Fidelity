package com.example.fidelity.viewmodel.animelist

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fidelity.AppApplication
import com.example.fidelity.model.AnimeModel
import com.example.fidelity.network.AnimeApiManager
import com.example.fidelity.network.network_response.CoreNetworkResponse
import com.example.fidelity.util.Utils
import com.example.fidelity.viewmodel.animelist.adapters.AnimeAdapter
import kotlinx.coroutines.launch
import java.util.*

class AnimeViewModel(appApplication: Application):ViewModel() {

    val dataLoading = MutableLiveData(false)
    var errorMessage: String = ""
    val data: ArrayList<AnimeModel> = ArrayList()
    private var apiService: AnimeApiManager = AnimeApiManager(appApplication)
    val adapter: AnimeAdapter = AnimeAdapter(appApplication, data)

    @VisibleForTesting
    constructor(
        apiClient: AnimeApiManager,
        appApplication: AppApplication
    ) : this(appApplication) {
        this.apiService = apiClient
    }

    @SuppressLint("CheckResult")
    fun loadData(animeName:String) {


        viewModelScope.launch {
            data.clear()
            onLoading()
            val response = apiService.getAnimeList(animeName)
            if (response.isSuccessful) {
                onSuccess(response.body())
            } else {
                response.errorBody()?.let { onError(Utils.getStringResponseFromRaw(it)) }
            }

        }
    }

    private fun onSuccess(animeModels: CoreNetworkResponse?) {

        animeModels?.results?.let { data.addAll(it) }
        adapter.notifyDataSetChanged()
        dataLoading.value = false
    }

    private fun onError(error: String?) {
        errorMessage = error ?: "Failed to search"
        dataLoading.value = false
    }

    private fun onLoading() {
        dataLoading.value = true
    }



}
