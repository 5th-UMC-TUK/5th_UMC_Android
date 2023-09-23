package com.example.chapter1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter1.databinding.AdListBinding

class AdAdapter: ListAdapter<Int, RecyclerView.ViewHolder>(DiffCallBack) {

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {

                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = AdListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderShared(binding)
    }

    inner class ViewHolderShared(private val binding: AdListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int){
            binding.adImg.setImageResource(item)
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