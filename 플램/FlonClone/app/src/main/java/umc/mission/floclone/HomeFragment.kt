package umc.mission.floclone

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.mission.floclone.NewMusicDailyAdapter.Companion.NEW_MUSIC_DAILY
import umc.mission.floclone.NewMusicDailyAdapter.Companion.VIDEO_COLLECTION
import umc.mission.floclone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var newMusicDailyAdapter: NewMusicDailyAdapter
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var newMusicDailyList: MutableList<NewMusicDaily>
    private lateinit var podcastList: MutableList<NewMusicDaily>
    private lateinit var videoCollectionList: MutableList<NewMusicDaily>
    private lateinit var homeViewPager1List: MutableList<Drawable>
    private lateinit var homeViewPager2List: MutableList<Drawable>
    private var binding: FragmentHomeBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        initViewPager1()
        //checkNewMusicDailyCategory(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initRecyclerView(view: View){
        val layoutManager1 = LinearLayoutManager(context)
        layoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        val newMusicDailyRecyclerView = view.findViewById<RecyclerView>(R.id.home_new_music_daily_recyclerview)
        newMusicDailyRecyclerView.layoutManager = layoutManager1
        newMusicDailyAdapter = NewMusicDailyAdapter(makeNewMusicDailyData(), NEW_MUSIC_DAILY)
        newMusicDailyRecyclerView.adapter = newMusicDailyAdapter

        val layoutManager2 = LinearLayoutManager(context)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        val podcastRecyclerView = view.findViewById<RecyclerView>(R.id.home_podcast_recyclerview)
        podcastRecyclerView.layoutManager = layoutManager2
        podcastRecyclerView.adapter = NewMusicDailyAdapter(makeNewPodcastData(), NEW_MUSIC_DAILY)

        val layoutManager3 = LinearLayoutManager(context)
        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL
        val videoCollectionRecyclerView = view.findViewById<RecyclerView>(R.id.home_video_collection_recyclerview)
        videoCollectionRecyclerView.layoutManager = layoutManager3
        videoCollectionRecyclerView.adapter = NewMusicDailyAdapter(makeVideoCollectionData(), VIDEO_COLLECTION)
    }

    private fun makeNewMusicDailyData(): MutableList<NewMusicDaily>{
        val activity = context as Activity
        newMusicDailyList = mutableListOf<NewMusicDaily>()
        newMusicDailyList.apply {
            add(NewMusicDaily("Next Level", "aespa", ContextCompat.getDrawable(activity, R.drawable.img_album_exp3)))
            add(NewMusicDaily("작은 것들을 위한 시", "방탄소년단", ContextCompat.getDrawable(activity, R.drawable.img_album_exp4)))
            add(NewMusicDaily("BAAM", "모모랜드 (MOMOLAND)", ContextCompat.getDrawable(activity, R.drawable.img_album_exp5)))
            add(NewMusicDaily("Weekend", "태연", ContextCompat.getDrawable(activity, R.drawable.img_album_exp6)))
        }
        return newMusicDailyList
    }

    private fun makeNewPodcastData(): MutableList<NewMusicDaily> {
        val activity = context as Activity
        podcastList = mutableListOf<NewMusicDaily>()
        podcastList.apply {
            add(NewMusicDaily("제목", "가수", ContextCompat.getDrawable(activity, R.drawable.img_potcast_exp)))
            add(NewMusicDaily("제목", "가수", ContextCompat.getDrawable(activity, R.drawable.img_potcast_exp)))
            add(NewMusicDaily("제목", "가수", ContextCompat.getDrawable(activity, R.drawable.img_potcast_exp)))
            add(NewMusicDaily("제목", "가수", ContextCompat.getDrawable(activity, R.drawable.img_potcast_exp)))
        }
        return podcastList
    }

    private fun makeVideoCollectionData(): MutableList<NewMusicDaily> {
        val activity = context as Activity
        videoCollectionList = mutableListOf<NewMusicDaily>()
        videoCollectionList.apply {
            add(NewMusicDaily("제목", "가수", ContextCompat.getDrawable(activity, R.drawable.img_video_exp)))
            add(NewMusicDaily("제목", "가수", ContextCompat.getDrawable(activity, R.drawable.img_video_exp)))
            add(NewMusicDaily("제목", "가수", ContextCompat.getDrawable(activity, R.drawable.img_video_exp)))
            add(NewMusicDaily("제목", "가수", ContextCompat.getDrawable(activity, R.drawable.img_video_exp)))
        }
        return videoCollectionList
    }

    private fun initViewPager1(){
        val activity = context as Activity
        homeViewPager1List = mutableListOf<Drawable>()
        homeViewPager1List.apply {
            ContextCompat.getDrawable(activity, R.drawable.img_home_viewpager_exp)?.let { add(it) }
            ContextCompat.getDrawable(activity, R.drawable.img_home_viewpager_exp)?.let { add(it) }
            ContextCompat.getDrawable(activity, R.drawable.img_home_viewpager_exp)?.let { add(it) }
        }
        homeViewPagerAdapter = HomeViewPagerAdapter(homeViewPager1List)
        binding?.homeAdViewpager1?.adapter = homeViewPagerAdapter

        homeViewPager2List = mutableListOf<Drawable>()
        homeViewPager2List.apply {
            ContextCompat.getDrawable(activity, R.drawable.img_home_viewpager_exp2)?.let { add(it) }
            ContextCompat.getDrawable(activity, R.drawable.img_home_viewpager_exp2)?.let { add(it) }
            ContextCompat.getDrawable(activity, R.drawable.img_home_viewpager_exp2)?.let { add(it) }
        }
        homeViewPagerAdapter = HomeViewPagerAdapter(homeViewPager2List)
        binding?.homeAdViewpager2?.adapter = homeViewPagerAdapter
    }
}