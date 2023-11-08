package com.example.recipeapp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            // Check if the user has Completed Onboarding
            if (onBoardingFinished()) {
                if(isLogin()){
                    findNavController().navigate(
                        R.id.action_splashFragment_to_homeFragment,
                        null, NavOptions.Builder()
                            .setPopUpTo(
                                R.id.splashFragment,
                                true
                            ).build()
                    )
                }
                else{
                    findNavController().navigate(
                        R.id.action_splashFragment_to_loginFragment,
                        null, NavOptions.Builder()
                            .setPopUpTo(
                                R.id.splashFragment,
                                true
                            ).build()
                    )
                }

            } else {
                findNavController().navigate(
                    R.id.action_splashFragment_to_viewPagerFragment,
                    null, NavOptions.Builder()
                        .setPopUpTo(
                            R.id.splashFragment,
                            true
                        ).build()
                )
            }
        }, 3000)
    }

    private fun isLogin(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onUser", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isLogin", false)
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}