package com.hgh.flo_clone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hgh.flo_clone.databinding.FragmentHomeBinding
import com.hgh.flo_clone.databinding.FragmentLookingBinding

class LookingFragment : Fragment() {

    lateinit var binding: FragmentLookingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLookingBinding.inflate(inflater,container,false)
        .also { binding = it }.root


}