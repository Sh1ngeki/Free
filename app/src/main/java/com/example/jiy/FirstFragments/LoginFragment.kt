package com.example.jiy.FirstFragments

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.jiy.Friends
import com.example.jiy.FullNavFragment
import com.example.jiy.R
import com.example.jiy.postistvis.PostClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import javax.crypto.AEADBadTagException


class LoginFragment:Fragment(R.layout.login_fragment) {
    private lateinit var button: Button
    private lateinit var noAccount: TextView
    private lateinit var forgotPassword: TextView
    private lateinit var loginmail:EditText
    private lateinit var loginpass:EditText

    private var friendslist = arrayListOf<Friends>()
    private var database = FirebaseDatabase.getInstance()

    private lateinit var frnd1:ArrayList<String>
    private lateinit var poster:ArrayList<String>
    private lateinit var addfriendstxt:EditText
    private var userref = database.getReference("users")
    private var postclassarray = ArrayList<PostClass>()
    private var postclassarray1 = ArrayList<PostClass>()


    private var storagereference = FirebaseStorage.getInstance().getReference("users")
    private var storage1 = FirebaseStorage.getInstance().getReference("default")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        val preferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = preferences?.edit()



        var fragmentManager = activity?.supportFragmentManager
        var fragmentTransaction = fragmentManager?.beginTransaction()
        button  = view.findViewById(R.id.logIn)
        noAccount = view.findViewById(R.id.noAccount)
        forgotPassword = view.findViewById(R.id.forgotPassword)
        loginmail = view.findViewById(R.id.loginmail)
        loginpass = view.findViewById(R.id.loginpass)
        storagereference = FirebaseStorage.getInstance().getReference("users")
        storage1 = FirebaseStorage.getInstance().getReference("default")

