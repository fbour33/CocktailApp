package com.example.cocktailapp.core.model

import com.google.gson.annotations.SerializedName

class CategoriesResponse {

    @SerializedName("drinks")
    var categories: List<Category>? = null
}

class Category {

    @SerializedName("strCategory")
    var name: String? = null
}