package com.example.chapter1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter1.databinding.TitleListBinding

class TitleAdapter : ListAdapter<TitleModel, RecyclerView.ViewHolder>(DiffCallBack) {

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<TitleModel>() {
            override fun areItemsTheSame(oldItem: TitleModel, newItem: TitleModel): Boolean {

                return oldItem.titleText == newItem.titleText && oldItem.albumInfo == newItem.albumInfo
            }

            override fun areContentsTheSame(oldItem: TitleModel, newItem: TitleModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = TitleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderShared(binding)
    }

    inner class ViewHolderShared(private val binding: TitleListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TitleModel){
            binding.titleFrame.setBackgroundResource(item.titleImgResId)
            binding.albumInfo.text = item.albumInfo
            binding.titleText.text = item.titleText
            val adapter = SongAdapter()
            binding.albumList.adapter = adapter
            adapter.submitList(item.songs)
        }


    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ViewHolderShared -> {
                holder.bind(item)
            }
        }
    }
}