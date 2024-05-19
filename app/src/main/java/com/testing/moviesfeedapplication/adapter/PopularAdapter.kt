package com.testing.moviesfeedapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.testing.moviesfeedapplication.R
import com.testing.moviesfeedapplication.databinding.PopularItemBinding
import com.testing.moviesfeedapplication.model.Result
import com.testing.moviesfeedapplication.interfaces.OnClickEvent

class PopularAdapter(private val dataItem: List<Result>, val click: OnClickEvent) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PopularItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataItem[position], position)
    }
    override fun getItemCount() = dataItem.size

    inner class ViewHolder(private val itemBinding: PopularItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(result: Result, position: Int) {
            itemBinding.apply {
                name.text = result.original_title
                Glide.with(itemView.context).load(
                    "https://media.themoviedb.org/t/p/w220_and_h330_face/" + result.poster_path
                ).into(itemBinding.Image)
                clCard.setOnClickListener {
                    click.click("" + result.id)
                }
            }
        }
    }
}

