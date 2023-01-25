package com.example.jiy.SecondFragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.jiy.FirstFragments.LoginFragment
import com.example.jiy.FirstFragments.RegistrationFragment
import com.example.jiy.MainActivity
import com.example.jiy.PasswordChange
import com.example.jiy.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlin.math.sign

class ProfileFragment:Fragment(R.layout.profile_fragment) {
    private lateinit var displayuser:TextView
    private lateinit var displayimg:ImageView
    private lateinit var imagebutton:Button
    private lateinit var passwordbutton:Button
    private lateinit var signoutbutton:Button
    private var REQUEST_CODE = 1
    private lateinit var imageuri:Uri
    private lateinit var storageReference:StorageReference
    private lateinit var imguri:Uri
    private lateinit var abouttxt:TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayuser = view.findViewById(R.id.profileusername)
        displayimg = view.findViewById(R.id.profilePic)
        imagebutton = view.findViewById(R.id.changeimage)
        passwordbutton = view.findViewById(R.id.profilechangepassword)
        signoutbutton = view.findViewById(R.id.logout)
        abouttxt = view.findViewById(R.id.abouttext)
        storageReference=FirebaseStorage.getInstance().getReference("users")

        var fragmentManager = activity?.supportFragmentManager
        var fragmentTransaction = fragmentManager?.beginTransaction()

        if (imagedata.imagedata!=null){
            println("imageeeeee")
            Glide.with(this.requireContext()).load(imagedata.imagedata).into(displayimg)
        }

        else if (LoginFragment.MySingleton.imageuri !=null){
            imguri = LoginFragment.MySingleton.imageuri!!
            imageuri = imguri
            Glide.with(this.requireContext()).load(imageuri).into(displayimg)
        }



        displayuser.text = "${FirebaseAuth.getInstance().currentUser?.displayName}"
        imagebutton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)


        }
        signoutbutton.setOnClickListener {

            fragmentTransaction?.replace(R.id.container, LoginFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

            FirebaseAuth.getInstance().signOut()
            val preferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = preferences?.edit()
            editor?.clear()
            editor?.apply()



        }



        abouttxt.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setCancelable(true)
            builder.setView(R.layout.about_app)
            val dialog = builder.create()
            dialog.show()
        }
        passwordbutton.setOnClickListener {


            fragmentTransaction?.replace(R.id.container, PasswordChange())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()


        }






    }
    private fun uploadprofilepic() {

        storageReference = FirebaseStorage.getInstance().getReference("users/"+FirebaseAuth.getInstance().currentUser?.displayName.toString())
        storageReference.putFile(imageuri).addOnSuccessListener {


        }.addOnFailureListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val selectedImageUri = data.data


            displayimg.setImageURI(selectedImageUri)
            if (selectedImageUri != null) {
                imageuri = selectedImageUri
                uploadprofilepic()
                imagedata.imagedata = imageuri




            }
        }
    }

    object imagedata{
        var imagedata:Uri? = null

    }
}