package com.example.recipeapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }

    private lateinit var sharedPref: SharedPreferences

    private var isExpend = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        val userName = sharedPref.getString("name", "")?.trim()
        val userEmail = sharedPref.getString("email", "")?.trim()
        val usePassword = sharedPref.getString("password", "")?.trim()

        binding.tvProfileName.text = userName

        binding.ivArrowDown.setOnClickListener {
            if (isExpend) {

                binding.etName.setText(userName)
                binding.etEmail.setText(userEmail)
                binding.etPassword.setText(usePassword)

                binding.layoutUserInfo.visibility = View.VISIBLE
                binding.ivArrowDown.setImageResource(R.drawable.ic_arrow_up)
                binding.btnLogout.text = "Save Information"

                binding.btnLogout.setOnClickListener {
                    Toast.makeText(context, ".....", Toast.LENGTH_SHORT).show()
                }

            } else {
                binding.layoutUserInfo.visibility = View.GONE
                binding.ivArrowDown.setImageResource(R.drawable.ic_arrow_down)
                binding.btnLogout.text = "Logout"
            }
            isExpend = !isExpend
        }

        binding.ibArrowBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }
    }

    private fun init() {
        sharedPref = requireActivity().getSharedPreferences("onUser", Context.MODE_PRIVATE)
    }
}