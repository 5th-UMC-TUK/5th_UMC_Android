package com.lacuna.chapter1.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lacuna.chapter1.R
import com.lacuna.chapter1.adapter.LockerSavedSongAdapter
import com.lacuna.chapter1.data.SavedSong
import com.lacuna.chapter1.data.TodayMusic
import com.lacuna.chapter1.databinding.FragmentSavedSongBinding
import com.lacuna.floclone.adapter.TodayMusicAdapter

class LockerSavedSongFragment: Fragment() {

    lateinit var binding: FragmentSavedSongBinding
    private val savedSong: ArrayList<SavedSong> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)

        addData() // 더미데이터 추가
        //리사이클러뷰 어댑터 연결
        val savedSongAdapter = LockerSavedSongAdapter(savedSong)
        binding.lockerSavedSongRecyclerView.adapter = savedSongAdapter

        // 라사이클러뷰 클릭 이벤트
        savedSongAdapter.setMyItemClickListener(object: LockerSavedSongAdapter.OnItemClickListener{
            override fun onItemClick(savedSong: SavedSong) {
                TODO("Not yet implemented")
            }

            override fun onRemoveSavedSong(position: Int) {
                savedSongAdapter.removeItem(position)
            }
        })

        return binding.root
    }

    private fun addData() {
        savedSong.add(SavedSong(R.drawable.img_album_exp1, "Butter", "방탄소년단"))
        savedSong.add(SavedSong(R.drawable.img_album_exp2, "Lilac", "아이유(IU)"))
        savedSong.add(SavedSong(R.drawable.img_album_exp3, "Next Level", "에스파"))
        savedSong.add(SavedSong(R.drawable.img_album_exp4, "Boy with Luv", "방탄소년단"))
        savedSong.add(SavedSong(R.drawable.img_album_exp5, "BBoom BBoom", "모모랜드"))
        savedSong.add(SavedSong(R.drawable.img_album_exp6, "Weekend", "태연"))
        savedSong.add(SavedSong(R.drawable.img_album_exp1, "Butter", "방탄소년단"))
        savedSong.add(SavedSong(R.drawable.img_album_exp2, "Lilac", "아이유(IU)"))
        savedSong.add(SavedSong(R.drawable.img_album_exp3, "Next Level", "에스파"))
        savedSong.add(SavedSong(R.drawable.img_album_exp4, "Boy with Luv", "방탄소년단"))
        savedSong.add(SavedSong(R.drawable.img_album_exp5, "BBoom BBoom", "모모랜드"))
        savedSong.add(SavedSong(R.drawable.img_album_exp6, "Weekend", "태연"))
        savedSong.add(SavedSong(R.drawable.img_album_exp1, "Butter", "방탄소년단"))
        savedSong.add(SavedSong(R.drawable.img_album_exp2, "Lilac", "아이유(IU)"))
        savedSong.add(SavedSong(R.drawable.img_album_exp3, "Next Level", "에스파"))
        savedSong.add(SavedSong(R.drawable.img_album_exp4, "Boy with Luv", "방탄소년단"))
        savedSong.add(SavedSong(R.drawable.img_album_exp5, "BBoom BBoom", "모모랜드"))
        savedSong.add(SavedSong(R.drawable.img_album_exp6, "Weekend", "태연"))
    }
}