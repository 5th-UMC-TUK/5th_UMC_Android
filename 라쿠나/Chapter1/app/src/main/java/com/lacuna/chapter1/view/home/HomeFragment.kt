package com.lacuna.chapter1.view.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.lacuna.chapter1.*
import com.lacuna.chapter1.adapter.AlbumRVAdapter
import com.lacuna.chapter1.adapter.BannerVPAdapter
import com.lacuna.chapter1.adapter.HomePannelVPAdapter
import com.lacuna.chapter1.view.album.AlbumFragment
import com.lacuna.chapter1.data.*
import com.lacuna.chapter1.databinding.FragmentHomeBinding
import com.lacuna.chapter1.view.MainActivity

class HomeFragment : Fragment(), AlbumView {

    lateinit var binding: FragmentHomeBinding
    private val sliderImageHandler: Handler = Handler()
    private val sliderImageRunnable = Runnable { binding.homePannelViewPager.currentItem = binding.homePannelViewPager.currentItem + 1 }
    private val todayMusic: ArrayList<TodayMusic> = arrayListOf()
    private lateinit var songDB: SongDatabase
    private lateinit var albumAdapter: AlbumRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        songDB = SongDatabase.getInstance(requireContext() as FragmentActivity)!!
//        todayMusic.addAll(songDB.albumDao().getAlbums()) // 앨범 더미데이터를 todayMusic에 초기화
//        Log.d("albumlist", todayMusic.toString())

        // 리사이클러뷰 어댑터 연결
//        val todayMusicAdapter = TodayMusicAdapter(todayMusic)

        // 라사이클러뷰 클릭 이벤트
//        albumAdapter.setMyItemClickListener(object: albumAdapter.ItemClickListener{
////            override fun onItemClick(album: ResultAlbum) {
////                changeAlbumFragment(album)
////            }
////
////            override fun onPlayAlbum(album: ResultAlbum) {
////                sendData(album)
////            }
////
////        })

        // 홈 패널 뷰페이저 어댑터 연결
        val pannelAdapter = HomePannelVPAdapter(this)
        pannelAdapter.addFragment(HomePannelFragment("포근하게 덮어주는 꿈의\n목소리"))
        pannelAdapter.addFragment(HomePannelFragment("새벽2시에 듣기 좋은\n발라드"))
        pannelAdapter.addFragment(HomePannelFragment("자동으로 춤이 나오는\n트로트"))
        pannelAdapter.addFragment(HomePannelFragment("헤드뱅잉 하다가 머리\n어지러운 록"))
        pannelAdapter.addFragment(HomePannelFragment("푸쳐핸접 하게 만드는\n힙합"))

        binding.homePannelViewPager.adapter = pannelAdapter
        binding.homePannelViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // 홈 패널 CircleIndicator 연동
        binding.homePannelCircleIndicator.setViewPager(binding.homePannelViewPager)
        // 자동 스크롤
        binding.homePannelViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderImageHandler.removeCallbacks(sliderImageRunnable)
                //자동 스크롤은 3초 당 한 번 넘어가도록
                sliderImageHandler.postDelayed(sliderImageRunnable, 3000)
            }
        })

        // 홈 배너 뷰페이저 어댑터 연결
        val bannaerAdapter = BannerVPAdapter(this)
        bannaerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        println("프래그먼트 값${bannaerAdapter.itemCount}")
        bannaerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        println("프래그먼트 값${bannaerAdapter.itemCount}")

        binding.homeBannerVp.adapter = bannaerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root

    }
    override fun onStart() {
        super.onStart()
        getAlbums()
    }
//    private fun changeAlbumFragment(todayMusic: TodayMusic) {
////        setFragmentResult("TitleInfo", bundleOf("title" to binding..text.toString()))
////        setFragmentResult("SingerInfo", bundleOf("singer" to binding..text.toString()))
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.main_frm, AlbumFragment().apply {
//                arguments = Bundle().apply {
//                    val gson = Gson()
//                    val todayMusicJson = gson.toJson(todayMusic)
//                    Log.d("bundle", todayMusicJson)
//                    putString("titleInfo", todayMusicJson)
//                }
//            }).commitAllowingStateLoss()
//    }
//    private fun sendData(todayMusic: TodayMusic){
//        if (activity is MainActivity) {
//            val activity = activity as MainActivity
//            activity.updateMainPlayerCl(todayMusic)
//        }
//    }
    private fun changeAlbumFragment(album: Album) {
//        setFragmentResult("TitleInfo", bundleOf("title" to binding..text.toString()))
//        setFragmentResult("SingerInfo", bundleOf("singer" to binding..text.toString()))
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    Log.d("bundle", albumJson)
                    putString("titleInfo", albumJson)
                }
            }).commitAllowingStateLoss()
    }
    private fun sendData(album: Album){
        if (activity is MainActivity) {
            val activity = activity as MainActivity
//            activity.updateMainPlayerCl(album)
        }
    }
    private fun initRecyclerView(result: ResultAlbum) {
        albumAdapter = AlbumRVAdapter(requireContext(), result)
        binding.homeTodayMusicRecyclerView.adapter = albumAdapter
        albumAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onPlayAlbum(album: Album) {
//                sendData(album)
            }

        })
    }

    private fun getAlbums() {
        val albumService = SongService()
        albumService.setAlbumView(this)
        albumService.getAlbums()

    }

    override fun onGetAlbumSuccess(code: Int, result: ResultAlbum) {
        initRecyclerView(result)
        Log.d("HOME-FRAG/SONG-RESPONSE", result.albums.toString())
    }

    override fun onGetAlbumFailure(code: Int, message: String) {
        Log.d("HOME-FRAG/SONG-RESPONSE", message)
    }
}