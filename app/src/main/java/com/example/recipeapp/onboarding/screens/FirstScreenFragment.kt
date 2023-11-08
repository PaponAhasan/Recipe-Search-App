package com.example.recipeapp.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentFirstScreenBinding

class FirstScreenFragment : Fragment() {

    private var _binding: FragmentFirstScreenBinding? = null
    private val firstScreenBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        val view = firstScreenBinding.root

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        firstScreenBinding.next.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}