package com.example.chapter1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter1.R
import com.example.chapter1.model.TodaySongModel
import com.example.chapter1.databinding.TodaysongListBinding

class TodaySongAdapter:  ListAdapter<TodaySongModel, RecyclerView.ViewHolder>(DiffCallBack) {
    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<TodaySongModel>() {
            override fun areItemsTheSame(oldItem: TodaySongModel, newItem: TodaySongModel): Boolean {

                return oldItem.songName == newItem.songName && oldItem.songSinger == newItem.songSinger
            }

            override fun areContentsTheSame(oldItem: TodaySongModel, newItem: TodaySongModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = TodaysongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderShared(binding)
    }

    inner class ViewHolderShared(private val binding: TodaysongListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodaySongModel){
            binding.todaySongImg.setImageResource(item.songImgResId)
            binding.songName.text = item.songName
            binding.singerName.text = item.songSinger
        }
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    public override fun getItem(position: Int): TodaySongModel {
        return super.getItem(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.findViewById<ImageView>(R.id.today_song_img).setOnClickListener {
            listener?.onItemClick(position)
        }
        when (holder) {
            is ViewHolderShared -> {
                holder.bind(item)
            }
        }
    }
}

interface OnItemClickListener {
    fun onItemClick(position: Int)
}