package com.example.recipeapp.network

import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.SingleRecipe
import com.example.recipeapp.model.SingleRecipeItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/search")
    suspend fun getRecipes(
        @Query("q") q: String
    ): Response<Recipe>

    @GET("api/search/?q=pizza")
    suspend fun getRecipe(): Response<Recipe>

    @GET("api/get")
    suspend fun getSingleRecipe(
        @Query("rId") rId: String
    ): Response<SingleRecipe>
}