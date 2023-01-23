package com.example.jiy.SecondFragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jiy.FirstFragments.LoginFragment
import com.example.jiy.Friends
import com.example.jiy.MainActivity
import com.example.jiy.PersonRecyclerAdapter
import com.example.jiy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.content.SharedPreferences as SharedPreferences1

class FriendsFragment:Fragment(R.layout.add_friends_fragment) {
    private lateinit var recyclerAdapter: PersonRecyclerAdapter
    private lateinit var recyclerview: RecyclerView

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var friendslist1 = arrayListOf<Friends>()
    private var database = FirebaseDatabase.getInstance()
    private lateinit var storagereference: StorageReference
    private lateinit var storage1: StorageReference
    private lateinit var frnd1:ArrayList<String>
    private lateinit var addfriendstxt:EditText
    private var userref = database.getReference("users")
    private var userfriendlist1 = arrayListOf<String>()
    private var userfriendlist2 = arrayListOf<String>()
    private var value = arrayListOf<Friends>()


    private lateinit var addfriendsbutton:Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout)
        addfriendstxt = view.findViewById(R.id.addfriendtext)
        recyclerview = view.findViewById(R.id.recycle)
        addfriendsbutton = view.findViewById(R.id.addfriendsbutton)
        storagereference = FirebaseStorage.getInstance().getReference("users")
        storage1 = FirebaseStorage.getInstance().getReference("default")

        println(value)

        if (LoginFragment.MySingleton.data !=null){
            println("aeiieieiejdeidjiejdiejdi"+LoginFragment.MySingleton.data!!)
            value = LoginFragment.MySingleton.data!!
            println("login "+ value)

            println("loginvalue")

        }
        friendslist1 = value
        println(value)


        recyclerAdapter = PersonRecyclerAdapter(friendslist1)
        recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerview.adapter = recyclerAdapter


        swipeRefreshLayout.setOnRefreshListener {
            friendslist1.clear()
            val window = requireActivity().window
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)


            userref.child(FirebaseAuth.getInstance().currentUser?.displayName.toString()).get().addOnSuccessListener {

                if (it.exists()) {


                    frnd1 = it.child("friendsname").value as ArrayList<String>


                    for (i in frnd1) {

                        userref.child(i.trim()).get().addOnSuccessListener {
                            if (it.exists() &&! i.trim().isEmpty()) {
                                //vamowmebt aris tu ara atvirtuli useris foto
                                storagereference.listAll()
                                    .addOnSuccessListener { listResult ->
                                        val items = listResult.items
                                        var imageexistance = false
                                        for (item in items) {
                                            if (item.name.trim() == i.trim()) {

                                                imageexistance = true
                                                storagereference.child(i.trim()).downloadUrl.addOnSuccessListener { uri ->
                                                    val friend = Friends(
                                                        uri.toString(),
                                                        it.child("username").value.toString(),
                                                        it.child("userid").value.toString(),
                                                        it.child("gmail").value.toString(),)
                                                    friendslist1.add(friend)
                                                    if (friendslist1.size == frnd1.size -1) {


                                                        getfriends(friendslist1)

                                                    }
                                                }
                                            }

                                        }
                                        if (!imageexistance){
                                            storage1.child("Screenshot_20230120_043118.png").downloadUrl.addOnSuccessListener { uri ->
                                                val friend = Friends(
                                                    uri.toString(),
                                                    it.child("username").value.toString(),
                                                    it.child("userid").value.toString(),
                                                    it.child("gmail").value.toString(),)
                                                friendslist1.add(friend)
                                                if (friendslist1.size == frnd1.size -1) {


                                                    getfriends(friendslist1)
                                                }
                                            }

                                        }
                                    }
                                    .addOnFailureListener {

                                    }


                            }
                        }

                    }

                }



            }


            Handler().postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing =false
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            },2000)


        }



        //megobrebis damateba
        addfriendsbutton.setOnClickListener {


            val friendusername = addfriendstxt.text.toString().trim()
            addfriendstxt.setText("")

            if (friendusername!=FirebaseAuth.getInstance().currentUser?.displayName.toString() &&friendusername.isNotEmpty()){
                userref.child(friendusername).get().addOnSuccessListener {u->
                if (u.exists()) {

                    userref.child(FirebaseAuth.getInstance().currentUser?.displayName.toString()).child("friendsname")
                        .get().addOnSuccessListener {a->
                            if (a.exists()) {
                                if (a.value.toString().contains(friendusername)){
                                    Toast.makeText(this.requireContext(), "User is already your friend", Toast.LENGTH_SHORT).show()
                                }else{
                                    //daamate megobari

                                    userref.child(FirebaseAuth.getInstance().currentUser?.displayName.toString())
                                        .get().addOnSuccessListener {
                                            if (it.exists()){
                                                userfriendlist1 = it.child("friendsname").value as ArrayList<String>


                                                userfriendlist1.add(friendusername)
                                                println("userfriends"+userfriendlist1)
                                                userref.child(FirebaseAuth.getInstance().currentUser?.displayName.toString()).child("friendsname").setValue(userfriendlist1)

                                            }
                                        }

                                    userref.child(friendusername)
                                        .get().addOnSuccessListener {
                                            if (it.exists()){
                                                userfriendlist2 = it.child("friendsname").value as ArrayList<String>


                                                userfriendlist2.add(FirebaseAuth.getInstance().currentUser?.displayName.toString())
                                                println("userfriends2"+userfriendlist2)
                                                userref.child(friendusername).child("friendsname").setValue(userfriendlist2)

                                            }
                                        }

                                }

                            }
                        }

                } else {
                    Toast.makeText(this.requireContext(), "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }
            }






        }


    }


    private fun getfriends(friendslist: ArrayList<Friends>) {
        println()
        println("getfriends" + friendslist)
        recyclerAdapter = PersonRecyclerAdapter(friendslist)
        recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerview.adapter = recyclerAdapter
        println("value++++++"+value)
        println("friednsasasdsad" +friendslist)


    }












}