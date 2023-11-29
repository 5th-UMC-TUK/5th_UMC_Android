package com.example.chapter1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chapter1.databinding.SongListBinding
import com.example.chapter1.model.Song

class SongAdapter : ListAdapter<Song, RecyclerView.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = SongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderShared(binding)
    }

    inner class ViewHolderShared(private val binding: SongListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Song) {
            Glide.with(binding.imgSong).load(item.coverImgUrl).into(binding.imgSong)
            binding.songTitle.text = item.title
            binding.songSinger.text = item.singer
        }


    }

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ViewHolderShared -> {
                holder.bind(item)
            }
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Song>() {
            override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {

                return oldItem.title == newItem.title && oldItem.singer == newItem.singer
            }

            override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem == newItem
            }

        }
    }
}