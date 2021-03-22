package com.example.fidelity.network

import android.app.Application
import com.example.fidelity.AppApplication
import com.example.fidelity.network.Constants.CONNECTION_TIMEOUT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiEndPoint(private val application: Application) {

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .build()

    val retrofitInstance: Retrofit
        get() = Retrofit.Builder()
            .baseUrl((application as AppApplication).getBaseURL())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}