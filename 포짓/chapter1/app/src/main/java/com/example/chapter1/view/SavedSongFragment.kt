package com.example.chapter1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chapter1.R
import com.example.chapter1.adapter.SavedSongAdapter
import com.example.chapter1.databinding.FragmentSavedSongBinding
import com.example.chapter1.model.SongModel


class SavedSongFragment : Fragment() {
    private lateinit var binding: FragmentSavedSongBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val songList = listOf(SongModel("사이렌", "marsjay (마스제이)", R.drawable.siren),
            SongModel("Got Me Started", "Troye Sivan", R.drawable.got_me_started),
            SongModel("소품집 Vol.1", "장동원", R.drawable.vol1),
            SongModel("The Wave", "다크비 (DKB)", R.drawable.the_wave)
        )
        val savedSongAdapter = SavedSongAdapter(songList)
        binding.savedSongRv.layoutManager = LinearLayoutManager(requireContext())
        binding.savedSongRv.adapter = savedSongAdapter

    }

}