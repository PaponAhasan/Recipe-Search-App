package com.example.recipeapp.repository

import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.SingleRecipe
import com.example.recipeapp.network.ApiClient
import retrofit2.Response

class Repository {

    suspend fun getRecipes(query: String): Response<Recipe> {
        return ApiClient.api.getRecipes(query)
    }

    suspend fun getRecipe(): Response<Recipe> {
        return ApiClient.api.getRecipe()
    }

    suspend fun getSingleRecipe(rId: String): Response<SingleRecipe> {
        return ApiClient.api.getSingleRecipe(rId)
    }
}