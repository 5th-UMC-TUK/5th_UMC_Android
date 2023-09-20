package com.hgh.flo_clone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hgh.flo_clone.adapter.MusicRVAdapter
import com.hgh.flo_clone.adapter.VideoRVAdapter
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val musicAdapter by lazy { MusicRVAdapter() }
    private val videoAdapter by lazy { VideoRVAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater,container,false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.musicRecyclerView.adapter=musicAdapter
        binding.musicRecyclerView2.adapter=musicAdapter
        binding.musicVideoRecyclerView.adapter=videoAdapter

        dummyData()
    }


    fun dummyData() {
        val musicDummy = listOf<MusicModel>(
            MusicModel("Attention", "NewJens"),
            MusicModel("Hype boy", "NewJens"),
            MusicModel("Cookie", "NewJens"),
            MusicModel("ETA", "NewJens"),
            MusicModel("Dance Hall", "Mrs.Green Apple"),
            MusicModel("Zutto Zutto Zutto", "syakai")
        )
        musicAdapter.setList(musicDummy)
        videoAdapter.setList(musicDummy)
    }
}