package com.example.recipeapp.model

data class RecipeItem(
    val image_url: String,
    val publisher: String,
    val publisher_url: String,
    val recipe_id: String,
    val social_rank: Double,
    val source_url: String,
    val title: String
)