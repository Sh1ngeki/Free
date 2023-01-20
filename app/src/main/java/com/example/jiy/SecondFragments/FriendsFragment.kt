package com.example.jiy.SecondFragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jiy.Friends
import com.example.jiy.PersonRecyclerAdapter
import com.example.jiy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FriendsFragment:Fragment(R.layout.add_friends_fragment) {
    private lateinit var recyclerAdapter: PersonRecyclerAdapter
    private lateinit var recyclerview: RecyclerView
    private var friendslist = arrayListOf<Friends>()
    private var myfriends = arrayListOf<String>()
    private var database = FirebaseDatabase.getInstance()
    private lateinit var storagereference: StorageReference
    private lateinit var storage1: StorageReference
    private lateinit var frnd1:ArrayList<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview = view.findViewById(R.id.recycle)
        friendslist.add(Friends("nika","nikol","1234","",))
        storagereference = FirebaseStorage.getInstance().getReference("users")
        storage1 = FirebaseStorage.getInstance().getReference("default")

        var userref = database.getReference("users")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                friendslist.clear()
                println(FirebaseAuth.getInstance().uid)
                userref.child(FirebaseAuth.getInstance().currentUser?.displayName.toString()).get()
                    .addOnSuccessListener {

                        if (it.exists()) {
                            println("uid")

                            frnd1 = it.child("friendsname").value as ArrayList<String>
                            println("frnd"+frnd1)
                            var n = 0
                            for (i in frnd1) {
                                n += 1
                                userref.child(i.trim()).get().addOnSuccessListener {
                                    if (it.exists() &&! i.trim().isEmpty()) {
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
                                                                it.child("gmail").value.toString(),)
                                                            friendslist.add(friend)
                                                            if (n == frnd1.size) {
                                                                println("done")
                                                                println(friendslist)
                                                                getfriends(friendslist)
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
                                                        friendslist.add(friend)
                                                        if (n == frnd1.size) {
                                                            println("done")
                                                            println(friendslist)
                                                            getfriends(friendslist)
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


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
                Log.w("ValueEventListener", "loadPost:onCancelled", databaseError.toException())
            }
        }
        userref.addValueEventListener(postListener)



    }

    private fun getfriends(friendslist: ArrayList<Friends>) {
        println()
        println("getfriends" + friendslist)
        recyclerAdapter = PersonRecyclerAdapter(friendslist)
        recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerview.adapter = recyclerAdapter

    }










}