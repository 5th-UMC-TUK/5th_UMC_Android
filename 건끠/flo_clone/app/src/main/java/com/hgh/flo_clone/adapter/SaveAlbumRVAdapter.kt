package com.hgh.flo_clone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.hgh.flo_clone.R
import com.hgh.flo_clone.databinding.ItemAlbumLikeBinding
import com.hgh.flo_clone.databinding.ItemChartSongBinding
import com.hgh.flo_clone.server.response.album.Album
import com.hgh.flo_clone.server.response.song.Song

class SaveAlbumRVAdapter() :
    RecyclerView.Adapter<SaveAlbumRVAdapter.AlbumItemHolder>() {

    private var albumList : List<Album> = listOf()

    inner class AlbumItemHolder(
        private val binding: ItemAlbumLikeBinding
    ): ViewHolder(binding.root){
        fun bind(data: Album){
            binding.includeTitleT.text = data.title
            binding.includeSingerT.text = data.singer
            Glide.with(itemView)
                .load(data.coverImgUrl)
                .error(R.drawable.appicon_image)
                .fallback(R.drawable.appicon_image)
                .into(binding.sumNailImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemHolder =
        AlbumItemHolder(
            ItemAlbumLikeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = albumList.size

    override fun onBindViewHolder(holder: AlbumItemHolder, position: Int) {
        holder.bind(albumList[position])
    }

    fun setList(list: List<Album>) {
        albumList = list
        notifyDataSetChanged()
    }
}