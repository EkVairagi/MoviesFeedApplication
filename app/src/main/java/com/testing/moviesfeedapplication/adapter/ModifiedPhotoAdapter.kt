package com.testing.moviesfeedapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.testing.moviesfeedapplication.databinding.PopularItemBinding
import com.testing.moviesfeedapplication.model.Result

class ModifiedPhotoAdapter(val onItemSelected: (result: Result, position: Int) -> Unit) :
    RecyclerView.Adapter<ModifiedPhotoAdapter.ViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position], position)
    }

    override fun getItemCount(): Int = differ.currentList.size
    inner class ViewHolder(private val itemBinding: PopularItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(result: Result, position: Int) {
            itemBinding.apply {
                name.text = result.original_title
                Glide.with(itemView.context).load(
                    "https://media.themoviedb.org/t/p/w220_and_h330_face/" + result.poster_path
                ).into(itemBinding.Image)
                clCard.setOnClickListener {
                   // click.click("" + result.id)

                    onItemSelected(result, position)

                }
            }
        }
    }
}