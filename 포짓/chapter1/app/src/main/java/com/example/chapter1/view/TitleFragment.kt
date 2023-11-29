package com.example.chapter1.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.chapter1.adapter.SongAdapter
import com.example.chapter1.databinding.FragmentTitleBinding
import com.example.chapter1.model.TitleAlbumModel


class TitleFragment(private val data: TitleAlbumModel) : Fragment() {
    private lateinit var binding: FragmentTitleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTitleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            Glide.with(titleFrame).load(data.album.coverImgUrl)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        a_resource: Drawable,
                        a_transition: Transition<in Drawable>?
                    ) {
                        val layout = binding.titleFrame
                        layout.background = a_resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
            albumInfo.text = data.album.title
            titleText.text = data.album.title
            val songAdapter = SongAdapter()
            albumList.adapter = songAdapter
            songAdapter.submitList(data.song)
        }

    }
}