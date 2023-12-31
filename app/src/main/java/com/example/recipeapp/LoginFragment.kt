package com.example.recipeapp

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.databinding.FragmentLoginBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class LoginFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences

    private val loginBinding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        loginBinding.tvGoSignUp.setOnClickListener {
            val navController =
                Navigation.findNavController(context as Activity, R.id.nav_host_fragment)
            navController.navigate(R.id.signupFragment)
        }

        loginBinding.tvLogin.setOnClickListener {

            if (!validate()) {
                //onLoginFailed()
                return@setOnClickListener
            }

            val email = loginBinding.etEmail.text.toString().trim().replace(" ", "");
            val password = loginBinding.etPassword.text.toString().trim()

            val userEmail = sharedPref.getString("email", "")?.trim()
            val usePassword = sharedPref.getString("password", "")?.trim()

            if (userEmail == email && password == usePassword) {
                val editor = sharedPref.edit()
                editor.apply {
                    putBoolean("isLogin", true)
                    apply()
                }

                val navController =
                    Navigation.findNavController(context as Activity, R.id.nav_host_fragment)
                navController.popBackStack()
                navController.navigate(R.id.homeFragment)
                //findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

            } else {
                Toast.makeText(context, "You are not valid user", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validate(): Boolean {
        var valid = true

        val email = loginBinding.etEmail.text.toString().replace(" ", "");
        if (email.isEmpty() || Regex("[^@]+").matches(email)) {
            loginBinding.etEmail.error = "Enter a valid Email Id"
            valid = false
        } else {
            loginBinding.etEmail.error = null
        }
        if (loginBinding.etPassword.text.toString().length < 6) {
            loginBinding.etPassword.error = "Enter a valid password (minimum 6 characters)"
            valid = false
        } else {
            loginBinding.etPassword.error = null
        }
        return valid
    }

    private fun init() {
        sharedPref = requireActivity().getSharedPreferences("onUser", Context.MODE_PRIVATE)
    }
}