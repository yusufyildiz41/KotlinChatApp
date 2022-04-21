package com.yusufyildiz.chatappwithfirebase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yusufyildiz.chatappwithfirebase.R
import com.yusufyildiz.chatappwithfirebase.databinding.FragmentSignUpBinding
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    private var _binding : FragmentSignUpBinding ?=null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth= Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var email = binding.emailEditText.text
        var password = binding.passwordEditText.text


        binding.signUpButton.setOnClickListener { view ->

            auth.createUserWithEmailAndPassword(email.toString(),password.toString()).addOnSuccessListener {

                var action = SignUpFragmentDirections.actionSignUpFragmentToLogInFragment()
                Navigation.findNavController(view).navigate(action)


            }.addOnFailureListener{ e->
                Toast.makeText(context,e.localizedMessage,Toast.LENGTH_LONG).show()
            }

        }

    }

}