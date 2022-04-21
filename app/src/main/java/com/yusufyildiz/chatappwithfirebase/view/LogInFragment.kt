package com.yusufyildiz.chatappwithfirebase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yusufyildiz.chatappwithfirebase.R
import com.yusufyildiz.chatappwithfirebase.databinding.FragmentLogInBinding


class LogInFragment : Fragment() {
    // TODO: Rename and change types of parameters


    private lateinit var binding : FragmentLogInBinding
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser !=null)
        {
            val action = LogInFragmentDirections.actionLogInFragmentToChatFragment()
            findNavController().navigate(action)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLogInBinding.inflate(inflater,container,false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var email = binding.emailEditText.text
        var password = binding.passwordEditText.text

        binding.loginButton.setOnClickListener { view->
            auth.signInWithEmailAndPassword(email.toString(),password.toString()).addOnSuccessListener {
                var action = LogInFragmentDirections.actionLogInFragmentToChatFragment()
                Navigation.findNavController(view).navigate(action)
            }.addOnFailureListener{e->
                Toast.makeText(context,e.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

        binding.signUpButton.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
            Navigation.findNavController(it).navigate(action)

        }


    }
}