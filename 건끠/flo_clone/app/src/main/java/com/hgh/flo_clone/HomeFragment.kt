package com.hgh.flo_clone

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hgh.flo_clone.adapter.HomeBannerRVAdapter
import com.hgh.flo_clone.adapter.MusicRVAdapter
import com.hgh.flo_clone.adapter.VideoRVAdapter
import com.hgh.flo_clone.data.AlbumModel
import com.hgh.flo_clone.data.HomeBannerModel
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.FragmentHomeBinding
import com.hgh.flo_clone.server.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    //배너 전환 중복 막기
    override fun onAttach(context: Context) {
        super.onAttach(context)
        CoroutineScope(Dispatchers.Main).launch {
            while (bannerLoop) {
                if (binding.homaBannerViewPager.currentItem == 3) {
                    delay(3000)
                    binding.homaBannerViewPager.currentItem = 0
                    continue

                }
                delay(3000)
                binding.homaBannerViewPager.setCurrentItem(
                    binding.homaBannerViewPager.currentItem,
                    true
                )
                binding.homaBannerViewPager.currentItem += 1
            }
        }
    }

    val apiService by inject<ApiRepository>()

    lateinit var binding: FragmentHomeBinding
    private val musicAdapter by lazy {
        MusicRVAdapter(
            //Alum Fragmetn 전환
            MusicClickListener = { albumId ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToAlbumFragment(
                        argAlbum = albumId
                    )
                )
            },
            //miniPlayer 에 정보 전달
            playClickListener = { music ->
                (requireActivity() as MainActivity).binding.nowPlayTitle.text = music.title
                (requireActivity() as MainActivity).binding.nowPlaySinger.text = music.singer
                (requireActivity() as MainActivity).currentPosition = 0
            }
        )
    }

    var bannerLoop = true

//    val handler = Handler(Looper.getMainLooper()) {
//        setPage()
//        true
//    }
//    lateinit var thread: Thread

    private val videoAdapter by lazy { VideoRVAdapter() }
    lateinit var bannerAdapter: HomeBannerRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )= FragmentHomeBinding.inflate(inflater, container, false)
            .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.musicRecyclerView.adapter = musicAdapter
        binding.musicRecyclerView2.adapter = musicAdapter
        binding.musicVideoRecyclerView.adapter = videoAdapter

        dummyData()
        lifecycleScope.launch {
            musicAdapter.setList(apiService.getSongList() ?: listOf())
        }

        //Banner 자동 전환 (Banner 는 fragment 재사용)
        bannerAdapter = HomeBannerRVAdapter(
            this, dummyBanner()
        )
        binding.homaBannerViewPager.adapter = bannerAdapter
        binding.indicator.setViewPager(binding.homaBannerViewPager)
        binding.indicator.createIndicators(4, 0)
        binding.facebookBtn.setOnClickListener {
            serviceStart(it)
        }
        binding.twiterBtn.setOnClickListener {
            serviceStop(it)
        }

//        thread = Thread(PagerRunnable())
//        if (thread.state !=Thread.State.RUNNABLE) {
//            thread.start()
//        }

    }
//
//    inner class PagerRunnable : Runnable {
//        override fun run() {
//            while (true) {
//                try {
//                    Thread.sleep(3000)
//                    handler.sendEmptyMessage(0)
//                } catch (e: InterruptedException) {
//                    Log.d("interupt", "interupt발생")
//                }
//            }
//        }
//    }
//
//    private fun setPage() {
//        if (binding.homaBannerViewPager.currentItem == 3) {
//            binding.homaBannerViewPager.currentItem = 0
//            return
//        }
//        binding.homaBannerViewPager.setCurrentItem(binding.homaBannerViewPager.currentItem, true)
//        binding.homaBannerViewPager.currentItem += 1
//    }

    fun serviceStart(view: View){
        val intent = Intent(context,PlayerService::class.java)
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    fun serviceStop(view: View){
        val intent = Intent(context,PlayerService::class.java)
        requireContext().stopService(intent)
    }

    @SuppressLint("ResourceType")
    fun dummyBanner(): List<HomeBannerFragment> {
        val homeBannerModel1 = HomeBannerModel(
            title = "저녁엔 감성을 꾹꾹 눌러 담은 플리",
            songTitle1 = "주저하는 연인들을 위해", songSinger1 = "잔나비",
            songTitle2 = "Bambi", songSinger2 = "백현", color = R.color.banner2
        )
        val homeBannerModel2 = HomeBannerModel(
            title = "일하는 중 귀로 떠나는 \n해외여행",
            songTitle1 = "Hana", songSinger1 = "ORANGE RANGE",
            songTitle2 = "Tokyo Flash", songSinger2 = "Vaundy", color = R.color.banner1
        )
        val homeBannerModel3 = HomeBannerModel(
            title = "가을 톤을 고스란히 담은 감성 \n인디",
            songTitle1 = "가을의 시작", songSinger1 = "주은하",
            songTitle2 = "너의 평범한 날", songSinger2 = "한율", color = R.color.banner3
        )
        val homeBannerModel4 = HomeBannerModel(
            title = "샤워할 땐 라떼 아이돌로 빙의",
            songTitle1 = "yayaya", songSinger1 = "티아라",
            songTitle2 = "So Hot", songSinger2 = "원더걸스", color = R.color.banner4
        )

        return listOf(
            HomeBannerFragment.newInstance(homeBannerModel1),
            HomeBannerFragment.newInstance(homeBannerModel2),
            HomeBannerFragment.newInstance(homeBannerModel3),
            HomeBannerFragment.newInstance(homeBannerModel4),
        )
    }

    fun dummyData() {
        val musicDummy = listOf<MusicModel>(
            MusicModel("Attention", "NewJens"),
            MusicModel("Hype boy", "NewJens"),
            MusicModel("Cookie", "NewJens"),
            MusicModel("ETA", "NewJens"),
            MusicModel("Dance Hall", "Mrs.Green Apple"),
            MusicModel("Zutto Zutto Zutto", "syakai")
        )

        val albumDummy = AlbumModel(
            albumTitle = "1st [EP]New Jeans",
            albumSinger = "New Jeans",
            albumOutline = "2022.07.22 | 정규 | 팝",
            musicList = listOf<MusicModel>(
                MusicModel("Attention", "NewJens", isTitle = true),
                MusicModel("Hype boy", "NewJens", isTitle = true),
                MusicModel("Cookie", "NewJens", isTitle = true),
                MusicModel("Hurt", "NewJens")
            )
        )

        //musicAdapter.setList(musicDummy, albumDummy)
        videoAdapter.setList(musicDummy)
    }
}