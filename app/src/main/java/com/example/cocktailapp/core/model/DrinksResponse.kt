package com.example.cocktailapp.core.model

import com.google.gson.annotations.SerializedName

class DrinksResponse {

    @SerializedName("drinks")
    var drinks: List<Drink>? = null
}

class Drink {
    fun getIngredients(): List<String> {
        return listOf(
            ingredient1 ?: "",
            ingredient2 ?: "",
            ingredient3 ?: "",
            ingredient4 ?: "",
            ingredient5 ?: "",
            ingredient6 ?: "",
            ingredient7 ?: "",
            ingredient8 ?: "",
            ingredient9 ?: "",
            ingredient10 ?: "",
            ingredient11 ?: "",
            ingredient12 ?: "",
            ingredient13 ?: "",
            ingredient14 ?: "",
            ingredient15 ?: ""
        )
    }

    fun getMeasures(): List<String> {
        return listOf(
            measure1 ?: "",
            measure2 ?: "",
            measure3 ?: "",
            measure4 ?: "",
            measure5 ?: "",
            measure6 ?: "",
            measure7 ?: "",
            measure8 ?: "",
            measure9 ?: "",
            measure10 ?: "",
            measure11 ?: "",
            measure12 ?: "",
            measure13 ?: "",
            measure14 ?: "",
            measure15 ?: ""
        )
    }

    @SerializedName("idDrink")
    var id: String? = null

    @SerializedName("strDrink")
    var title: String? = null

    @SerializedName("strDrinkAlternate")
    var alternate: String? = null

    @SerializedName("strTags")
    var tags: String? = null

    @SerializedName("strCategory")
    var category: String? = null

    @SerializedName("strAlcoholic")
    var alcoholic: String? = null

    @SerializedName("strGlass")
    var glass: String? = null

    @SerializedName("strInstructions")
    var instructions: String? = null

    @SerializedName("strDrinkThumb")
    var imageURL: String? = null

    @SerializedName("strIngredient1")
    var ingredient1: String? = null

    @SerializedName("strIngredient2")
    var ingredient2: String? = null

    @SerializedName("strIngredient3")
    var ingredient3: String? = null

    @SerializedName("strIngredient4")
    var ingredient4: String? = null

    @SerializedName("strIngredient5")
    var ingredient5: String? = null

    @SerializedName("strIngredient6")
    var ingredient6: String? = null

    @SerializedName("strIngredient7")
    var ingredient7: String? = null

    @SerializedName("strIngredient8")
    var ingredient8: String? = null

    @SerializedName("strIngredient9")
    var ingredient9: String? = null

    @SerializedName("strIngredient10")
    var ingredient10: String? = null

    @SerializedName("strIngredient11")
    var ingredient11: String? = null

    @SerializedName("strIngredient12")
    var ingredient12: String? = null

    @SerializedName("strIngredient13")
    var ingredient13: String? = null

    @SerializedName("strIngredient14")
    var ingredient14: String? = null

    @SerializedName("strIngredient15")
    var ingredient15: String? = null

    @SerializedName("strMeasure1")
    var measure1: String? = null

    @SerializedName("strMeasure2")
    var measure2: String? = null

    @SerializedName("strMeasure3")
    var measure3: String? = null

    @SerializedName("strMeasure4")
    var measure4: String? = null

    @SerializedName("strMeasure5")
    var measure5: String? = null

    @SerializedName("strMeasure6")
    var measure6: String? = null

    @SerializedName("strMeasure7")
    var measure7: String? = null

    @SerializedName("strMeasure8")
    var measure8: String? = null

    @SerializedName("strMeasure9")
    var measure9: String? = null

    @SerializedName("strMeasure10")
    var measure10: String? = null

    @SerializedName("strMeasure11")
    var measure11: String? = null

    @SerializedName("strMeasure12")
    var measure12: String? = null

    @SerializedName("strMeasure13")
    var measure13: String? = null

    @SerializedName("strMeasure14")
    var measure14: String? = null

    @SerializedName("strMeasure15")
    var measure15: String? = null

    @SerializedName("dateModified")
    var date: String? = null

}