package com.example.fidelity.network

import android.app.Application
import com.example.fidelity.network.network_response.CoreNetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class AnimeApiManager(application: Application) {

    private val apiService: RestService =
        ApiEndPoint(application).retrofitInstance.create(RestService::class.java)

    suspend fun getAnimeList(animeName:String) =
        apiService.getAnimeList(animeName)

    interface RestService {
        @GET(Constants.URL_SEARCH_DATA)
        suspend fun getAnimeList(@Query("q")animeName:String): Response<CoreNetworkResponse>
    }
}