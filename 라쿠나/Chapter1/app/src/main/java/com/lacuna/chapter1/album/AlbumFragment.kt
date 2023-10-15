package com.lacuna.chapter1.album

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.tabs.TabLayoutMediator
import com.lacuna.chapter1.HomeFragment
import com.lacuna.chapter1.MainActivity
import com.lacuna.chapter1.R
import com.lacuna.chapter1.adapter.AlbumVPAdapter
import com.lacuna.chapter1.databinding.FragmentAlbumBinding


class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

        setFragmentResultListener("TitleInfo") { requestKey, bundle ->
            binding.albumMusicTitleTv.text = bundle.getString("title")
        }

        setFragmentResultListener("SingerInfo") { requestKey, bundle ->
            binding.albumSingerNameTv.text = bundle.getString("singer")
        }

        // binding.albumMusicTitleTv.text = arguments?.getString("title")
        // binding.albumSingerNameTv.text = arguments?.getString("singer")

        binding.albumBackIv.setOnClickListener {
//            (context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.main_frm, HomeFragment())
//                .commitAllowingStateLoss()
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
}