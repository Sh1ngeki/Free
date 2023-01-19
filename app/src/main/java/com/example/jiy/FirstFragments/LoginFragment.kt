package com.example.jiy.FirstFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.jiy.R
import com.google.firebase.auth.FirebaseAuth

class LoginFragment:Fragment(R.layout.login_fragment) {
    private lateinit var button: Button
    private lateinit var noAccount: TextView
    private lateinit var forgotPassword: TextView
    private lateinit var loginmail:EditText
    private lateinit var loginpass:EditText
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        button  = view.findViewById(R.id.logIn)
        noAccount = view.findViewById(R.id.noAccount)
        forgotPassword = view.findViewById(R.id.forgotPassword)
        loginmail = view.findViewById(R.id.loginmail)
        loginpass = view.findViewById(R.id.loginpass)

        noAccount.setOnClickListener{
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.container, RegistrationFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        forgotPassword.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.container, ForgotPasswordFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        button.setOnClickListener {
            val mail = loginmail.text.toString()
            val pass = loginpass.text.toString()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(mail,pass)
                .addOnSuccessListener {  }

        }


        return view
    }

}