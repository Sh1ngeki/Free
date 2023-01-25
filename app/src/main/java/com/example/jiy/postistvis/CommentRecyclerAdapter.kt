package com.example.jiy.postistvis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jiy.R


class CommentRecyclerAdapter(private val comments: List<CommentText>): RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder>()  {
    inner class CommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.addComment)

        fun setData(comment: CommentText){

            textView.text = comment.commentar
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.setData(comments[position])
    }

    override fun getItemCount() = comments.size
}