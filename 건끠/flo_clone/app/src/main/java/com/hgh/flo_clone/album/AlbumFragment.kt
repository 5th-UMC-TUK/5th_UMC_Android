package com.hgh.flo_clone.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.hgh.flo_clone.R
import com.hgh.flo_clone.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding

    val args: AlbumFragmentArgs by navArgs()
    private val tabAdapter by lazy { TabAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAlbumBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.title = args.argAlbum.albumTitle

        binding.albumBackBtn.setOnClickListener {
            findNavController().navigate(R.id.action_albumFragment_to_homeFragment, null)
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