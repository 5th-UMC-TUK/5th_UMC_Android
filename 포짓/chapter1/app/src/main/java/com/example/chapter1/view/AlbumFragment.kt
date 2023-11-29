package com.example.chapter1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.chapter1.adapter.AlbumViewpagerAdapter
import com.example.chapter1.databinding.FragmentAlbumBinding
import com.example.chapter1.model.Album
import com.example.chapter1.network.SongRepository
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    private val repository = SongRepository()

    //    private lateinit var songDB: SongDB
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val mainFragment = parentFragmentManager.findFragmentByTag("main")
            parentFragmentManager.beginTransaction()
                .show(mainFragment!!)
                .remove(this@AlbumFragment)
                .commit()
        }
    }
    private lateinit var album: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//        songDB = SongDB.getDB(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as MainActivity
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)


        binding.backBtn.setOnClickListener {
            val mainFragment = parentFragmentManager.findFragmentByTag("main")
            parentFragmentManager.beginTransaction()
                .show(mainFragment!!)
                .remove(this)
                .commit()
        }

        binding.albumLikeBtn.setOnClickListener {
//            if (album.isLike) {
//                binding.albumLikeBtn.setImageResource(R.drawable.ic_my_like_off)
//                album.isLike = false
//                CustomSnackBar.createSnackBar(it, "좋아요 한 엘범이 취소되었습니다.").show()
//                CoroutineScope(Dispatchers.IO).launch {
//                    songDB.albumDao().update(album)
//                }
//            } else {
//                album.isLike = true
//                binding.albumLikeBtn.setImageResource(R.drawable.ic_my_like_on)
//                CustomSnackBar.createSnackBar(it, "좋아요 한 앨범에 추가되었습니다..").show()
//                CoroutineScope(Dispatchers.IO).launch {
//                    songDB.albumDao().update(album)
//                }
//            }
        }


        binding.albumViewpager.adapter = AlbumViewpagerAdapter(this@AlbumFragment)
        val tabs = arrayOf("수록곡", "상세정보", "영상")
        TabLayoutMediator(binding.albumTab, binding.albumViewpager) { tab, position ->
            tab.text = tabs[position]
        }.attach()

        val albumId = arguments?.getInt("id")!!
//        CoroutineScope(Dispatchers.IO).launch {
//            val deferred = CoroutineScope(Dispatchers.IO).async {
//                songDB.albumDao().getAlbum(albumId)
//            }
//            album = deferred.await()
//            withContext(Dispatchers.Main) {
//                binding.albumTitle.text = album.title
//                binding.singerName.text = album.singer
//                binding.albumDetail.text = album.title
//                binding.albumImg.setImageResource(album.coverImg!!)
//                if (album.isLike) {
//                    binding.albumLikeBtn.setImageResource(R.drawable.ic_my_like_off)
//                }
//            }
//        }

        CoroutineScope(Dispatchers.IO).launch {
            val result = CoroutineScope(Dispatchers.IO).async {
                repository.getAllAlbums()
            }.await()
            if (result != null) {
                result.result.albums.forEach {
                    if (it.albumIdx == albumId) {
                        withContext(Dispatchers.Main) {
                            binding.albumTitle.text = it.title
                            binding.singerName.text = it.singer
                            binding.albumDetail.text = it.title
                            Glide.with(binding.albumImg).load(it.coverImgUrl).into(binding.albumImg)
                        }
                    }
                }
            }
        }
    }


}