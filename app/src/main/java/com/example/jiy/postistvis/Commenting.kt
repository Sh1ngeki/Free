package com.example.jiy.postistvis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.jiy.R
import com.example.jiy.viewpagerpages.SecondPage

class Commenting:Fragment(R.layout.comment_layout) {


    private lateinit var commentText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.comment_layout,container,false)
        println("haimeeeee")
//        commentText=view.findViewById(R.id.comment)
//        println(SecondPage.commentdata.commentText)
//        commentText.setText(SecondPage.commentdata.commentText)




        return view
    }
}