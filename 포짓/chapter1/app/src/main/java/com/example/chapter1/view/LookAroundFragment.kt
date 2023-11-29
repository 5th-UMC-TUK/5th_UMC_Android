package com.example.chapter1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chapter1.adapter.ChartAdapter
import com.example.chapter1.databinding.FragmentLookAroundBinding
import com.example.chapter1.network.SongRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LookAroundFragment : Fragment() {
    private lateinit var binding: FragmentLookAroundBinding
    private val repository = SongRepository()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLookAroundBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChart()
    }

    private fun initChart() {
        CoroutineScope(Dispatchers.IO).launch {
            val chart = CoroutineScope(Dispatchers.IO).async {
                repository.getAllSongs()
            }.await()

            chart?.let {
                withContext(Dispatchers.Main) {
                    val adapter = ChartAdapter()
                    binding.lookAroundChart.layoutManager = LinearLayoutManager(requireContext())
                    binding.lookAroundChart.adapter = adapter
                    adapter.submitList(it.result.songs)
                }
            }
        }
    }


}