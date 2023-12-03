package com.lacuna.chapter1.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lacuna.chapter1.data.Album
import com.lacuna.chapter1.data.ResultAlbum
import com.lacuna.chapter1.data.TodayMusic
import com.lacuna.chapter1.databinding.ItemLookChartBinding
import com.lacuna.chapter1.databinding.ItemTodayMusicBinding

class AlbumRVAdapter(val context: Context, val result : ResultAlbum) : RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {
    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun onPlayAlbum(album: Album)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemTodayMusicBinding = ItemTodayMusicBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        //holder.bind(result.songs[position])

        if(result.albums[position].coverImgUrl == "" || result.albums[position].coverImgUrl == null){

        } else {
            Log.d("image",result.albums[position].coverImgUrl )
            Glide.with(context).load(result.albums[position].coverImgUrl).into(holder.coverImg)
        }
        holder.title.text = result.albums[position].title
        holder.singer.text = result.albums[position].singer
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(result.albums[position])
        }
        holder.binding.itemTodayMusicPlayIv.setOnClickListener {
            mItemClickListener.onPlayAlbum(result.albums[position])
        }
    }

    override fun getItemCount(): Int = result.albums.size


    inner class ViewHolder(val binding: ItemTodayMusicBinding) : RecyclerView.ViewHolder(binding.root){
        val coverImg : ImageView = binding.itemTodayMusicAlbumIv
        val title : TextView = binding.itemTodayMusicTitleTv
        val singer : TextView = binding.itemTodayMusicSingerTv
    }

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }
}