package com.example.recipeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.model.SingleRecipe
import com.example.recipeapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailsRecipeViewModel(private val repository: Repository) : ViewModel() {
    val recipeDetailsResponse: MutableLiveData<Response<SingleRecipe>> = MutableLiveData()

    fun getSingleRecipe(rId: String) {
        viewModelScope.launch {
            val response = repository.getSingleRecipe(rId)
            recipeDetailsResponse.value = response
        }
    }
}