package com.example.fidelity

import java.io.InputStreamReader


class MockResponseFileReader(path: String) {
    var content: String?=null

    init {
        try {
            val isReader=this.javaClass.classLoader?.getResourceAsStream(path)
            val reader = InputStreamReader(isReader)
            content = reader.readText()
            reader.close()
        }catch (ex:Exception)
        {
            println(ex.localizedMessage)
        }
    }
}