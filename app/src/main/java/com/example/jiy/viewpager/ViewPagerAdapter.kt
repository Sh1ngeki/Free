package com.example.jiy.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.jiy.Loading.FirstPage
import com.example.jiy.Loading.SecondPage

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstPage()
            1 -> SecondPage()
            else -> FirstPage()
        }
    }

    fun getPageTitle(position: Int): String {
        return when (position) {
            0 -> "Fragment 1"
            1 -> "Fragment 2"
            else -> "Fragment 1"
        }
    }
}
