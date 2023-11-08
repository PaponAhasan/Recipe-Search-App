package com.example.recipeapp.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipeapp.databinding.FragmentViewPagerBinding
import com.example.recipeapp.onboarding.screens.FirstScreenFragment
import com.example.recipeapp.onboarding.screens.SecondScreenFragment
import com.example.recipeapp.onboarding.screens.ThirdScreenFragment

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val viewPagerBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val view = viewPagerBinding.root

        val fragmentList = arrayListOf(
            FirstScreenFragment(),
            ThirdScreenFragment(),
            SecondScreenFragment(),
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        viewPagerBinding.viewPager.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}