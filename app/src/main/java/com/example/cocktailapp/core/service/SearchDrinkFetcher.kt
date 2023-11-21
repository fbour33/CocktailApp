package com.example.cocktailapp.core.service

import android.util.Log
import com.example.cocktailapp.core.model.Drink
import com.example.cocktailapp.core.model.DrinksResponse
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class SearchDrinkFetcher {

    private val client = OkHttpClient()
    private var URL = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s="

    fun fetchData(searchText: String, callback: (DrinksResponse?)->Unit){
        val request = Request.Builder()
            .url(URL + searchText)
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP", e.localizedMessage)
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("OKHTTP", "Search data correctly fetch")
                val gson = Gson()
                val responseData = response.body?.string()
                callback(gson.fromJson(responseData, DrinksResponse::class.java))
            }

        })
    }
}