package com.example.jiy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jiy.FirstFragments.LoginFragment
import com.example.jiy.SecondFragments.FeedFragment
import com.example.jiy.SecondFragments.FriendsFragment
import com.example.jiy.SecondFragments.PostFragment
import com.example.jiy.SecondFragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class FullNavFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fullnavfragment, container, false)

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.fragments_bottom)



        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.feedFragment23 -> {
                    // Replace the current fragment with Fragment1
                    val fragment1 = FeedFragment()
                    val transaction = fragmentManager?.beginTransaction()
                    transaction?.replace(R.id.nav_host_fragment, fragment1)
                    transaction?.commit()
                    true
                }
                R.id.friendsFragment23 -> {
                    // Replace the current fragment with Fragment1
                    val fragment1 = FriendsFragment()
                    val transaction = fragmentManager?.beginTransaction()
                    transaction?.replace(R.id.nav_host_fragment, fragment1)
                    transaction?.commit()
                    true
                }
                R.id.profileFragment23 -> {
                    // Replace the current fragment with Fragment1
                    val fragment1 = ProfileFragment()
                    val transaction = fragmentManager?.beginTransaction()
                    transaction?.replace(R.id.nav_host_fragment, fragment1)
                    transaction?.commit()
                    true
                }

                R.id.postFragment23-> {
                    // Replace the current fragment with Fragment2
                    val fragment2 = PostFragment()
                    val transaction = fragmentManager?.beginTransaction()
                    transaction?.replace(R.id.nav_host_fragment, fragment2)
                    transaction?.commit()
                    true
                }
                else -> false
            }
        }
        return view
    }
}
