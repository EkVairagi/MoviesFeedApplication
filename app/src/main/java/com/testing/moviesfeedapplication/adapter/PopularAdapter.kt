package com.testing.moviesfeedapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.testing.moviesfeedapplication.databinding.PopularItemBinding
import com.testing.moviesfeedapplication.model.Result

class PopularAdapter(private val dataItem: List<Result>, private val click: (String) -> Unit) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PopularItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataItem[position])
    }
    override fun getItemCount() = dataItem.size

    inner class ViewHolder(private val itemBinding: PopularItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(result: Result) {
            itemBinding.apply {
                tvName.text = result.original_title
                Glide.with(itemView.context).load(
                    "https://media.themoviedb.org/t/p/w220_and_h330_face/" + result.poster_path
                ).into(itemBinding.ivImage)
                clCard.setOnClickListener {
                    click(result.id.toString())
                }
            }
        }
    }
}

