package com.hgh.flo_clone.adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hgh.flo_clone.R
import com.hgh.flo_clone.data.AlbumModel
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.ItemMusicSmallBinding
import com.hgh.flo_clone.server.response.song.Song

class MusicRVAdapter(
    val MusicClickListener : (Int)->Unit,
    val playClickListener: (Song)->Unit,
) : RecyclerView.Adapter<MusicRVAdapter.MusicItemHolder>() {

    private var musicList: List<Song> = listOf()

    inner class MusicItemHolder(
        private val binding: ItemMusicSmallBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Song) {
            binding.itemMusicTitle.text = data.title
            binding.itemMusicSinger.text = data.singer
            Glide.with(itemView)
                .load(data.coverImgUrl)
                .error(R.drawable.appicon_image)
                .fallback(R.drawable.appicon_image)
                .into(binding.itemMusicSumNail)

            binding.itemMusicSumNail.setOnClickListener{
                MusicClickListener(data.albumIdx)
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

    fun setList(list: List<Song>) {
        musicList = list
        notifyDataSetChanged()
    }
}