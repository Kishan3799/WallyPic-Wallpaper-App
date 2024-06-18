package com.kishan.wallypic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.kishan.wallypic.R
import com.kishan.wallypic.databinding.ItemRecyclerviewBinding
import com.kishan.wallypic.model.Photo
import com.kishan.wallypic.utils.WallInteractionListener


class RecyclerViewAdapter(private val listener:WallInteractionListener) : PagingDataAdapter<Photo, RecyclerViewAdapter.MyViewHolder>(DiffUtilCallBack()) {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = ItemRecyclerviewBinding.bind(view)



        fun bind(photo:Photo){
            Glide.with(itemView.context)
                .asBitmap()
                .load(photo.src.portrait)
                .centerCrop()
                .transition(BitmapTransitionOptions.withCrossFade(80))
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.imageView)

            itemView.setOnClickListener {
                listener.onItemClick(photo, it )
            }

        }


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)

        return MyViewHolder(inflater)
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Photo>(){
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

    }
}