package com.example.recipeapp

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.databinding.FragmentSignupBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SignupBottomFragment : BottomSheetDialogFragment() {

    private val signupBottomBinding by lazy {
        FragmentSignupBottomBinding.inflate(layoutInflater)
    }

    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return signupBottomBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        signupBottomBinding.btnSignIn.setOnClickListener {
            val navController = Navigation.findNavController(context as Activity, R.id.nav_host_fragment)
            navController.popBackStack()
            navController.navigate(R.id.loginFragment)
            dismiss()
        }

        val username = sharedPref.getString("name", "")
        signupBottomBinding.tvName.text = username
    }

    private fun init() {
        sharedPref = requireActivity().getSharedPreferences("onUser", Context.MODE_PRIVATE)
    }
}