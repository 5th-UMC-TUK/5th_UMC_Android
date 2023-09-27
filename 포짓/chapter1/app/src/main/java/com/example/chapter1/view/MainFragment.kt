package com.example.chapter1.view

import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.chapter1.R
import com.example.chapter1.adapter.OnItemClickListener
import com.example.chapter1.adapter.TitleAdapter
import com.example.chapter1.adapter.TodaySongAdapter
import com.example.chapter1.adapter.VideoAdapter
import com.example.chapter1.databinding.FragmentMainBinding
import com.example.chapter1.model.SongModel
import com.example.chapter1.model.TitleModel
import com.example.chapter1.model.TodaySongModel


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
            WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        }

        val titleList = listOf(
            TitleModel("포근하게 덮어주는 꿈의\n목소리","총 15곡 2019.11.11", R.drawable.img_first_album_default,
                listOf(SongModel("잠이 안온다", "젠(Zen)", R.drawable.img_album_exp))),
            TitleModel("포근하게 덮어주는 꿈의\n목소리","총 15곡 2019.11.11", R.drawable.img_first_album_default,
                listOf(SongModel("잠이 안온다", "젠(Zen)", R.drawable.img_album_exp))),
            TitleModel("포근하게 덮어주는 꿈의\n목소리","총 15곡 2019.11.11", R.drawable.img_first_album_default,
                listOf(SongModel("잠이 안온다", "젠(Zen)", R.drawable.img_album_exp)))
        )

        val titleAdapter = TitleAdapter()
        binding.titleViewpager.adapter = titleAdapter
        binding.titleViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        titleAdapter.submitList(titleList)

        binding.titleIndicator.setViewPager(binding.titleViewpager)

//        val adImages = listOf(R.drawable.img_home_viewpager_exp, R.drawable.img_home_viewpager_exp2)
//        val adAdapter = AdAdapter()
//        binding.adViewpager.adapter = adAdapter
//        adAdapter.submitList(adImages)

        val todaySongArray = listOf(
            TodaySongModel("사이렌", "marsjay (마스제이)", R.drawable.siren, "album 정보"),
            TodaySongModel("Got Me Started", "Troye Sivan", R.drawable.got_me_started, "album 정보"),
            TodaySongModel("소품집 Vol.1", "장동원", R.drawable.vol1, "album 정보"),
            TodaySongModel("The Wave", "다크비 (DKB)", R.drawable.the_wave, "album 정보")
        )

        val todaySongAdapter = TodaySongAdapter()
        binding.todaySongRv.adapter = todaySongAdapter
        todaySongAdapter.submitList(todaySongArray)
        todaySongAdapter.setOnItemClickListener(object: OnItemClickListener{
            override fun onItemClick(position: Int) {
                val albumFragment = AlbumFragment()
                val bundle = Bundle()
                val data = todaySongAdapter.getItem(position)
                bundle.putString("song", data.songName)
                bundle.putString("singer", data.songSinger)
                bundle.putString("detail", data.songDetail)
                bundle.putInt("resId", data.songImgResId)
                albumFragment.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌

                parentFragmentManager.beginTransaction()
                    .add(R.id.fragment_frame, albumFragment)
                    .hide(this@MainFragment)
                    .show(albumFragment)
                    .addToBackStack(null) // 필요에 따라 back stack에 추가할 수 있습니다.
                    .commit()


            }

        })

        binding.todaySongTotal.setOnClickListener{
            binding.todaySongTotal.setTextColor(ContextCompat.getColor(requireContext(), R.color.flo_blue))
            binding.todaySongInner.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.todaySongOuter.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
        binding.todaySongInner.setOnClickListener {
            binding.todaySongTotal.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.todaySongInner.setTextColor(ContextCompat.getColor(requireContext(), R.color.flo_blue))
            binding.todaySongOuter.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
        binding.todaySongOuter.setOnClickListener {
            binding.todaySongTotal.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.todaySongInner.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.todaySongOuter.setTextColor(ContextCompat.getColor(requireContext(), R.color.flo_blue))
        }

        val everydaySongArray = listOf(
            TodaySongModel("제목", "가수", R.drawable.img_potcast_exp, "album 정보"),
            TodaySongModel("제목", "가수", R.drawable.img_potcast_exp, "album 정보"),
            TodaySongModel("제목", "가수", R.drawable.img_potcast_exp, "album 정보"),
            TodaySongModel("제목", "가수", R.drawable.img_potcast_exp, "album 정보")
        )
        val everydaySongAdapter = TodaySongAdapter()
        binding.everydaySongRv.adapter = everydaySongAdapter
        everydaySongAdapter.submitList(everydaySongArray)

        val videoArray = listOf(
            TodaySongModel("제목", "가수", R.drawable.img_video_exp, "album 정보"),
            TodaySongModel("제목", "가수", R.drawable.img_video_exp, "album 정보"),
            TodaySongModel("제목", "가수", R.drawable.img_video_exp, "album 정보"),
            TodaySongModel("제목", "가수", R.drawable.img_video_exp, "album 정보")
        )
        val videoAdapter = VideoAdapter()
        binding.videoRv.adapter = videoAdapter
        videoAdapter.submitList(videoArray)


    }


}