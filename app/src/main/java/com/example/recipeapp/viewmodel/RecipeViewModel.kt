package com.example.recipeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeViewModel(private val repository: Repository): ViewModel() {
    val recipeResponse: MutableLiveData<Response<Recipe>> = MutableLiveData()
    val recipesResponse: MutableLiveData<Response<Recipe>> = MutableLiveData()

    fun getRecipe(){
        viewModelScope.launch {
            val response = repository.getRecipe()
            recipeResponse.value = response
        }
    }

    fun getRecipes(query: String){
        viewModelScope.launch {
            val response = repository.getRecipes(query)
            recipesResponse.value = response
        }
    }
}