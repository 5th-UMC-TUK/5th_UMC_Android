package com.hgh.flo_clone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.ItemMusicVideoBinding

class VideoRVAdapter() :
    RecyclerView.Adapter<VideoRVAdapter.VideoItemHolder>() {

    private var musicList: List<MusicModel> = listOf()

    inner class VideoItemHolder(
        private val binding: ItemMusicVideoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MusicModel) {
            binding.itemVideoTitle.text = data.title
            binding.itemVideoSinger.text = data.singer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemHolder =
        VideoItemHolder(
            ItemMusicVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = musicList.size

    override fun onBindViewHolder(holder: VideoItemHolder, position: Int) {
        holder.bind(musicList[position])
    }

    fun setList(list: List<MusicModel>) {
        musicList = list
        notifyDataSetChanged()
    }
}