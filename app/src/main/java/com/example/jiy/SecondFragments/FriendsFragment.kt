package com.example.jiy.SecondFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jiy.Friends
import com.example.jiy.PersonRecyclerAdapter
import com.example.jiy.R

class FriendsFragment:Fragment(R.layout.add_friends_fragment) {
    private lateinit var recyclerAdapter: PersonRecyclerAdapter
    private lateinit var recyclerview: RecyclerView
    private var friendslist = arrayListOf<Friends>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview = view.findViewById(R.id.recycle)
        friendslist.add(Friends("nika","nikol","1234","",))

        recyclerAdapter = PersonRecyclerAdapter(friendslist)
        recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerview.adapter = recyclerAdapter



    }

}