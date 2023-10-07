package com.example.chapter1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chapter1.R
import com.example.chapter1.adapter.SongAdapter
import com.example.chapter1.databinding.FragmentTitleBinding
import com.example.chapter1.model.TitleModel


class TitleFragment(private val data: TitleModel) : Fragment() {
    private lateinit var binding: FragmentTitleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTitleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            titleFrame.setBackgroundResource(data.titleImgResId)
            albumInfo.text = data.albumInfo
            titleText.text = data.titleText
            val songAdapter = SongAdapter()
            albumList.adapter = songAdapter
            songAdapter.submitList(data.songs)
        }

    }
}