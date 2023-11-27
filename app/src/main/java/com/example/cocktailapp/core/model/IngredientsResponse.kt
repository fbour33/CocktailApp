package com.example.cocktailapp.core.model

import com.google.gson.annotations.SerializedName

class IngredientsResponse {

    @SerializedName("drinks")
    var ingredients: List<Ingredient>? = null
}

class Ingredient {

    @SerializedName("strIngredient1")
    var name: String? = null
}