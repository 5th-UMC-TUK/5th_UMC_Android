package com.lacuna.chapter1.album

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.lacuna.chapter1.HomeFragment
import com.lacuna.chapter1.MainActivity
import com.lacuna.chapter1.R
import com.lacuna.chapter1.adapter.AlbumVPAdapter
import com.lacuna.chapter1.data.Like
import com.lacuna.chapter1.data.SongDatabase
import com.lacuna.chapter1.data.TodayMusic
import com.lacuna.chapter1.databinding.FragmentAlbumBinding


class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    private var gson: Gson = Gson()
    private val information = arrayListOf("수록곡", "상세정보", "영상")

    private var isLiked: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

        // gson을 이용해 json데이터를 객체로 만들어 저장
        val todayMusicJson = arguments?.getString("titleInfo")
        val todayMusic = gson.fromJson(todayMusicJson, TodayMusic::class.java)

        // Home에서 넘어온 데이터를 반영
        isLiked = isLikedAlbum(todayMusic.id)
        setInit(todayMusic)
        setOnClickListeners(todayMusic)
//        setFragmentResultListener("TitleInfo") { requestKey, bundle ->
//            binding.albumMusicTitleTv.text = bundle.getString("title")
//        }
//
//        setFragmentResultListener("SingerInfo") { requestKey, bundle ->
//            binding.albumSingerNameTv.text = bundle.getString("singer")
//        }

        binding.albumBackIv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        // TabLayoutMediator는 탭 레이아웃을 뷰페이저 2와 연결하는 중재자
        // attach는 뷰페이저와 탭 레이아웃을 붙여줌
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) {
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
    private fun setInit(todayMusic: TodayMusic){
        binding.albumAlbumIv.setImageResource(todayMusic.coverImg!!)
        binding.albumMusicTitleTv.text = todayMusic.title.toString()
        binding.albumSingerNameTv.text = todayMusic.singer.toString()
        if (isLiked){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun getJwt(): Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt", 0) // default값 0
    }

    private fun likeAlbum(userId: Int, albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }

    private fun isLikedAlbum(albumId: Int): Boolean { // 앨범에 좋아요 되었는지 확인
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt() // 로그인한 유저 jwt 확인

        val likeId: Int? = songDB.albumDao().isLikedAlbum(userId, albumId) // 어떤 유저가 해당 앨범을 좋아요 했는지 확인
        return likeId != null
    }

    private fun disLikedAlbum(albumId: Int){ // 앨범에 좋아요 되었는지 확인
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt() // 로그인한 유저 jwt 확인

        songDB.albumDao().disLikedAlbum(userId, albumId) // 어떤 유저가 해당 앨범을 좋아요 했는지 확인
    }

    private fun setOnClickListeners(album: TodayMusic){
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener {
            if(isLiked) {
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(album.id)
            }

            else {
                binding.albumLikeIv.setImageResource((R.drawable.ic_my_like_on))
                likeAlbum(userId, album.id)
            }
        }
    }


}