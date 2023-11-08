package com.example.chapter1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.example.chapter1.R
import com.example.chapter1.adapter.AlbumViewpagerAdapter
import com.example.chapter1.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator


class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val mainFragment = parentFragmentManager.findFragmentByTag("main")
            parentFragmentManager.beginTransaction()
                .show(mainFragment!!)
                .remove(this@AlbumFragment)
                .commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as MainActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.toolbar.menu.clear()
        binding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_new_24)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                binding.toolbar.menu.clear()
                menuInflater.inflate(R.menu.album_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }

        })
        binding.toolbar.setNavigationOnClickListener {
            val mainFragment = parentFragmentManager.findFragmentByTag("main")
            parentFragmentManager.beginTransaction()
                .show(mainFragment!!)
                .remove(this)
                .commit()
        }


        binding.albumViewpager.adapter = AlbumViewpagerAdapter(this@AlbumFragment)
        val tabs = arrayOf("수록곡", "상세정보", "영상")
        TabLayoutMediator(binding.albumTab, binding.albumViewpager) { tab, position ->
            tab.text = tabs[position]
        }.attach()

        val songName = arguments?.getString("song")
        val singerName = arguments?.getString("singer")
        val songDetail = arguments?.getString("detail")
        val resId = arguments?.getInt("resId")

        songName?.let {
            binding.albumTitle.text = it
        }
        singerName?.let {
            binding.singerName.text = it
        }
        songDetail?.let {
            binding.albumDetail.text = it
        }

        Toast.makeText(
            requireContext(),
            "album 제목: $songName  가수: $singerName  세부내용: $songDetail",
            Toast.LENGTH_SHORT
        ).show()

        resId?.let {
            binding.albumImg.setImageResource(it)
        }

    }


}