package com.hgh.flo_clone.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hgh.flo_clone.databinding.FragmentSaveSongBinding
import java.util.zip.Inflater

class SaveSongFragment : Fragment(){
    lateinit var binding: FragmentSaveSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSaveSongBinding.inflate(inflater,container,false)
        .also {binding = it}.root
}