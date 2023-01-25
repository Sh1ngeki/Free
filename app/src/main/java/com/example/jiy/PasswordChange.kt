package com.example.jiy

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.jiy.FirstFragments.RegistrationFragment
import com.example.jiy.SecondFragments.ProfileFragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class PasswordChange : Fragment(R.layout.change_password_fragment) {

    private lateinit var changebutton: Button
    private lateinit var currentpass: EditText
    private lateinit var newpass1: EditText
    private lateinit var newpass2: EditText
    private lateinit var backtext: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.change_password_fragment, container, false)

        val preferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = preferences?.edit()

        changebutton = view.findViewById(R.id.changebutton)
        currentpass = view.findViewById(R.id.currentpass)
        newpass1 = view.findViewById(R.id.newpass1)
        newpass2 = view.findViewById(R.id.newpass2)
        backtext = view.findViewById(R.id.back)

        backtext.setOnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.container, FullNavFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()


        }

        changebutton.setOnClickListener {



            val newpassw1 = newpass1.text.toString()
            val newpassw2 = newpass2.text.toString()
            val currentpassw = currentpass.text.toString()


            if (newpassw1.isNotEmpty()&&newpassw2.isNotEmpty()&&currentpassw.isNotEmpty()) {
                val user = FirebaseAuth.getInstance().currentUser
                val credential = EmailAuthProvider.getCredential(user?.email!!, currentpassw)

                user.reauthenticateAndRetrieveData(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {


                            if (newpassw1==newpassw2 && newpassw1.length>=6){
                                val builder = AlertDialog.Builder(this.requireContext())
                                builder.setTitle("Password change")
                                builder.setMessage("this action can not be undone!")
                                builder.setPositiveButton("OK") { dialog, _ ->
                                    dialog.dismiss()

                                    user?.updatePassword(newpassw1)
                                        ?.addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(context, "password changed", Toast.LENGTH_SHORT).show()
                                                editor?.remove("password")
                                                editor?.putString("password",newpassw1)
                                                editor?.apply()

                                            } else {
                                                Toast.makeText(context,"error",Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                }
                                builder.setNeutralButton("Cancel"){
                                        dialog,_ ->
                                    dialog.dismiss()

                                }
                                val dialog = builder.create()

                                dialog.show()


                            }

                        } else {
                            Toast.makeText(context, "old password is not correct", Toast.LENGTH_SHORT).show()
                            currentpass.setText("")
                            //Show error message to user

                        }
                    }
            }
        }



        return view
    }
}