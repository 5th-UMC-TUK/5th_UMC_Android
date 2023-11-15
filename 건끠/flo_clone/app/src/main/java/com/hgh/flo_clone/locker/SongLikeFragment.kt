package com.hgh.flo_clone.locker

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hgh.flo_clone.MainActivity
import com.hgh.flo_clone.adapter.LikeMusicRVAdapter
import com.hgh.flo_clone.adapter.SaveMusicRVAdapter
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.FragmentSongFileBinding
import com.hgh.flo_clone.databinding.FragmentSongLikeBinding

class SongLikeFragment : Fragment(){
    lateinit var binding: FragmentSongLikeBinding

    val adapter by lazy { LikeMusicRVAdapter() }
    val liveData = MutableLiveData<List<MusicModel>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSongLikeBinding.inflate(inflater,container,false)
        .also {binding = it}.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dummyDataList = dummyData()
        binding.saveSongRV.adapter = adapter
        adapter.setList(dummyDataList)
        adapter.onItemSelectionChangedListener={
            liveData.value = it
        }
        binding.includeAllClickBtn.setOnClickListener{
            adapter.selectionList = dummyDataList.toMutableList()
            adapter.notifyDataSetChanged()
            liveData.value = dummyDataList
        }

        liveData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                (requireActivity() as MainActivity).likeSongFragment(it.size)
                binding.includeAllClickBtn.setTextColor(Color.parseColor("#3148CA"))
            }else{
                (requireActivity() as MainActivity).likeSongFragmentNot()
                binding.includeAllClickBtn.setTextColor(Color.parseColor("#959595"))
            }
        }

        (requireActivity() as MainActivity).binding.likeDestoryBtn.setOnClickListener {
            dummyDataList.removeAll(liveData.value!!)
            adapter.selectionList = mutableListOf()
            liveData.value = mutableListOf()
            adapter.notifyDataSetChanged()
        }

    }

    fun dummyData(): MutableList<MusicModel> {
        return mutableListOf(
            MusicModel("Attention", "NewJens"),
            MusicModel("Hype boy", "NewJens"),
            MusicModel("Cookie", "NewJens"),
            MusicModel("ETA", "NewJens")
        )
    }
}