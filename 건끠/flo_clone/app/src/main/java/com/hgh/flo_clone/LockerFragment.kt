package com.hgh.flo_clone

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.hgh.flo_clone.databinding.FragmentHomeBinding
import com.hgh.flo_clone.databinding.FragmentLockerBinding
import com.hgh.flo_clone.locker.TabLockerAdapter
import com.hgh.flo_clone.util.getJwt
import com.hgh.flo_clone.util.saveJwt

class LockerFragment : Fragment() {

    private val tabAdapter by lazy { TabLockerAdapter(this) }

    lateinit var binding: FragmentLockerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLockerBinding.inflate(inflater,container,false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = tabAdapter
        if (getJwt() !=""){
            binding.loginBtn.isVisible= true
        }else{
            binding.loginBtn.isGone =true
        }
        binding.loginBtn.setOnClickListener {
            saveJwt("")
            startActivity(Intent(requireContext(),LoginActivity::class.java))
            (requireActivity() as MainActivity).finish()
        }

        val tabTitleArray = arrayOf(
            "저장한 곡",
            "음악파일",
            "❤︎좋아요"
        )

        TabLayoutMediator(binding.layoutTab, binding.viewPager) { tab, position ->
            tab.text = tabTitleArray[position]

        }.attach()

    }
}