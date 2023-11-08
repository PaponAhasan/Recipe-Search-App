package com.example.recipeapp.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentFirstScreenBinding
import com.example.recipeapp.databinding.FragmentThirdScreenBinding


class ThirdScreenFragment : Fragment() {

    private var _binding: FragmentThirdScreenBinding? = null
    private val thirdScreenFragment get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        val view = thirdScreenFragment.root

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        thirdScreenFragment.next.setOnClickListener {
            viewPager?.currentItem = 2
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}