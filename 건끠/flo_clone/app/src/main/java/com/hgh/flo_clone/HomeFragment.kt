package com.hgh.flo_clone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hgh.flo_clone.adapter.MusicRVAdapter
import com.hgh.flo_clone.adapter.VideoRVAdapter
import com.hgh.flo_clone.data.AlbumModel
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val musicAdapter by lazy {
        MusicRVAdapter { album ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAlbumFragment( argAlbum = album))
        }
    }
    private val videoAdapter by lazy { VideoRVAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.musicRecyclerView.adapter = musicAdapter
        binding.musicRecyclerView2.adapter = musicAdapter
        binding.musicVideoRecyclerView.adapter = videoAdapter

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

        val albumDummy = AlbumModel(
            albumTitle = "1st [EP]New Jeans",
            albumSinger = "New Jeans",
            albumOutline = "2022.07.22 | 정규 | 팝",
            musicList = listOf<MusicModel>(
                MusicModel("Attention", "NewJens", isTitle = true),
                MusicModel("Hype boy", "NewJens", isTitle = true),
                MusicModel("Cookie", "NewJens", isTitle = true),
                MusicModel("Hurt","NewJens")
            )
        )

        musicAdapter.setList(musicDummy,albumDummy)
        videoAdapter.setList(musicDummy)
    }
}