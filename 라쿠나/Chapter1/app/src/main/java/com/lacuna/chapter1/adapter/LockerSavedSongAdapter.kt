package com.lacuna.chapter1.adapter

import android.annotation.SuppressLint
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
//import com.lacuna.chapter1.data.SavedSong
import androidx.recyclerview.widget.RecyclerView
import com.lacuna.chapter1.data.Song
import com.lacuna.chapter1.databinding.ItemLockerSavedSongBinding

class LockerSavedSongAdapter(): RecyclerView.Adapter<LockerSavedSongAdapter.SavedSongViewHolder>() {
    private val switchStatus = SparseBooleanArray()
    private val songs = ArrayList<Song>()

    interface OnItemClickListener {
        fun onItemClick(song: Song)
        fun onRemoveSavedSong(songId: Int)
    }

    // 리스너 객체를 전달받는 함수랑 리스너 객체를 저장할 변수
    private lateinit var itemClickListener: OnItemClickListener
    fun setMyItemClickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }

//    fun removeItem(position: Int){
//        savedSongList.removeAt(position)
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LockerSavedSongAdapter.SavedSongViewHolder {
        val binding =  ItemLockerSavedSongBinding.inflate(
            LayoutInflater.from(parent.context), // layoutInflater 를 넘기기위해 함수 사용, ViewGroup 는 View 를 상속하고 View 는 이미 Context 를 가지고 있음
            parent, // 부모(리싸이클러뷰 = 뷰그룹)
            false   // 리싸이클러뷰가 attach 하도록 해야함 (우리가 하면 안됨)
        )
        return SavedSongViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LockerSavedSongAdapter.SavedSongViewHolder,
        position: Int
    ) {
        holder.bind(songs[position])
        holder.binding.itemLockerAlbumMoreIv.setOnClickListener {
            itemClickListener.onRemoveSavedSong(songs[position].id)
            removeSong(position)
        }

//        holder.binding.itemLockerAlbumMoreIv.setOnClickListener { itemClickListener.onRemoveSavedSong(position) }

        val switch = holder.binding.itemLockerAlbumSwitch
        switch.isChecked = switchStatus[position]
        switch.setOnClickListener {
            if (switch.isChecked) {
                switchStatus.put(position, true)
            }
            else {
                switchStatus.put(position, false)
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return songs.size
    }
    @SuppressLint("NotifyDataSetChanged") // 경고 무시 어노테이션
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }
    inner class SavedSongViewHolder(val binding: ItemLockerSavedSongBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(song: Song) {
            binding.itemLockerAlbumCoverImgIv.setImageResource(song.coverImg!!)
            binding.itemLockerAlbumTitleTv.text = song.title.toString()
            binding.itemLockerAlbumSingerTv.text = song.singer.toString()
        }
    }


}