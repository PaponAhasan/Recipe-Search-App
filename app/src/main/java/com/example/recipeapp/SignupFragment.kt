package com.example.recipeapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
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

            if (!validate()) {
                //onLoginFailed()
                return@setOnClickListener
            }

            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val isSignUp = sharedPref.getBoolean("isSignUp", false)

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
        }

        binding.tvHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        binding.togglePassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Show Password
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                // Hide Password
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            // Move the cursor to the end of the text to update the display properly
            binding.etPassword.setSelection(binding.etPassword.text!!.length)
        }
    }

    private fun validate(): Boolean {
        var valid = true

        val name = binding.etName.text.toString().replace(" ", "")
        if (name.isEmpty()) {
            binding.etName.error = "Enter a valid Name"
            valid = false
        } else {
            binding.etName.error = null
        }
        val email = binding.etEmail.text.toString().replace(" ", "")
        if (email.isEmpty() || Regex("[^@]+").matches(email)) {
            binding.etEmail.error = "Enter a valid Email Id"
            valid = false
        } else {
            binding.etEmail.error = null
        }
        if (binding.etPassword.text.toString().length < 6) {
            binding.etPassword.error = "Enter a valid password (minimum 6 characters)"
            valid = false
        } else {
            binding.etPassword.error = null
        }
        return valid
    }

    private fun init() {
        sharedPref = requireActivity().getSharedPreferences("onUser", Context.MODE_PRIVATE)
    }
}