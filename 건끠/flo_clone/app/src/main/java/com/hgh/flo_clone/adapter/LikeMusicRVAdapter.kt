package com.hgh.flo_clone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hgh.flo_clone.R
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.ItemSaveSongBinding
import com.hgh.flo_clone.databinding.ItemSongLikeBinding

class LikeMusicRVAdapter(
) : ListAdapter<MusicModel, LikeMusicRVAdapter.SaveMusicItemHolder>(DiffUtil) {
    private var musicList: MutableList<MusicModel> = mutableListOf()

    var selectionList = mutableListOf<MusicModel>()
    var onItemSelectionChangedListener: ((MutableList<MusicModel>) -> Unit)? = null

    inner class SaveMusicItemHolder(
         val binding: ItemSongLikeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MusicModel) {
            binding.root.isActivated = selectionList.contains(data)
            binding.includeTitleT.text = data.title
            binding.includeSingerT.text = data.singer

            //item 선택하기
            binding.root.setOnClickListener { v ->
                val id = v?.tag
                if (selectionList.contains(id)){
                    selectionList.remove(id)
                    binding.root.isActivated =false
                }
                else {
                    selectionList.add(id as MusicModel)
                    binding.root.isActivated =true
                }
                notifyDataSetChanged()
                onItemSelectionChangedListener?.let { it(selectionList) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LikeMusicRVAdapter.SaveMusicItemHolder {

//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_song_like, parent, false)
//        view.setOnClickListener { v ->
//            val id = v?.tag
//            if (selectionList.contains(id)) selectionList.remove(id)
//            else selectionList.add(id as Long)
//            notifyDataSetChanged()
//            onItemSelectionChangedListener?.let { it(selectionList) }
//        }

        return SaveMusicItemHolder(
            ItemSongLikeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: LikeMusicRVAdapter.SaveMusicItemHolder, position: Int) {
        holder.bind(musicList[position])
        //item 선택하기 조건
        holder.binding.root.tag = musicList[position].copy()
    }

    override fun getItemCount() = musicList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setList(list: MutableList<MusicModel>) {
        musicList = list
        notifyDataSetChanged()
    }


    companion object {
        private val DiffUtil = object : DiffUtil.ItemCallback<MusicModel>() {
            override fun areItemsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem.title == newItem.title

            }

            override fun areContentsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}