package com.example.jiy.SecondFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.jiy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class PostFragment:Fragment(R.layout.post_fragment) {


    private lateinit var  postbutton:Button
    private lateinit var  posttext:EditText
    private lateinit var databaseReference: DatabaseReference
    private var postarray = arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.post_fragment,container,false)
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        postbutton =  view.findViewById(R.id.postbutton)
        posttext = view.findViewById(R.id.posttext)

        postbutton.setOnClickListener {

            if (!posttext.text.isEmpty()){
                val builder = AlertDialog.Builder(this.requireContext())
                builder.setTitle("Title of Dialog")
                builder.setMessage("This is the message of the dialog.")
                builder.setPositiveButton("Yes") { dialog, _ ->
                    dialog.dismiss()
                    databaseReference.child(FirebaseAuth.getInstance().currentUser?.displayName.toString()).child("posts").get().addOnSuccessListener {
                        if (it.exists()){
                            postarray = it.value as ArrayList<String>

                            postarray.add(posttext.text.toString())
                            databaseReference.child(FirebaseAuth.getInstance().currentUser?.displayName.toString())
                                .child("posts").setValue(postarray)
                                .addOnSuccessListener { posttext.setText("") }


                        }
                    }







                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()

            }
        }



        return view
    }
}