package com.hgh.flo_clone

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hgh.flo_clone.data.HomeBannerModel
import com.hgh.flo_clone.databinding.FragmentAudioBinding
import com.hgh.flo_clone.databinding.FragmentHomeBannerBinding

class HomeBannerFragment: Fragment() {

    lateinit var binding: FragmentHomeBannerBinding

    private val bannerData by lazy<HomeBannerModel> { arguments?.getParcelable(BANNER_DATA_KEY)!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBannerBinding.inflate(inflater,container,false)
        .also { binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recommendTitle.text = bannerData.title
        binding.musicTitle1.text = bannerData.songTitle1
        binding.musicSinger1.text = bannerData.songSinger1
        binding.musicTitle2.text = bannerData.songTitle2
        binding.musicSinger2.text =bannerData.songSinger2
        binding.recommendLayout.setBackgroundColor(ContextCompat.getColor(requireContext(),bannerData.color))
    }

    companion object {
        const val BANNER_KEY = "Banner"
        const val BANNER_DATA_KEY = "BannerData"

        fun newInstance( bannerData: HomeBannerModel): HomeBannerFragment {
            val bundle = Bundle().apply {
                putParcelable(BANNER_DATA_KEY, bannerData)
            }

            return HomeBannerFragment().apply {
                arguments = bundle
            }
        }

    }

}