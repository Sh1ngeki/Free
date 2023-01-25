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
import com.example.jiy.PersonRecyclerAdapter
import com.example.jiy.R
import com.example.jiy.SecondFragments.FriendsFragment
import com.example.jiy.postistvis.PostClass
import com.example.jiy.postistvis.PostRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.reflect.Field

class SecondPage:Fragment(R.layout.discovery_page) {

    private var value = arrayListOf<PostClass>()
    private lateinit var postlist: ArrayList<PostClass>
    private lateinit var recyclerAdapter: PostRecyclerAdapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var userref:DatabaseReference
    private lateinit var storagereference:StorageReference
    private lateinit var storage1:StorageReference
    private  var postclassarray= ArrayList<PostClass>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.discovery_page,container,false)
        println("aeebdebdhebdheb ${LoginFragment.MySingleton.postdata}")
        recyclerview = view.findViewById(R.id.recycle1)
        if (postdata.posts!=null){
            postclassarray = postdata.posts!!

        }
        if (LoginFragment.MySingleton.postdata!=null) {
            value = LoginFragment.MySingleton.postdata!!
            println("ashahjsajshjahsjahsaj")
            println(value)
            println("ashahjsajshjahsjahsaj")
            postclassarray = value

        }

        recyclerAdapter = PostRecyclerAdapter(postclassarray)
        recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerview.adapter = recyclerAdapter


        userref = FirebaseDatabase.getInstance().getReference("users")
        refreshLayout =view.findViewById(R.id.refresh1)
        storage1=FirebaseStorage.getInstance().getReference("default")
        storagereference=FirebaseStorage.getInstance().getReference("users")
        refreshLayout.setOnRefreshListener {
            postclassarray.clear()
            val window = requireActivity().window
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            userref.child("everyone").get().addOnSuccessListener {
                if (it.exists()){
                    val poster = it.value as ArrayList<String>
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
                                                                LoginFragment.MySingleton.postdata=postclassarray
                                                                hehe(postclassarray)
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
                                                            LoginFragment.MySingleton.postdata =
                                                                postclassarray
                                                            hehe(postclassarray)


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

        recyclerAdapter = PostRecyclerAdapter(postclassarray)
        recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerview.adapter = recyclerAdapter
        postdata.posts=postclassarray
    }
    object postdata{
        var posts :ArrayList<PostClass>?=null
    }

}