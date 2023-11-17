package com.example.chapter1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter1.databinding.TodaysongListBinding
import com.example.chapter1.db.Album

class TodaySongAdapter : ListAdapter<Album, RecyclerView.ViewHolder>(DiffCallBack) {
    private lateinit var onImageClickListener: (Album) -> Unit
    private lateinit var onBtnClickListener: (Album) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            TodaysongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderShared(binding)
    }

    inner class ViewHolderShared(private val binding: TodaysongListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Album) {
            binding.todaySongImg.setImageResource(item.coverImg!!)
            binding.songName.text = item.title
            binding.singerName.text = item.singer
            binding.todaySongImg.setOnClickListener {
                onImageClickListener(item)
            }
            binding.playTodaySongBtn.setOnClickListener {
                onBtnClickListener(item)
            }
        }
    }

    fun setClickListener(img: (Album) -> Unit, btn: (Album) -> Unit) {
        onImageClickListener = img
        onBtnClickListener = btn
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    public override fun getItem(position: Int): Album = super.getItem(position)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ViewHolderShared -> {
                holder.bind(item)
            }
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(
                oldItem: Album,
                newItem: Album
            ): Boolean {

                return oldItem.title == newItem.title && oldItem.singer == newItem.singer
            }

            override fun areContentsTheSame(
                oldItem: Album,
                newItem: Album
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}