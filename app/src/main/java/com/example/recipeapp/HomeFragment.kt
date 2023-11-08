package com.example.recipeapp

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.recipeapp.adapter.RecipeAdapter
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.RecipeItem
import com.example.recipeapp.repository.Repository
import com.example.recipeapp.viewmodel.RecipeViewModel
import com.example.recipeapp.viewmodel.RecipeViewModelFactory
import com.google.android.material.navigation.NavigationView
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences

    private var nvDrawer: NavigationView? = null
    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null

    private var _binding: FragmentHomeBinding? = null
    private val homeBinding get() = _binding!!
    private lateinit var adapter: RecipeAdapter
    private var recipeLists = mutableListOf<Recipe>()

    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialization()

        openNav(savedInstanceState)
        navClick()

        recipeViewModel.getRecipe()
        recipeViewModel.recipeResponse.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                parseResponseData(it)
            } else {
                homeBinding.spinKit.visibility = View.GONE
                Log.e(TAG, it.errorBody().toString())
            }
        }

        // setup imageSlider
        setUpImageSlider()

        //setup for serarch view
        setupSearchView()
    }

    private fun parseResponseData(response: Response<Recipe>) {

        homeBinding.spinKit.visibility = View.GONE

        val recipeItemList = mutableListOf<RecipeItem>()

        val count = response.body()?.count
        val list = response.body()?.recipes
        list?.forEach { it ->
            val imageUrl = it.image_url
            val publisher = it.publisher
            val publisherUrl = it.publisher_url
            val recipeId = it.recipe_id
            val socialRank = it.social_rank
            val sourceUrl = it.source_url
            val title = it.title

            val recipeItem = RecipeItem(
                imageUrl,
                publisher,
                publisherUrl,
                recipeId,
                socialRank,
                sourceUrl,
                title
            )
            recipeItemList.add(recipeItem)
        }

        val recipes = Recipe(count!!, recipeItemList)
        recipeLists.clear()
        recipeLists.add(recipes)

        homeBinding.tvCount.visibility  = requireView().visibility
        homeBinding.tvCount.text = "Number Of Items: $count"
        homeBinding.RvRecipe.layoutManager = GridLayoutManager(context, 2)
        adapter = RecipeAdapter(recipeLists, context)
        homeBinding.RvRecipe.adapter = adapter
        homeBinding.RvRecipe.setHasFixedSize(true)

        adapter.setOnClickListener(object : RecipeAdapter.OnClickListener {
            override fun onClick(position: Int, recipeId: String) {
                //Toast.makeText(context, position.toString(), Toast.LENGTH_LONG).show()
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(recipeId)
                view?.let { Navigation.findNavController(it).navigate(action) }
                //findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
            }
        })
    }

    private fun initialization() {

        sharedPref = requireActivity().getSharedPreferences("onUser", Context.MODE_PRIVATE)

        nvDrawer = view?.findViewById(R.id.nv_drawer)
        drawerLayout = view?.findViewById(R.id.drawerLayout)
        toolbar = view?.findViewById(R.id.toolbar)

        val repository = Repository()
        val recipeViewModelFactory = RecipeViewModelFactory(repository)
        recipeViewModel =
            ViewModelProvider(this, recipeViewModelFactory)[RecipeViewModel::class.java]

        homeBinding.spinKit.visibility = View.VISIBLE
    }

    private fun openNav(savedInstanceState: Bundle?) {

        val toggle =
            ActionBarDrawerToggle(
                activity,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
            )

        drawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            nvDrawer!!.setCheckedItem(R.id.drawerLayout)
        }
    }

    private fun navClick() {
        // for menu
        nvDrawer!!.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_profile -> {
                    val navController =
                    Navigation.findNavController(context as Activity, R.id.nav_host_fragment)
                    navController.navigate(R.id.profileFragment)
                }
            }
            drawerLayout!!.closeDrawer(GravityCompat.START)
            true
        }

        homeBinding.logout.setOnClickListener {
            sharedPref.edit().remove("onUser").apply()

            val editor = sharedPref.edit()
            editor.apply {
                putBoolean("isLogin", false)
                apply()
            }

            val navController =
                Navigation.findNavController(context as Activity, R.id.nav_host_fragment)
            // Clear the back stack
            navController.popBackStack()
            navController.navigate(R.id.loginFragment)
        }
    }

    fun openDrawer() {
        drawerLayout!!.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer(drawerLayout: DrawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun setUpImageSlider() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlide = homeBinding.imageSlider
        imageSlide.setImageList(imageList)
        imageSlide.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun setupSearchView() {
        homeBinding.svRecipe.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterMenuItem(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                //filterMenuItem(query)
                return true
            }
        })
    }

    private fun filterMenuItem(query: String?) {
        recipeLists.clear()
        //..api call
        recipeViewModel.getRecipes(query!!.trim())
        recipeViewModel.recipesResponse.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                parseResponseData(it)
            } else {
                homeBinding.spinKit.visibility = View.GONE
                Toast.makeText(context, "Recipe was not found. Type correct recipe name", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val TAG = "HomeFragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        closeDrawer(drawerLayout!!)
    }
}