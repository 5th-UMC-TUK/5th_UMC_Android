package com.hgh.flo_clone.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.hgh.flo_clone.MainActivity
import com.hgh.flo_clone.R
import com.hgh.flo_clone.databinding.FragmentAlbumBinding
import com.hgh.flo_clone.server.network.ApiService
import com.hgh.flo_clone.server.repository.ApiRepository
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding

    val args: AlbumFragmentArgs by navArgs()
    private val tabAdapter by lazy { TabAdapter(this) }
    val apiService by inject<ApiRepository>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAlbumBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.title =""
       // binding.toolBar.title = args.argAlbum.albumTitle
        lifecycleScope.launch {
            val albumDetail= apiService.getAlbumList()!!.filter {
                it.albumIdx == args.argAlbum
            }.first()
            binding.toolBar.title = albumDetail.title
            binding.albumTitleT.text = albumDetail.title
            binding.albumSingerT.text = albumDetail.singer
            Glide.with(requireActivity())
                .load(albumDetail.coverImgUrl)
                .error(R.drawable.appicon_image)
                .fallback(R.drawable.appicon_image)
                .into(binding.albumSumNail)

            binding.likeBtn.setImageResource(if (args.argAlbum in (requireActivity() as MainActivity).likeAlbum ) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
        }
        binding.albumBackBtn.setOnClickListener {
            findNavController().navigate(R.id.action_albumFragment_to_homeFragment, null)
        }

        binding.likeBtn.setOnClickListener {
            if (args.argAlbum in (requireActivity() as MainActivity).likeAlbum ){
                (requireActivity() as MainActivity).likeAlbum.remove(args.argAlbum)
                binding.likeBtn.setImageResource(R.drawable.baseline_favorite_border_24)
            }else{
                (requireActivity() as MainActivity).likeAlbum.add(args.argAlbum)
                binding.likeBtn.setImageResource(R.drawable.baseline_favorite_24)
            }
        }
        binding.viewPager.adapter = tabAdapter


        val tabTitleArray = arrayOf(
            "수록곡",
            "상세정보",
            "영상"
        )

        TabLayoutMediator(binding.layoutTab, binding.viewPager) { tab, position ->
            tab.text = tabTitleArray[position]

        }.attach()

    }

}