package com.hgh.flo_clone.adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hgh.flo_clone.data.AlbumModel
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.ItemMusicSmallBinding

class MusicRVAdapter(
    val MusicClickListener : (AlbumModel)->Unit,
    val playClickListener: (MusicModel)->Unit,
) : RecyclerView.Adapter<MusicRVAdapter.MusicItemHolder>() {

    private var musicList: List<MusicModel> = listOf()
    lateinit var album: AlbumModel

    inner class MusicItemHolder(
        private val binding: ItemMusicSmallBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MusicModel) {
            binding.itemMusicTitle.text = data.title
            binding.itemMusicSinger.text = data.singer

            binding.itemMusicSumNail.setOnClickListener{
                MusicClickListener(album)
            }
            binding.playMusic.setOnClickListener {
                playClickListener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicItemHolder =
        MusicItemHolder(
            ItemMusicSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = musicList.size

    override fun onBindViewHolder(holder: MusicItemHolder, position: Int) {
        holder.bind(musicList[position])
    }

    fun setList(list: List<MusicModel>, albumList: AlbumModel) {
        musicList = list
        album = albumList
        notifyDataSetChanged()
    }
}