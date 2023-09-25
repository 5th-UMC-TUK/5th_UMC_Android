package com.example.chapter1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chapter1.R
import com.example.chapter1.databinding.FragmentAlbumBinding



class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        resId?.let {
            binding.albumImg.setImageResource(it)
        }
        binding.albumBack.setOnClickListener {
            val mainFrag = parentFragmentManager.findFragmentByTag("main")
            parentFragmentManager.beginTransaction()
                .show(mainFrag!!)
                .remove(this)
                .commit()
        }
    }


}