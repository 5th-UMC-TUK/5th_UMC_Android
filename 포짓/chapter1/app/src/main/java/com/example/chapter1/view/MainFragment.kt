package com.example.chapter1.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.chapter1.R
import com.example.chapter1.adapter.AdAdapter
import com.example.chapter1.adapter.TitleAdapter
import com.example.chapter1.adapter.TodaySongAdapter
import com.example.chapter1.adapter.VideoAdapter
import com.example.chapter1.databinding.FragmentMainBinding
import com.example.chapter1.model.Album
import com.example.chapter1.model.Song
import com.example.chapter1.model.TitleAlbumModel
import com.example.chapter1.model.TodaySongModel
import com.example.chapter1.network.SongRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val repository = SongRepository()

    //    val songs = arrayListOf<Song>()
//    val albums = arrayListOf<Album>()
    private val titleAlbums = arrayListOf<Album>()

    //    lateinit var songDB: SongDB
    val titles = arrayListOf<TitleAlbumModel>()

    private val titleHandler = Handler(Looper.getMainLooper()) {
        setTitlePage()
        true
    }
    private val adHandler = Handler(Looper.getMainLooper()) {
        setAdPage()
        true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        removeWindowLimit()
        setTitleAdapter()
//        setAdAdapter()


        binding.todaySongTotal.setOnClickListener {
            binding.todaySongTotal.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.flo_blue
                )
            )
            binding.todaySongInner.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.todaySongOuter.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        }
        binding.todaySongInner.setOnClickListener {
            binding.todaySongTotal.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.todaySongInner.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.flo_blue
                )
            )
            binding.todaySongOuter.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        }
        binding.todaySongOuter.setOnClickListener {
            binding.todaySongTotal.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.todaySongInner.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.todaySongOuter.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.flo_blue
                )
            )
        }

        val videoArray = listOf(
            TodaySongModel("제목", "가수", R.drawable.img_video_exp, "album 정보"),
            TodaySongModel("제목", "가수", R.drawable.img_video_exp, "album 정보"),
            TodaySongModel("제목", "가수", R.drawable.img_video_exp, "album 정보"),
            TodaySongModel("제목", "가수", R.drawable.img_video_exp, "album 정보")
        )
        val videoAdapter = VideoAdapter()
        binding.videoRv.adapter = videoAdapter
        videoAdapter.submitList(videoArray)


    }


    private fun setTitleAdapter() {
//        songDB = SongDB.getDB(requireContext())
//        CoroutineScope(Dispatchers.IO).launch {
//            val albumDao = songDB.albumDao()
//            val songDao = songDB.songDao()
//
//            val albumsDeferred = CoroutineScope(Dispatchers.IO).async {
//                albumDao.getAllAlbum()
//            }
//            val albumsResult = albumsDeferred.await()
//            albums.addAll(albumsResult)
//
//            val songsDeferred = CoroutineScope(Dispatchers.IO).async {
//                songDao.getAllSong()
//            }
//            val songsResult = songsDeferred.await()
//            songs.addAll(songsResult)
//            albums.forEach { album ->
//                val albumSongs = mutableListOf<Song>()
//                songs.forEach { song ->
//                    if (album.id == song.albumIdx) {
//                        albumSongs.add(song)
//                    }
//                }
//                titles.add(TitleAlbumModel(album, albumSongs))
//            }
//            withContext(Dispatchers.Main) {
//                submitTileList()
//            }
//        }
        CoroutineScope(Dispatchers.IO).launch {
            val albums = CoroutineScope(Dispatchers.IO).async {
                repository.getAllAlbums()
            }.await()
            val songs = CoroutineScope(Dispatchers.IO).async {
                repository.getAllSongs()
            }.await()
            if (albums != null && songs != null) {
                if (albums.code == 1000 && songs.code == 1000) {
                    titleAlbums.addAll(albums.result.albums)
                    titleAlbums.forEach { album ->
                        val albumSongs = mutableListOf<Song>()
                        songs.result.songs.forEach { song ->
                            if (album.albumIdx == song.albumIdx) {
                                albumSongs.add(song)
                            }
                        }
                        titles.add(TitleAlbumModel(album, albumSongs))
                    }
                    withContext(Dispatchers.Main) {
                        submitTileList()
                        setTodaySongAdapter()
                    }
                }
            }
        }
    }

    private fun submitTileList() {
        val titleAdapter = TitleAdapter(titles, this@MainFragment)
        binding.titleViewpager.adapter = titleAdapter
        binding.titleViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.titleViewpager.overScrollMode = View.OVER_SCROLL_NEVER
        binding.titleIndicator.setViewPager(binding.titleViewpager)
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                Thread.sleep(5000)
                titleHandler.sendEmptyMessage(0)
            }
        }
    }


    private fun setTodaySongAdapter() {
        val todaySongAdapter = TodaySongAdapter()
        todaySongAdapter.setClickListener(
            img = {
                val albumFragment = AlbumFragment()
                val bundle = Bundle()
                val data = it
                bundle.putInt("id", data.albumIdx)
                albumFragment.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌

                parentFragmentManager.beginTransaction()
                    .add(R.id.fragment_frame, albumFragment)
                    .hide(this@MainFragment)
                    .show(albumFragment)
                    .addToBackStack(null) // 필요에 따라 back stack에 추가할 수 있습니다.
                    .commit()
            },
            btn = {

            })
        submitTodaySongList(todaySongAdapter)
    }

    private fun submitTodaySongList(todaySongAdapter: TodaySongAdapter) {
        binding.everydaySongRv.adapter = todaySongAdapter
        binding.todaySongRv.adapter = todaySongAdapter
        
        todaySongAdapter.submitList(titleAlbums.toList())
    }

    //
    private fun setAdAdapter() {
        binding.adViewpager.adapter = AdAdapter(
            listOf(R.drawable.img_home_viewpager_exp, R.drawable.img_home_viewpager_exp2),
            this@MainFragment
        )
        binding.adViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                Thread.sleep(7000)
                adHandler.sendEmptyMessage(0)
            }
        }
    }


    private fun removeWindowLimit() {
        requireActivity().window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        if (Build.VERSION.SDK_INT >= 30) {    // API 30 에 적용
            WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        }
    }

    private fun setTitlePage() {
        binding.titleViewpager.setCurrentItem(
            (binding.titleViewpager.currentItem + 1) % binding.titleViewpager.adapter!!.itemCount,
            true
        )
    }

    private fun setAdPage() {
        binding.adViewpager.setCurrentItem(
            (binding.adViewpager.currentItem + 1) % binding.adViewpager.adapter!!.itemCount,
            true
        )
    }
}