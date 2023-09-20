package com.hgh.flo_clone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hgh.flo_clone.databinding.FragmentAudioBinding
import com.hgh.flo_clone.databinding.FragmentHomeBinding

class AudioFragment : Fragment() {

    lateinit var binding: FragmentAudioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAudioBinding.inflate(inflater,container,false)
        .also { binding = it }.root


}