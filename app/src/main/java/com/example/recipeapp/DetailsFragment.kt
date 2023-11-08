package com.example.recipeapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.FragmentDetailsBinding
import com.example.recipeapp.model.SingleRecipeItem
import com.example.recipeapp.repository.Repository
import com.example.recipeapp.viewmodel.DetailsRecipeViewModel
import com.example.recipeapp.viewmodel.DetailsRecipeViewModelFactory

class DetailsFragment : Fragment() {

    private val detailsBinding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var detailsRecipeViewModel: DetailsRecipeViewModel

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return detailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeId = args.recipeId

        val repository = Repository()
        val recipeViewModelFactory = DetailsRecipeViewModelFactory(repository)
        detailsRecipeViewModel =
            ViewModelProvider(this, recipeViewModelFactory)[DetailsRecipeViewModel::class.java]
        detailsRecipeViewModel.getSingleRecipe(recipeId)
        detailsRecipeViewModel.recipeDetailsResponse.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                detailsBinding.spinKit.visibility = View.GONE
                showResponseData(it.body()?.recipe)
            } else Log.e(HomeFragment.TAG, it.errorBody().toString())
        }

        detailsBinding.ibArrowBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_homeFragment)
        }
    }

    private fun showResponseData(response: SingleRecipeItem?) {
        if (response != null) {
            detailsBinding.tvRecipeTitle.text = response.title
            detailsBinding.tvRecipePublisher.text = response.publisher
            detailsBinding.tvRecipeRank.text = response.social_rank.toString()


            // Convert the ArrayList to a single String
            val concatenatedString = StringBuilder()

            response.ingredients.forEach {
                concatenatedString.append("■ ").append(it).append("\n")
            }

            detailsBinding.tvRecipeIngredient.text = concatenatedString.toString()

            Glide.with(this)
                .load(response.image_url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(detailsBinding.ivRecipes)
        }
    }
}