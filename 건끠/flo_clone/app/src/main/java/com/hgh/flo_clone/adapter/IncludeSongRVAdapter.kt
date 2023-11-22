package com.hgh.flo_clone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hgh.flo_clone.databinding.ItemIncludeSongBinding
import com.hgh.flo_clone.server.response.albumin.Result

class IncludeSongRVAdapter() :
    RecyclerView.Adapter<IncludeSongRVAdapter.VideoItemHolder>() {

    private var musicList: List<Result> = listOf()

    inner class VideoItemHolder(
        private val binding: ItemIncludeSongBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Result, position: Int) {
            binding.includeNumT.text = String.format("%02d",position+1)
            binding.includeTitleT.text = data.title
            binding.includeSingerT.text = data.singer
            if (data.isTitleSong == "T") {
                binding.includeTitleImage.isVisible =true
            } else {
                binding.includeTitleImage.isGone = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemHolder =
        VideoItemHolder(
            ItemIncludeSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = musicList.size

    override fun onBindViewHolder(holder: VideoItemHolder, position: Int) {
        holder.bind(musicList[position], position)
    }

    fun setList(list: List<Result>) {
        musicList = list
        notifyDataSetChanged()
    }
}