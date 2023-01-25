package com.example.jiy.viewpagerpages

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jiy.FirstFragments.LoginFragment
import com.example.jiy.R
import com.example.jiy.postistvis.PostClass
import com.example.jiy.postistvis.PostRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirstPage:Fragment(R.layout.friends_page) {
    private var value = arrayListOf<PostClass>()
    private lateinit var postlist: ArrayList<PostClass>
    private lateinit var recyclerAdapter: PostRecyclerAdapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var userref: DatabaseReference
    private lateinit var storagereference: StorageReference
    private lateinit var storage1: StorageReference
    private  var postclassarray1= ArrayList<PostClass>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.friends_page,container,false)
        println("aeebdebdhebdheb ${LoginFragment.MySingleton.friendpost}")
        recyclerview = view.findViewById(R.id.recycle2)
        if (friendpost.posts1!=null &&!friendpost.posts1?.isEmpty()!!){
            postclassarray1 = friendpost.posts1!!

        }else
        if (LoginFragment.MySingleton.friendpost!=null) {
            value = LoginFragment.MySingleton.friendpost!!
            println("ashahjsajshjahsjahsaj")
            println(value)
            println("ashahjsajshjahsjahsaj")
            postclassarray1 = value

        }

        recyclerAdapter = PostRecyclerAdapter(postclassarray1)
        recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerview.adapter = recyclerAdapter
        val window = requireActivity().window

        userref = FirebaseDatabase.getInstance().getReference("users")
        refreshLayout =view.findViewById(R.id.refresh2)
        storage1= FirebaseStorage.getInstance().getReference("default")
        storagereference= FirebaseStorage.getInstance().getReference("users")
        refreshLayout.setOnRefreshListener {
            postclassarray1.clear()

            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

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
                                                                        LoginFragment.MySingleton.friendpost =
                                                                            postclassarray1
                                                                        hehe(postclassarray1)

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
                                                                    LoginFragment.MySingleton.friendpost =
                                                                        postclassarray1
                                                                    hehe(postclassarray1)

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



            Handler().postDelayed(Runnable {
                refreshLayout.isRefreshing =false
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            },2000)






        }






        println("heeeeeheeee")
        return view
    }

    private fun hehe(arrayList: ArrayList<PostClass>){

        recyclerAdapter = PostRecyclerAdapter(postclassarray1)
        recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerview.adapter = recyclerAdapter
        friendpost.posts1=postclassarray1
    }
    object friendpost{
        var posts1 :ArrayList<PostClass>?=null
    }
}