        noAccount.setOnClickListener{

            fragmentTransaction?.replace(R.id.container, RegistrationFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        forgotPassword.setOnClickListener {

            fragmentTransaction?.add(R.id.container, ForgotPasswordFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        println(FirebaseAuth.getInstance().currentUser)
        if (FirebaseAuth.getInstance().currentUser !=null){
            val mail = preferences?.getString("email", "")
            val pass = preferences?.getString("password", "")
            println("saaaaaaaaaaaaaaa$mail$pass")
            if(mail !="" &&pass!=""){
                val builder = AlertDialog.Builder(requireContext())
                builder.setCancelable(false)
                builder.setView(R.layout.loading_fragment)
                val dialog = builder.create()
                dialog.show()
                userref.child("everyone").get().addOnSuccessListener {
                    if (it.exists()){
                        poster = it.value as ArrayList<String>
                        println(poster)
                        for(i in poster){
                            userref.child(i.trim()).child("posts").get()
                                .addOnSuccessListener {pos->
                                    if (pos.exists()){

                                        val allpost =pos.value as ArrayList<String>
                                        for (k in allpost){
                                            storagereference.listAll()
                                                .addOnSuccessListener { listResult ->
                                                    val items = listResult.items
                                                    var imageexistance = false
                                                    for (item in items) {
                                                        if (item.name.trim() == i.trim()) {
                                                            println("object exists")
                                                            imageexistance = true
                                                            storagereference.child(i.trim()).downloadUrl.addOnSuccessListener { uri ->
                                                                if (k.length > 1) {
                                                                    val post =
                                                                        PostClass(k, i.trim(), uri)
                                                                    postclassarray.add(post)
                                                                    println(postclassarray)
                                                                    println("aeaseaesesaes")
                                                                    MySingleton.postdata=postclassarray

                                                                }
                                                            }


                                                        }
                                                    }
                                                    if (!imageexistance){
                                                        println("araaaaaaaaaaaraaaaaaaaa")
                                                        storage1.child("Screenshot_20230120_043118.png").downloadUrl.addOnSuccessListener { uri ->
                                                            if (k.length > 1) {
                                                                val post =
                                                                    PostClass(k, i.trim(), uri)
                                                                postclassarray.add(post)
                                                                println(postclassarray)
                                                                println("aeaseaesesaes")
                                                                MySingleton.postdata=postclassarray


                                                            }
                                                        }
                                                    }
                                                }



                                        }


                                    }


                                }


                        }

                    }

                    userref.child(FirebaseAuth.getInstance().currentUser?.displayName.toString())
                        .get().addOnSuccessListener {kai->
                            if (kai.exists()){
                                val posterfriends = kai.child("friendsname").value as ArrayList<String>
                                println(posterfriends)
                                println("bjobjo megobari")
                                for(i in posterfriends){
                                    if (i.length>1) {
                                        userref.child(i.trim()).child("posts").get()
                                            .addOnSuccessListener { pos1 ->
                                                if (pos1.exists()) {
                                                    println("bjobjo $pos1")

                                                    val allpost = pos1.value as ArrayList<String>
                                                    println("bijooo$allpost")
                                                    for (k in allpost) {
                                                        storagereference.listAll()
                                                            .addOnSuccessListener { listResult ->
                                                                val items = listResult.items
                                                                var imageexistance = false
                                                                for (item in items) {
                                                                    if (item.name.trim() == i.trim()) {
                                                                        println("object exists")
                                                                        imageexistance = true
                                                                        storagereference.child(i.trim()).downloadUrl.addOnSuccessListener { uri ->
                                                                            if (k.length > 1) {
                                                                                val post =
                                                                                    PostClass(
                                                                                        k,
                                                                                        i.trim(),
                                                                                        uri
                                                                                    )
                                                                                postclassarray1.add(
                                                                                    post
                                                                                )
                                                                                println(
                                                                                    postclassarray1
                                                                                )
                                                                                println("megobrebtan ertad")
                                                                                MySingleton.friendpost =
                                                                                    postclassarray1


                                                                            }
                                                                        }


                                                                    }
                                                                }
                                                                if (!imageexistance) {
                                                                    println("araaaaaaaaaaaraaaaaaaaa")
                                                                    storage1.child("Screenshot_20230120_043118.png").downloadUrl.addOnSuccessListener { uri ->
                                                                        if (k.length > 1) {
                                                                            val post =
                                                                                PostClass(
                                                                                    k,
                                                                                    i.trim(),
                                                                                    uri
                                                                                )
                                                                            postclassarray1.add(post)
                                                                            println(postclassarray1)
                                                                            println("aeaseaesesaes")
                                                                            MySingleton.friendpost =
                                                                                postclassarray1


                                                                        }
                                                                    }
                                                                }
                                                            }


                                                    }


                                                }


                                            }

                                    }
                                }

                            }


                        }

                    Thread.sleep(3000)
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(mail!!, pass!!)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@LoginFragment.requireContext(),
                                "Welcome",
                                Toast.LENGTH_SHORT
                            ).show()
                            println(FirebaseAuth.getInstance().currentUser?.displayName.toString())



                            friendslist.clear()

                            println(FirebaseAuth.getInstance().uid)
                            userref.child(FirebaseAuth.getInstance().currentUser?.displayName.toString()).get()
                                .addOnSuccessListener {

                                    if (it.exists()) {
                                        println("uid")

                                        frnd1 = it.child("friendsname").value as ArrayList<String>
                                        println("frnd"+frnd1)
                                        println(frnd1.size)
                                        if (frnd1.size==1) {
                                            MySingleton.data = arrayListOf()
                                            loadprofileimage()
                                            Thread.sleep(100)
                                            fragmentTransaction?.replace(
                                                R.id.container,
                                                FullNavFragment()

                                            )
                                            println("datanull")
                                            dialog.dismiss()
                                            fragmentTransaction?.commit()


                                        }else {
                                            for (i in frnd1) {

                                                userref.child(i.trim()).get().addOnSuccessListener {
                                                    if (it.exists() && !i.trim().isEmpty()) {
                                                        //vamowmebt aris tu ara atvirtuli useris foto

                                                        storagereference.listAll()
                                                            .addOnSuccessListener { listResult ->
                                                                val items = listResult.items
                                                                var imageexistance = false
                                                                for (item in items) {
                                                                    if (item.name.trim() == i.trim()) {
                                                                        println("object exists")
                                                                        imageexistance = true
                                                                        storagereference.child(i.trim()).downloadUrl.addOnSuccessListener { uri ->
                                                                            val friend = Friends(
                                                                                uri.toString(),
                                                                                it.child("username").value.toString(),
                                                                                it.child("userid").value.toString(),
                                                                                it.child("gmail").value.toString(),
                                                                            )
                                                                            friendslist.add(friend)
                                                                            if (friendslist.size == frnd1.size - 1) {
                                                                                println("done")
                                                                                println(friendslist)

                                                                                MySingleton.data =
                                                                                    friendslist
                                                                                loadprofileimage()
                                                                                Thread.sleep(200)
                                                                                fragmentTransaction?.replace(
                                                                                    R.id.container,
                                                                                    FullNavFragment()
                                                                                )
                                                                                dialog.dismiss()
                                                                                fragmentTransaction?.commit()


                                                                            }
                                                                        }
                                                                    }

                                                                }
                                                                if (!imageexistance) {
                                                                    storage1.child("Screenshot_20230120_043118.png").downloadUrl.addOnSuccessListener { uri ->
                                                                        val friend = Friends(
                                                                            uri.toString(),
                                                                            it.child("username").value.toString(),
                                                                            it.child("userid").value.toString(),
                                                                            it.child("gmail").value.toString(),
                                                                        )
                                                                        friendslist.add(friend)
                                                                        if (friendslist.size == frnd1.size - 1) {
                                                                            println("done")
                                                                            println(friendslist)
                                                                            loadprofileimage()
                                                                            MySingleton.data =
                                                                                friendslist
                                                                            Thread.sleep(200)
                                                                            fragmentTransaction?.replace(
                                                                                R.id.container,
                                                                                FullNavFragment()
                                                                            )
                                                                            dialog.dismiss()
                                                                            fragmentTransaction?.commit()


                                                                        }
                                                                    }

                                                                }

                                                            }
                                                            .addOnFailureListener {
                                                                dialog.dismiss()

                                                            }

                                                    }
                                                }

                                            }
                                        }
                                    }

                                }


                        }
                }

            }

        }else {


            button.setOnClickListener {
                val mail = loginmail.text.toString()
                val pass = loginpass.text.toString()
                if (mail.isEmpty() || pass.isEmpty()) {

                } else {

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setCancelable(false)
                    builder.setView(R.layout.loading_fragment)
                    val dialog = builder.create()
                    dialog.show()
                    userref.child("everyone").get().addOnSuccessListener {
                        if (it.exists()){
                            poster = it.value as ArrayList<String>
                            println(poster)
                            for(i in poster){
                                userref.child(i.trim()).child("posts").get()
                                    .addOnSuccessListener {pos->
                                        if (pos.exists()){

                                            val allpost =pos.value as ArrayList<String>
                                            for (k in allpost){
                                                storagereference.listAll()
                                                    .addOnSuccessListener { listResult ->
                                                        val items = listResult.items
                                                        var imageexistance = false
                                                        for (item in items) {
                                                            if (item.name.trim() == i.trim()) {
                                                                println("object exists")
                                                                imageexistance = true
                                                                storagereference.child(i.trim()).downloadUrl.addOnSuccessListener { uri ->
                                                                    if (k.length > 1) {
                                                                        val post =
                                                                            PostClass(k, i.trim(), uri)
                                                                        postclassarray.add(post)
                                                                        println(postclassarray)
                                                                        println("aeaseaesesaes")
                                                                        MySingleton.postdata=postclassarray

                                                                    }
                                                                }


                                                            }
                                                        }
                                                        if (!imageexistance){
                                                            println("araaaaaaaaaaaraaaaaaaaa")
                                                            storage1.child("Screenshot_20230120_043118.png").downloadUrl.addOnSuccessListener { uri ->
                                                                if (k.length > 1) {
                                                                    val post =
                                                                        PostClass(k, i.trim(), uri)
                                                                    postclassarray.add(post)
                                                                    println(postclassarray)
                                                                    println("aeaseaesesaes")
                                                                    MySingleton.postdata=postclassarray


                                                                }
                                                            }
                                                        }
                                                    }



                                            }


                                        }


                                    }


                            }

                        }

                        userref.child(FirebaseAuth.getInstance().currentUser?.displayName.toString())
                            .get().addOnSuccessListener {kai->
                                if (kai.exists()){
                                    val posterfriends = kai.child("friendsname").value as ArrayList<String>
                                    println(posterfriends)
                                    println("bjobjo megobari")
                                    for(i in posterfriends){
                                        if (i.length>1) {
                                            userref.child(i.trim()).child("posts").get()
                                                .addOnSuccessListener { pos1 ->
                                                    if (pos1.exists()) {
                                                        println("bjobjo $pos1")

                                                        val allpost = pos1.value as ArrayList<String>
                                                        println("bijooo$allpost")
                                                        for (k in allpost) {
                                                            storagereference.listAll()
                                                                .addOnSuccessListener { listResult ->
                                                                    val items = listResult.items
                                                                    var imageexistance = false
                                                                    for (item in items) {
                                                                        if (item.name.trim() == i.trim()) {
                                                                            println("object exists")
                                                                            imageexistance = true
                                                                            storagereference.child(i.trim()).downloadUrl.addOnSuccessListener { uri ->
                                                                                if (k.length > 1) {
                                                                                    val post =
                                                                                        PostClass(
                                                                                            k,
                                                                                            i.trim(),
                                                                                            uri
                                                                                        )
                                                                                    postclassarray1.add(
                                                                                        post
                                                                                    )
                                                                                    println(
                                                                                        postclassarray1
                                                                                    )
                                                                                    println("megobrebtan ertad")
                                                                                    MySingleton.friendpost =
                                                                                        postclassarray1


                                                                                }
                                                                            }


                                                                        }
                                                                    }
                                                                    if (!imageexistance) {
                                                                        println("araaaaaaaaaaaraaaaaaaaa")
                                                                        storage1.child("Screenshot_20230120_043118.png").downloadUrl.addOnSuccessListener { uri ->
                                                                            if (k.length > 1) {
                                                                                val post =
                                                                                    PostClass(
                                                                                        k,
                                                                                        i.trim(),
                                                                                        uri
                                                                                    )
                                                                                postclassarray1.add(post)
                                                                                println(postclassarray1)
                                                                                println("aeaseaesesaes")
                                                                                MySingleton.friendpost =
                                                                                    postclassarray1


                                                                            }
                                                                        }
                                                                    }
                                                                }


                                                        }


                                                    }


                                                }

                                        }
                                    }

                                }


                            }

                        Thread.sleep(3000)
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(mail!!, pass!!)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this@LoginFragment.requireContext(),
                                    "Welcome",
                                    Toast.LENGTH_SHORT
                                ).show()
                                println(FirebaseAuth.getInstance().currentUser?.displayName.toString())



                                friendslist.clear()

                                println(FirebaseAuth.getInstance().uid)
                                userref.child(FirebaseAuth.getInstance().currentUser?.displayName.toString()).get()
                                    .addOnSuccessListener {

                                        if (it.exists()) {
                                            println("uid")

                                            frnd1 = it.child("friendsname").value as ArrayList<String>
                                            println("frnd"+frnd1)
                                            println(frnd1.size)
                                            if (frnd1.size==1) {
                                                MySingleton.data = arrayListOf()
                                                loadprofileimage()
                                                Thread.sleep(100)
                                                fragmentTransaction?.replace(
                                                    R.id.container,
                                                    FullNavFragment()

                                                )
                                                println("datanull")
                                                dialog.dismiss()
                                                fragmentTransaction?.commit()


                                            }else {
                                                for (i in frnd1) {

                                                    userref.child(i.trim()).get().addOnSuccessListener {
                                                        if (it.exists() && !i.trim().isEmpty()) {
                                                            //vamowmebt aris tu ara atvirtuli useris foto

                                                            storagereference.listAll()
                                                                .addOnSuccessListener { listResult ->
                                                                    val items = listResult.items
                                                                    var imageexistance = false
                                                                    for (item in items) {
                                                                        if (item.name.trim() == i.trim()) {
                                                                            println("object exists")
                                                                            imageexistance = true
                                                                            storagereference.child(i.trim()).downloadUrl.addOnSuccessListener { uri ->
                                                                                val friend = Friends(
                                                                                    uri.toString(),
                                                                                    it.child("username").value.toString(),
                                                                                    it.child("userid").value.toString(),
                                                                                    it.child("gmail").value.toString(),
                                                                                )
                                                                                friendslist.add(friend)
                                                                                if (friendslist.size == frnd1.size - 1) {
                                                                                    println("done")
                                                                                    println(friendslist)

                                                                                    MySingleton.data =
                                                                                        friendslist
                                                                                    loadprofileimage()
                                                                                    Thread.sleep(200)
                                                                                    fragmentTransaction?.replace(
                                                                                        R.id.container,
                                                                                        FullNavFragment()
                                                                                    )
                                                                                    dialog.dismiss()
                                                                                    fragmentTransaction?.commit()


                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                    if (!imageexistance) {
                                                                        storage1.child("Screenshot_20230120_043118.png").downloadUrl.addOnSuccessListener { uri ->
                                                                            val friend = Friends(
                                                                                uri.toString(),
                                                                                it.child("username").value.toString(),
                                                                                it.child("userid").value.toString(),
                                                                                it.child("gmail").value.toString(),
                                                                            )
                                                                            friendslist.add(friend)
                                                                            if (friendslist.size == frnd1.size - 1) {
                                                                                println("done")
                                                                                println(friendslist)
                                                                                loadprofileimage()
                                                                                MySingleton.data =
                                                                                    friendslist
                                                                                Thread.sleep(200)
                                                                                fragmentTransaction?.replace(
                                                                                    R.id.container,
                                                                                    FullNavFragment()
                                                                                )
                                                                                dialog.dismiss()
                                                                                fragmentTransaction?.commit()


                                                                            }
                                                                        }

                                                                    }

                                                                }
                                                                .addOnFailureListener {
                                                                    dialog.dismiss()

                                                                }

                                                        }
                                                    }

                                                }
                                            }
                                        }

                                    }


                            }
                    }
                }

            }
        }


        return view
    }



    private fun loadprofileimage() {



        val storagereference = FirebaseStorage.getInstance().getReference("users")
        val storage1 = FirebaseStorage.getInstance().getReference("default")

        val i =FirebaseAuth.getInstance().currentUser?.displayName.toString()
        storagereference.listAll()
            .addOnSuccessListener { listResult ->
                val items = listResult.items
                var imageexistance = false
                for (item in items) {
                    if (item.name.trim() == i.trim()) {
                        println("object exists")
                        imageexistance = true
                        storagereference.child(i.trim()).downloadUrl.addOnSuccessListener { uri ->
                            println("imageeee")
                            MySingleton.imageuri = uri



                        }
                    }

                }
                if (!imageexistance) {
                    storage1.child("Screenshot_20230120_043118.png").downloadUrl.addOnSuccessListener { uri ->

                            println("imageeee")
                            MySingleton.imageuri = uri

                        }.addOnFailureListener{
                            println("failed")
                            Toast.makeText(this.requireContext(), "failed", Toast.LENGTH_SHORT).show()


                    }

                }
            }
            .addOnFailureListener {

            }



    }

    object MySingleton {
        var data: ArrayList<Friends>? = null
        var imageuri: Uri? = null
        var postdata:ArrayList<PostClass>?=null
        var friendpost:ArrayList<PostClass>?=null
    }




}