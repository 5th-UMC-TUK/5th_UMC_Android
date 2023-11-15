package com.example.chapter1.adapter

import android.os.Build
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chapter1.databinding.PlayListBinding
import com.example.chapter1.db.Song


class SavedSongAdapter : ListAdapter<Song, RecyclerView.ViewHolder>(DiffCallBack) {
    private val checkboxStatus = SparseBooleanArray()

    inner class ViewHolder(private val binding: PlayListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(item: Song) {
            binding.songListImg.setImageResource(item.coverImg!!)
            binding.removeSwitch.isChecked = checkboxStatus[adapterPosition]
            binding.removeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                checkboxStatus.put(adapterPosition, isChecked)
                Log.d("boolean", checkboxStatus.toString())
            }
            binding.songListImg.clipToOutline = true
            binding.songMusicTitle.text = item.music
            binding.songSingerName.text = item.singer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSongAdapter.ViewHolder {
        val binding = PlayListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ViewHolder -> {
                holder.bind(item)
            }
        }
    }

    fun setCount(size: Int) {
        for (i in 0 until size) {
            checkboxStatus.put(i, false)
            Log.d("boolean", checkboxStatus.toString())
        }
    }

    override fun getItemViewType(position: Int): Int = position

    @RequiresApi(Build.VERSION_CODES.P)
    private fun removeCurrentItem(position: Int) {
        val updatedList = currentList.toMutableList()
        checkboxStatus.delete(position)
        for (i in position until checkboxStatus.size()) {
            val key = checkboxStatus.keyAt(i)
            checkboxStatus.put(key - 1, checkboxStatus.valueAt(i))
            checkboxStatus.delete(key)
        }
        Log.d("boolean", checkboxStatus.toString())
        updatedList.removeAt(position)
        submitList(updatedList)
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Song>() {
            override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {

                return oldItem.music == newItem.music && oldItem.singer == newItem.singer
            }

            override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem == newItem
            }

        }
    }
}