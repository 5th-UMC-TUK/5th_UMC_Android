package com.example.chapter1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter1.databinding.SongListBinding
import com.example.chapter1.model.SongModel

class SongAdapter : ListAdapter<SongModel, RecyclerView.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = SongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderShared(binding)
    }

    inner class ViewHolderShared(private val binding: SongListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SongModel) {
            binding.imgSong.setImageResource(item.songImgResId)
            binding.songTitle.text = item.songName
            binding.songSinger.text = item.singerName
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
        private val DiffCallBack = object : DiffUtil.ItemCallback<SongModel>() {
            override fun areItemsTheSame(oldItem: SongModel, newItem: SongModel): Boolean {

                return oldItem.songName == newItem.songName && oldItem.singerName == newItem.singerName
            }

            override fun areContentsTheSame(oldItem: SongModel, newItem: SongModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}