package com.lacuna.chapter1.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lacuna.chapter1.databinding.FragmentAlbumIncludedSongsBinding

class AlbumIncludedSongsFragment : Fragment() {

    lateinit var binding: FragmentAlbumIncludedSongsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumIncludedSongsBinding.inflate(inflater, container, false)

        binding.songMixoffTg.setOnClickListener {
            setMixStatus(true)
        }
        binding.songMixonTg.setOnClickListener {
            setMixStatus(false)
        }

        return binding.root
    }

    fun setMixStatus(isMixing : Boolean) {
        if(isMixing) {
            binding.songMixonTg.visibility = View.VISIBLE
            binding.songMixoffTg.visibility = View.GONE
        }
        else {
            binding.songMixonTg.visibility = View.GONE
            binding.songMixoffTg.visibility = View.VISIBLE
        }
    }

    //    private fun initRecyclerView(result: ResultAlbum) {
//        albumAdapter = AlbumRVAdapter(requireContext(), result)
//        binding.homeTodayMusicRecyclerView.adapter = albumAdapter
//        albumAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
//            override fun onItemClick(album: Album) {
//                changeAlbumFragment(album)
//            }
//
//            override fun onPlayAlbum(album: Album) {
//                sendData(album)
//            }
//
//        })
//    }

//    private fun getAlbumsIn() {
//        val albumService = SongService()
//        albumService.setAlbumInView(this)
//        albumService.getAlbumsIn()
//
//    }
//    override fun onGetAlbumInSuccess(code: Int, result: ResultAlbumIn) {
////        initRecyclerView(result)
//
//        Log.d("ALBUM-FRAG/SUCCESS", result.toString())
//    }
//
//    override fun onGetAlbumInFailure(code: Int, message: String) {
//        Log.d("ALBUM-FRAG/FAILURE", message)
//    }

}