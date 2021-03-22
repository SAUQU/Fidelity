package com.example.fidelity.util

import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

//Singleton class
object Utils {
    const val dateFormatYYYYMMDD = "yyyy-MM-dd"

    fun getStringResponseFromRaw(response: ResponseBody): String? {
        var reader: BufferedReader? = null
        val sb = StringBuilder()
        try {
            reader = BufferedReader(InputStreamReader(response.byteStream()))
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    sb.append(line)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.toString()
    }
}