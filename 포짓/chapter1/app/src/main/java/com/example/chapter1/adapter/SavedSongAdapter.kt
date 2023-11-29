package com.example.chapter1.adapter

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chapter1.databinding.PlayListBinding
import com.example.chapter1.model.Song


class SavedSongAdapter : ListAdapter<Song, RecyclerView.ViewHolder>(DiffCallBack) {
    private val checkboxStatus = SparseBooleanArray()
    private lateinit var onSelectClickListener: (Song) -> Unit
    private val selectedItems = mutableListOf<Int>()

    inner class ViewHolder(private val binding: PlayListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(item: Song) {
            if (selectedItems.contains(adapterPosition)) {
                binding.root.setBackgroundColor(Color.parseColor("#eeeeee"))
            } else {
                binding.root.setBackgroundColor(Color.TRANSPARENT)
            }
            Glide.with(binding.songListImg).load(item.coverImgUrl).into(binding.songListImg)
            binding.removeSwitch.isChecked = checkboxStatus[adapterPosition]
            binding.removeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                checkboxStatus.put(adapterPosition, isChecked)
                Log.d("boolean", checkboxStatus.toString())
            }
            binding.songListImg.clipToOutline = true
            binding.songMusicTitle.text = item.title
            binding.songSingerName.text = item.singer
            binding.root.setOnLongClickListener {
                setSelected(it, adapterPosition)
                true
            }
        }
    }

    private fun setSelected(view: View, position: Int) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position)
            view.setBackgroundColor(Color.TRANSPARENT)
            return
        }
        selectedItems.add(position)
        view.setBackgroundColor(Color.parseColor("#eeeeee"))
    }

    fun selectAll() {
        val updatedList = currentList.toMutableList()
        selectedItems.clear()
        for (i in 0 until updatedList.size) {
            selectedItems.add(i)
        }
        submitList(updatedList)
        notifyDataSetChanged()
        Log.d("selected", selectedItems.toString())
    }

    fun unselectAll() {
        val updatedList = currentList.toMutableList()
        selectedItems.clear()
        submitList(updatedList)
        notifyDataSetChanged()
        Log.d("selected", selectedItems.toString())
    }

    fun setSelectListener(select: (Song) -> Unit) {
        onSelectClickListener = select

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

    fun removeSelectedItem() {
        val updatedList = currentList.toMutableList()
        for (i in 0 until selectedItems.size) {
            updatedList.removeAt(selectedItems[i] - i)
        }
        submitList(updatedList)
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Song>() {
            override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {

                return oldItem.title == newItem.title && oldItem.singer == newItem.singer
            }

            override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem == newItem
            }

        }
    }
}