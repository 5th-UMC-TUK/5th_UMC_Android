package com.lacuna.chapter1.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lacuna.chapter1.databinding.FragmentAlbumIncludedSongsBinding

class AlbumIncludedSongsFragment : Fragment() {

    lateinit var binding: FragmentAlbumIncludedSongsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumIncludedSongsBinding.inflate(inflater, container, false)

        binding.songMixoffTg.setOnClickListener {
            setMixStatus(true)
        }
        binding.songMixonTg.setOnClickListener {
            setMixStatus(false)
        }

        return binding.root
    }

    fun setMixStatus(isMixing : Boolean) {
        if(isMixing) {
            binding.songMixonTg.visibility = View.VISIBLE
            binding.songMixoffTg.visibility = View.GONE
        }
        else {
            binding.songMixonTg.visibility = View.GONE
            binding.songMixoffTg.visibility = View.VISIBLE
        }
    }
}