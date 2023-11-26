package com.lacuna.chapter1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lacuna.chapter1.data.TodayMusic
import com.lacuna.chapter1.databinding.ItemLockerSavedAlbumBinding

class LockerSavedAlbumAdapter() : RecyclerView.Adapter<LockerSavedAlbumAdapter.ViewHolder>() {
    private val albums = ArrayList<TodayMusic>()

    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LockerSavedAlbumAdapter.ViewHolder {
        val binding: ItemLockerSavedAlbumBinding = ItemLockerSavedAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerSavedAlbumAdapter.ViewHolder, position: Int) {
        holder.bind(albums[position])
        holder.binding.itemAlbumMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(albums[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = albums.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAlbums(albums: ArrayList<TodayMusic>) {
        this.albums.clear()
        this.albums.addAll(albums)

        notifyDataSetChanged()
    }

    fun removeSong(position: Int){
        albums.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLockerSavedAlbumBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(album: TodayMusic){
            binding.itemAlbumImgIv.setImageResource(album.coverImg!!)
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
        }
    }
}