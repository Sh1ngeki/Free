package com.example.jiy.postistvis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jiy.R


class PostRecyclerAdapter(private val posts: List<PostClass>): RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder>()  {

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private  val imageView: ImageView = itemView.findViewById(R.id.Pfp)
        private  val username: TextView = itemView.findViewById(R.id.feedUsername)
        private  val comment: TextView  = itemView.findViewById(R.id.comment)
        private  val description: TextView = itemView.findViewById(R.id.feedDescription)

    }

    override fun onCreateViewHolder( parent: ViewGroup,  viewType: Int ): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_feed_everyone_fragment, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = posts.size

}