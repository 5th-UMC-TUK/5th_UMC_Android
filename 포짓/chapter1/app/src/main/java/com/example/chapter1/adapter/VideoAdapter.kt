package com.example.chapter1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter1.databinding.VideoListBinding
import com.example.chapter1.model.TodaySongModel

class VideoAdapter : ListAdapter<TodaySongModel, RecyclerView.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = VideoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderShared(binding)
    }

    inner class ViewHolderShared(private val binding: VideoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodaySongModel) {
            binding.videoImg.setImageResource(item.songImgResId)
            binding.videoName.text = item.songName
            binding.singerName.text = item.songSinger
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
        private val DiffCallBack = object : DiffUtil.ItemCallback<TodaySongModel>() {
            override fun areItemsTheSame(
                oldItem: TodaySongModel,
                newItem: TodaySongModel
            ): Boolean {

                return oldItem.songName == newItem.songName && oldItem.songSinger == newItem.songSinger
            }

            override fun areContentsTheSame(
                oldItem: TodaySongModel,
                newItem: TodaySongModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}