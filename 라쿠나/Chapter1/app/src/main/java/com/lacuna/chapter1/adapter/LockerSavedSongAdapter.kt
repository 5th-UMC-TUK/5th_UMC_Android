package com.lacuna.chapter1.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lacuna.chapter1.data.SavedSong
import com.lacuna.chapter1.databinding.ItemLockerSavdSongBinding
import com.lacuna.floclone.adapter.TodayMusicAdapter

class LockerSavedSongAdapter(private val savedSongList: ArrayList<SavedSong>): RecyclerView.Adapter<LockerSavedSongAdapter.SavedSongViewHolder>() {
    private val switchStatus = SparseBooleanArray()

    interface OnItemClickListener {
        fun onItemClick(savedSong: SavedSong)
        fun onRemoveSavedSong(position: Int)
    }

    // 리스너 객체를 전달받는 함수랑 리스너 객체를 저장할 변수
    private lateinit var itemClickListener: OnItemClickListener
    fun setMyItemClickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }

    fun removeItem(position: Int){
        savedSongList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LockerSavedSongAdapter.SavedSongViewHolder {
        val binding =  ItemLockerSavdSongBinding.inflate(
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
        holder.bind(savedSongList[position])
        holder.binding.itemLockerAlbumMoreIv.setOnClickListener { itemClickListener.onRemoveSavedSong(position) }

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
        return savedSongList.size
    }

    inner class SavedSongViewHolder(val binding: ItemLockerSavdSongBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(savedSong: SavedSong) {
            binding.itemLockerAlbumCoverImgIv.setImageResource(savedSong.imgRes)
            binding.itemLockerAlbumTitleTv.text = savedSong.title.toString()
            binding.itemLockerAlbumSingerTv.text = savedSong.singer.toString()
        }
    }
}