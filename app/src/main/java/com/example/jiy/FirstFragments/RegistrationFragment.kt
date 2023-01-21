package com.example.jiy.FirstFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.jiy.FullNavFragment
import com.example.jiy.HomeActivity
import com.example.jiy.R
import com.example.jiy.SecondFragments.ProfileFragment
import com.example.jiy.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*



class RegistrationFragment:Fragment(R.layout.registration_fragment) {
    private lateinit var backSignIn1 : TextView
    private lateinit var signupbutton:Button
    private lateinit var usernametext:EditText
    private lateinit var emailtext:EditText
    private lateinit var pass1text:EditText
    private lateinit var pass2text:EditText
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.registration_fragment, container, false)
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        backSignIn1 = view.findViewById(R.id.backSignIn1)
        emailtext = view.findViewById(R.id.regmail)
        pass1text=view.findViewById(R.id.regpass1)
        pass2text=view.findViewById(R.id.regpass2)
        usernametext = view.findViewById(R.id.regname)
        signupbutton = view.findViewById(R.id.signUp)
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        signupbutton.setOnClickListener {
            val mail = emailtext.text.toString()
            val username = usernametext.text.toString()
            val pass1 = pass1text.text.toString()
            val pass2 = pass2text.text.toString()
            val emptylist= arrayListOf<String>()
            emptylist.add("")

            if (!mail.isEmpty()&&!username.isEmpty()&&!pass1.isEmpty()&&!pass2.isEmpty()){
                if (pass1==pass2) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, pass1)
                        .addOnSuccessListener {
                            val query = databaseReference.orderByChild("username").equalTo(username)
                            query.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        println("already")
                                        FirebaseAuth.getInstance().currentUser?.delete()
                                    } else {
                                        // username is available, proceed with registration
                                        println("new")
                                        val us = Users(FirebaseAuth.getInstance().uid.toString(),username,mail,emptylist,)
                                        databaseReference.child(username).setValue(us)
                                        val profileUpdates = UserProfileChangeRequest.Builder()
                                            .setDisplayName(username)
                                            .build()

                                        FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdates)
                                            ?.addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    Toast.makeText(this@RegistrationFragment.requireContext(), "Welcome ${FirebaseAuth.getInstance().currentUser?.displayName}", Toast.LENGTH_SHORT).show()
                                                    fragmentTransaction?.add(R.id.container, FullNavFragment())
                                                    fragmentTransaction?.commit()
                                                }
                                            }







                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    // Handle error
                                }
                            })




                        }
                }
            }

        }




        backSignIn1.setOnClickListener{

            fragmentTransaction?.replace(R.id.container, LoginFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }





        return view
    }

}