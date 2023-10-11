package com.lacuna.chapter1

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lacuna.chapter1.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    var isRepeat: Boolean = false
    var isShuffle: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var title : String? = null
        var singer : String? = null

        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            title = intent.getStringExtra("title")
            singer = intent.getStringExtra("singer")
            binding.songMusicTitleTv.text = title
            binding.songSingerNameTv.text = singer
        }

        binding.songDownIb.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message", title + " _ " + singer)

            setResult(RESULT_OK, intent)
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songRepeatIv.setOnClickListener {
            setRepeatStatus()
        }
        binding.songShuppleIv.setOnClickListener {
            setShuppleStatus()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("message", "뒤로가기 버튼 클릭")

        setResult(RESULT_OK, intent)
        finish()
    }

    fun setPlayerStatus(isPlaying : Boolean) {
        if(isPlaying) {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
        else {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }

    fun setRepeatStatus() {
        isRepeat = !isRepeat
        if (isRepeat) {
            binding.songRepeatIv.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#ff002080"))
        } else {
            binding.songRepeatIv.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#ff959595"))
        }
    }
    fun setShuppleStatus() {
        isRepeat = !isRepeat
        if (isRepeat) {
            binding.songShuppleIv.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#ff002080"))
        } else {
            binding.songShuppleIv.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#ff959595"))
        }
    }
}