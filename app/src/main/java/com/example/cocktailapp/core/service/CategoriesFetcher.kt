package com.example.cocktailapp.core.service

import android.util.Log
import com.example.cocktailapp.core.model.CategoriesResponse
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CategoriesFetcher {

    private val client = OkHttpClient()
    private var URL = "https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list"

    fun fetchData(callback: (CategoriesResponse?)->Unit){
        val request = Request.Builder()
            .url(URL)
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.localizedMessage?.let { Log.e("OKHTTP", it) }
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("OKHTTP", "Search data correctly fetch")
                val gson = Gson()
                val responseData = response.body?.string()
                callback(gson.fromJson(responseData, CategoriesResponse::class.java))
            }

        })
    }
}