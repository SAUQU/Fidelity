package com.example.fidelity

import android.app.Application
import com.example.fidelity.network.Constants

class AppApplication : Application() {

    fun getBaseURL() = Constants.BASE_URL
}