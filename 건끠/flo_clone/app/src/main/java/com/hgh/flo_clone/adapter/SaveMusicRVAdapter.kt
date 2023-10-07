package com.hgh.flo_clone.adapter

import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hgh.flo_clone.R
import com.hgh.flo_clone.data.AlbumModel
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.ItemMusicVideoBinding
import com.hgh.flo_clone.databinding.ItemSaveSongBinding
import java.util.Collections

class SaveMusicRVAdapter(
) : ListAdapter<MusicModel,SaveMusicRVAdapter.SaveMusicItemHolder>(DiffUtil) {
    private var musicList: MutableList<MusicModel> = mutableListOf()

    lateinit var deleteClickListener: (MusicModel) -> Unit

    //방법 1
    private val checkStatus = SparseBooleanArray()


    inner class SaveMusicItemHolder(
        private val binding: ItemSaveSongBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MusicModel) {
            binding.includeTitleT.text = data.title
            binding.includeSingerT.text = data.singer
            binding.includeMoreBtn.setOnClickListener {
                deleteClickListener(data)
            }

            //방법2
            binding.includePlayBtn.setImageResource(if (data.isTitle) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
            binding.includePlayBtn.setOnClickListener {
                val currentPosition = adapterPosition
                val currentHeartStatus = musicList[currentPosition].isTitle

                musicList[currentPosition].isTitle = !currentHeartStatus

                binding.includePlayBtn.setImageResource(if (currentHeartStatus) R.drawable.baseline_favorite_border_24 else R.drawable.baseline_favorite_24)
            }

            //방법1
//            binding.saveSwitch.isChecked = checkStatus[adapterPosition]
//            binding.saveSwitch.setOnClickListener {
//                if (!binding.saveSwitch.isChecked){
//                    checkStatus.put(adapterPosition, false)
//                }else{
//                    checkStatus.put(adapterPosition, true)
//                }
//            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SaveMusicRVAdapter.SaveMusicItemHolder =
        SaveMusicItemHolder(
            ItemSaveSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: SaveMusicRVAdapter.SaveMusicItemHolder, position: Int) {
        holder.bind(musicList[position])
    }

    override fun getItemCount() = musicList.size

    fun setList(list: MutableList<MusicModel>) {
        musicList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    fun swapList(form: Int, to: Int) {
       // Collections.swap(musicList, form, to)
        notifyItemMoved(form, to)
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