package com.example.cocktailapp.core.service

import android.util.Log
import com.example.cocktailapp.core.model.ApiUrls
import com.example.cocktailapp.core.model.DrinksResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
                Log.d("OKHTTP", "Search data correctly fetch ${responseData.drinks?.count()}")
            }

        })
    }

    suspend fun fetchDataWithWaiting(url: ApiUrls, searchText: String = ""): DrinksResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(url.value + searchText)
                    .build()

                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    Gson().fromJson(responseBody, DrinksResponse::class.java)
                } else {
                    null
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("OKHTTP", it) }
                null
            }
        }
    }

}