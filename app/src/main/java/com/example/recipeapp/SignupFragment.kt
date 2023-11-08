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
import com.example.recipeapp.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences

    private val binding by lazy {
        FragmentSignupBinding.inflate(layoutInflater)
    }

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

        binding.btnSignup.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val isSignUp = sharedPref.getBoolean("isSignUp", false)

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password.length >= 6) {
                val editor = sharedPref.edit()
                editor.apply {
                    putString("name", name)
                    putString("email", email)
                    putString("password", password)
                    putBoolean("isLogin", false)
                    putBoolean("isSignUp", true)
                    apply()
                }

                //findNavController().navigate(R.id.action_signupFragment_to_loginFragment)

                val bottomSheetFragment = SignupBottomFragment()
                bottomSheetFragment.show(
                    requireActivity().supportFragmentManager,
                    bottomSheetFragment.tag
                )

            } else {
                if (password.length < 6) {
                    Toast.makeText(context, "Password length at least 6", Toast.LENGTH_SHORT).show()
                } else if (isSignUp) Toast.makeText(
                    context,
                    "You already create an account",
                    Toast.LENGTH_SHORT
                ).show()
                else Toast.makeText(context, "Required Fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
    }

    private fun init() {
        sharedPref = requireActivity().getSharedPreferences("onUser", Context.MODE_PRIVATE)
    }
}