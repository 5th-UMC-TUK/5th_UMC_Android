package com.hgh.flo_clone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.hgh.flo_clone.R
import com.hgh.flo_clone.databinding.ItemChartSongBinding
import com.hgh.flo_clone.server.response.song.Song

class LookingSongRVAdapter() :
    RecyclerView.Adapter<LookingSongRVAdapter.LookingItemHolder>() {

    private var chartList : List<Song> = listOf()

    inner class LookingItemHolder(
        private val binding: ItemChartSongBinding
    ): ViewHolder(binding.root){
        fun bind(data: Song){
            binding.includeNumT.text = (adapterPosition+1).toString()
            binding.includeTitleT.text = data.title
            binding.includeSingerT.text = data.singer
            Glide.with(itemView)
                .load(data.coverImgUrl)
                .error(R.drawable.appicon_image)
                .fallback(R.drawable.appicon_image)
                .into(binding.sumNailImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookingItemHolder =
        LookingItemHolder(
            ItemChartSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = chartList.size

    override fun onBindViewHolder(holder: LookingItemHolder, position: Int) {
        holder.bind(chartList[position])
    }

    fun setList(list: List<Song>) {
        chartList = list
        notifyDataSetChanged()
    }
}