package com.example.chapter1.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chapter1.R
import com.example.chapter1.databinding.FragmentAdBinding

class AdFragment(private val resId: Int) : Fragment() {
    private lateinit var binding: FragmentAdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("resId", resId.toString())
        binding.adIv.setImageResource(resId)
    }


}