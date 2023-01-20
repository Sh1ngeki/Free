package com.example.jiy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class PersonRecyclerAdapter(private val persons: List<Friends>): RecyclerView.Adapter<PersonRecyclerAdapter.PersonViewHolder>() {
    inner class PersonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val textView: TextView = itemView.findViewById(R.id.saxeli)
        private val textView2: TextView = itemView.findViewById(R.id.gmail)
        private val number: TextView = itemView.findViewById(R.id.number)

        fun setData(person: Friends) {

            Glide.with(itemView).load(person.friendimg).into(imageView)

            textView.text = person.friendname
            number.text = person.friendmail


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.setData(persons[position])

    }

    override fun getItemCount() = persons.size
}