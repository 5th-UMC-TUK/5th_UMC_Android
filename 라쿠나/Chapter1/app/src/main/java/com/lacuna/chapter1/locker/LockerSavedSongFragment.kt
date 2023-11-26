package com.lacuna.chapter1.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lacuna.chapter1.adapter.LockerSavedSongAdapter
//import com.lacuna.chapter1.data.SavedSong
import com.lacuna.chapter1.data.Song
import com.lacuna.chapter1.data.SongDatabase
import com.lacuna.chapter1.data.TodayMusic
import com.lacuna.chapter1.databinding.FragmentSavedSongBinding

class LockerSavedSongFragment: Fragment() {

    lateinit var binding: FragmentSavedSongBinding
    lateinit var songDB: SongDatabase

//    private val savedSong: ArrayList<SavedSong> = arrayListOf()
    private val savedSong: ArrayList<TodayMusic> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireActivity())!!
//        addData() // 더미데이터 추가

        //리사이클러뷰 어댑터 연결
        val savedSongAdapter = LockerSavedSongAdapter()
        binding.lockerSavedSongRecyclerView.adapter = savedSongAdapter
        savedSongAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song> )

        // 라사이클러뷰 클릭 이벤트
        savedSongAdapter.setMyItemClickListener(object: LockerSavedSongAdapter.OnItemClickListener{
            override fun onItemClick(song: Song) {
                TODO("Not yet implemented")
            }

            override fun onRemoveSavedSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
            }
        })

        return binding.root
    }

//    private fun addData() {
//        savedSong.add(SavedSong(R.drawable.img_album_exp1, "Butter", "방탄소년단"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp2, "Lilac", "아이유(IU)"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp3, "Next Level", "에스파"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp4, "Boy with Luv", "방탄소년단"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp5, "BBoom BBoom", "모모랜드"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp6, "Weekend", "태연"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp1, "Butter", "방탄소년단"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp2, "Lilac", "아이유(IU)"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp3, "Next Level", "에스파"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp4, "Boy with Luv", "방탄소년단"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp5, "BBoom BBoom", "모모랜드"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp6, "Weekend", "태연"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp1, "Butter", "방탄소년단"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp2, "Lilac", "아이유(IU)"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp3, "Next Level", "에스파"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp4, "Boy with Luv", "방탄소년단"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp5, "BBoom BBoom", "모모랜드"))
//        savedSong.add(SavedSong(R.drawable.img_album_exp6, "Weekend", "태연"))
//    }
}