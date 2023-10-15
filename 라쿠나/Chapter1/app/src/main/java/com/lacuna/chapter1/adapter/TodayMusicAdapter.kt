package com.lacuna.chapter1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lacuna.chapter1.data.TodayMusic
import com.lacuna.chapter1.databinding.ItemTodayMusicBinding

class TodayMusicAdapter(private val todayMusicList: List<TodayMusic>): RecyclerView.Adapter<TodayMusicAdapter.TodayMusicViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodayMusicAdapter.TodayMusicViewHolder {
        val binding =  ItemTodayMusicBinding.inflate(
            LayoutInflater.from(parent.context), // layoutInflater 를 넘기기위해 함수 사용, ViewGroup 는 View 를 상속하고 View 는 이미 Context 를 가지고 있음
            parent, // 부모(리싸이클러뷰 = 뷰그룹)
            false   // 리싸이클러뷰가 attach 하도록 해야함 (우리가 하면 안됨)
        )
        return TodayMusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodayMusicAdapter.TodayMusicViewHolder, position: Int) {

        holder.bind(todayMusicList[position])
    }

    override fun getItemCount(): Int {
        return todayMusicList.size
    }

    class TodayMusicViewHolder(private val binding: ItemTodayMusicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todayMusic: TodayMusic) {
            binding.todayMusicTitleTv.text = todayMusic.title
            binding.todayMusicSingerTv.text = todayMusic.singer
            binding.root.setOnClickListener {
//                onItemClick(todayMusic)
            }


        }
    }
}
