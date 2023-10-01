package com.hgh.flo_clone.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hgh.flo_clone.databinding.FragmentSongFileBinding

class SongFileFragment : Fragment(){
    lateinit var binding: FragmentSongFileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSongFileBinding.inflate(inflater,container,false)
        .also {binding = it}.root
}