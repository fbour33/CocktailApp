package com.example.cocktailapp.core.service

import android.util.Log
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.DrinksResponse
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class SearchDrinkFetcher {

    private val client = OkHttpClient()
    fun fetchData(url: ApiUrls, searchText: String = "", callback: (DrinksResponse?)->Unit){

        val request = Request.Builder()
            .url(url.value + searchText)
            .build()


        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.localizedMessage?.let { Log.e("OKHTTP", it) }
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                val gson = Gson()
                val responseData = gson.fromJson(response.body?.string(), DrinksResponse::class.java)
                callback(responseData)
                Log.i("OKHTTP", "Search data correctly fetch ${responseData.drinks?.count()}")
            }

        })
    }
}