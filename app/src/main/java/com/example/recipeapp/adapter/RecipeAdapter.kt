package com.example.recipeapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.RecipeItemBinding
import com.example.recipeapp.model.Recipe
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.round

class RecipeAdapter(private val recipe: MutableList<Recipe>, val context: Context?) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var onClickListener: OnClickListener? = null

    inner class RecipeViewHolder(val binding: RecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = RecipeViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int = recipe[0].recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentItem = recipe[0].recipes[position]

        holder.binding.tvTitle.text = currentItem.title

        val number = currentItem.social_rank
        val socialRank:Double = String.format("%.2f", number).toDouble()
        holder.binding.tvRank.text = "Rank: $socialRank"

        Glide.with(holder.itemView.context)
            .load(currentItem.image_url)
            .placeholder(R.drawable.banner1)
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(holder.binding.ivRecipe)

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentItem.recipe_id)
            }
        }
    }

    fun updateList(newRecipes: MutableList<Recipe>) {
        Toast.makeText(context, newRecipes.size.toString(), Toast.LENGTH_LONG).show()
        recipe.clear()
        recipe.addAll(newRecipes)
        notifyDataSetChanged()
    }

    // A function to bind the onclickListener.
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, recipeId: String)
    }
}

