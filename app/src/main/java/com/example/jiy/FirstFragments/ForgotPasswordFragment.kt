package com.example.jiy.FirstFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.jiy.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment:Fragment(R.layout.forgot_password_fragment) {
    private lateinit var backSignIn2 : TextView
    private lateinit var forgormail:EditText
    private lateinit var forgorbutton:Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.forgot_password_fragment, container, false)
        backSignIn2 = view.findViewById(R.id.backSignIn2)
        forgormail = view.findViewById(R.id.forgormail)
        forgorbutton = view.findViewById(R.id.forgorbutton)
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        backSignIn2.setOnClickListener{

            fragmentTransaction?.replace(R.id.container, LoginFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        forgorbutton.setOnClickListener {
            if (forgormail.text.isEmpty()){
            }else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(forgormail.text.toString())
                    .addOnSuccessListener { Toast.makeText(this.requireContext(), "Check E-mail", Toast.LENGTH_SHORT).show()}
                    .addOnFailureListener{
                        Toast.makeText(this.requireContext(), "Enter the E-mail you signed up with", Toast.LENGTH_SHORT).show()
                    }
            }
        }



        return view
    }

}