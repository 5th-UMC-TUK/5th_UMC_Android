package com.lacuna.chapter1

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.viewpager2.widget.ViewPager2
import com.lacuna.chapter1.adapter.BannerVPAdapter
import com.lacuna.chapter1.adapter.HomePannelVPAdapter
import com.lacuna.chapter1.adapter.TodayMusicAdapter
import com.lacuna.chapter1.album.AlbumFragment
import com.lacuna.chapter1.data.TodayMusic
import com.lacuna.chapter1.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val sliderImageHandler: Handler = Handler()
    private val sliderImageRunnable = Runnable { binding.homePannelViewPager.currentItem = binding.homePannelViewPager.currentItem + 1 }
    private val todayMusic = listOf(
        TodayMusic(1, "라일락", "아이유(IU)"),
        TodayMusic(2, "라일락", "아이유(IU)"),
        TodayMusic(3, "라일락", "아이유(IU)"),
        TodayMusic(4, "라일락", "아이유(IU)"),
        )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 리사이클러뷰 어댑터 연결
//        binding.homeTodayMusicRecyclerView.adapter = TodayMusicAdapter(todayMusic)

        binding.homeAlbumImgIv1.setOnClickListener {

            setFragmentResult("TitleInfo", bundleOf("title" to binding.titleLilac.text.toString()))
            setFragmentResult("SingerInfo", bundleOf("singer" to binding.singerIu.text.toString()))
//            (context as MainActivity)
//                .supportFragmentManager
                parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
        }
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

}