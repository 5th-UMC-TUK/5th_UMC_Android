package com.example.chapter1

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.chapter1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.btn_main_ticket)

        window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }

        val titleList = listOf(TitleModel("포근하게 덮어주는 꿈의\n목소리","총 15곡 2019.11.11", R.drawable.img_first_album_default,
            listOf(SongModel("잠이 안온다", "젠(Zen)", R.drawable.img_album_exp))),
            TitleModel("포근하게 덮어주는 꿈의\n목소리","총 15곡 2019.11.11", R.drawable.img_first_album_default,
                listOf(SongModel("잠이 안온다", "젠(Zen)", R.drawable.img_album_exp))),
            TitleModel("포근하게 덮어주는 꿈의\n목소리","총 15곡 2019.11.11", R.drawable.img_first_album_default,
                listOf(SongModel("잠이 안온다", "젠(Zen)", R.drawable.img_album_exp))))

        val titleAdapter = TitleAdapter()
        binding.titleViewpager.adapter = titleAdapter
        binding.titleViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        titleAdapter.submitList(titleList)

        binding.titleIndicator.setViewPager(binding.titleViewpager)

//        val adImages = listOf(R.drawable.img_home_viewpager_exp, R.drawable.img_home_viewpager_exp2)
//        val adAdapter = AdAdapter()
//        binding.adViewpager.adapter = adAdapter
//        adAdapter.submitList(adImages)

        val todaySongArray = listOf(TodaySongModel("사이렌", "marsjay (마스제이)", R.drawable.siren),
            TodaySongModel("Got Me Started", "Troye Sivan", R.drawable.got_me_started),
            TodaySongModel("소품집 Vol.1", "장동원", R.drawable.vol1),
            TodaySongModel("The Wave", "다크비 (DKB)", R.drawable.the_wave))

        val todaySongAdapter = TodaySongAdapter()
        binding.todaySongRv.adapter = todaySongAdapter
        todaySongAdapter.submitList(todaySongArray)

        binding.todaySongTotal.setOnClickListener{
            binding.todaySongTotal.setTextColor(ContextCompat.getColor(this, R.color.flo_blue))
            binding.todaySongInner.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.todaySongOuter.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
        binding.todaySongInner.setOnClickListener {
            binding.todaySongTotal.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.todaySongInner.setTextColor(ContextCompat.getColor(this, R.color.flo_blue))
            binding.todaySongOuter.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
        binding.todaySongOuter.setOnClickListener {
            binding.todaySongTotal.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.todaySongInner.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.todaySongOuter.setTextColor(ContextCompat.getColor(this, R.color.flo_blue))
        }

        val everydaySongArray = listOf(TodaySongModel("제목", "가수", R.drawable.img_potcast_exp),
            TodaySongModel("제목", "가수", R.drawable.img_potcast_exp),
            TodaySongModel("제목", "가수", R.drawable.img_potcast_exp),
            TodaySongModel("제목", "가수", R.drawable.img_potcast_exp))
        val everydaySongAdapter = TodaySongAdapter()
        binding.everydaySongRv.adapter = everydaySongAdapter
        everydaySongAdapter.submitList(everydaySongArray)

        val videoArray = listOf(TodaySongModel("제목", "가수", R.drawable.img_video_exp),
            TodaySongModel("제목", "가수", R.drawable.img_video_exp),
            TodaySongModel("제목", "가수", R.drawable.img_video_exp),
            TodaySongModel("제목", "가수", R.drawable.img_video_exp))
        val videoAdapter = VideoAdapter()
        binding.videoRv.adapter = videoAdapter
        videoAdapter.submitList(videoArray)

        binding.imgPlalistStart.setOnClickListener {
            val imageButtonDrawable = binding.imgPlalistStart.drawable as? BitmapDrawable
            val playDrawable = ContextCompat.getDrawable(this, R.drawable.btn_miniplayer_play) as? BitmapDrawable
            if(playDrawable != null && imageButtonDrawable != null && playDrawable.bitmap == imageButtonDrawable.bitmap) {
                binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplay_pause)
            } else {
                binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplayer_play)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {

            }
            R.id.main_mic -> {

            }
            R.id.main_notice -> {

            }
            R.id.main_settings -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}