package com.example.jiy.SecondFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.jiy.Loading.ViewPagerFragmentAdapter
import com.example.jiy.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FeedFragment:Fragment(R.layout.feed_fragment) {
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPagerFragmentAdapter: ViewPagerFragmentAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.feed_fragment, container, false)
        viewPager2 = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPagerFragmentAdapter = ViewPagerFragmentAdapter(this.requireContext() as AppCompatActivity)
        TabLayoutMediator(tabLayout, viewPager2){
                tab,position ->
            when(position){
                0 -> tab.text = "Friends"
                1 -> tab.text = "Discovery"
            }
        }
        return view
    }
}