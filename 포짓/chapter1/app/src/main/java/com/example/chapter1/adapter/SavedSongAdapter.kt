package com.example.chapter1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter1.databinding.PlayListBinding
import com.example.chapter1.model.SongModel


class SavedSongAdapter(private val songList: List<SongModel>): RecyclerView.Adapter<SavedSongAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: PlayListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SongModel) {
            binding.songListImg.setImageResource(item.songImgResId)
            binding.songListImg.clipToOutline = true
            binding.songMusicTitle.text = item.songName
            binding.songSingerName.text = item.singerName
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSongAdapter.ViewHolder {
        val binding = PlayListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedSongAdapter.ViewHolder, position: Int) {
        holder.bind(songList[position])
    }

    override fun getItemCount() = songList.size

}