package com.hannibalprojects.sampleproject.data.remote

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.InputStreamReader

object Utils {

    fun MockWebServer.enqueueWithCode(filename: String, code: Int) {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(filename))
        val content = reader.readText()
        reader.close()
        this.enqueue(
            MockResponse().setBody(content).setResponseCode(code)
        )
    }
}