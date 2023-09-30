package com.hgh.flo_clone

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    var isPlaying :Boolean = false
    var isRepeat : Boolean =false
    var isShuffle : Boolean =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = com.hgh.flo_clone.databinding.ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val music = intent.getParcelableExtra<MusicModel>("song")
        binding.songTitleText.text = music?.title ?:"null"
        binding.songSingerText.text = music?.singer ?:"null"

        binding.songBackBtn.setOnClickListener {
            intent= Intent(this, MainActivity::class.java).apply {
                putExtra("return", music)
            }
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.songPlayBtn.setOnClickListener {
            isPlaying =!isPlaying
            if (isPlaying){
                binding.songPlayBtn.setImageResource(R.drawable.baseline_pause_24)
            }else{
                binding.songPlayBtn.setImageResource(R.drawable.baseline_play_arrow_24)
            }
        }
        binding.songShuffleBtn.setOnClickListener {
            isShuffle = !isShuffle
            if (isShuffle){
                binding.songShuffleBtn.imageTintList = ColorStateList.valueOf(Color.parseColor("#ff3148CA"))
            }else{
                binding.songShuffleBtn.imageTintList = ColorStateList.valueOf(Color.parseColor("#ff959595"))
            }
        }

        binding.songRepeatBtn.setOnClickListener {
            isRepeat = !isRepeat
            if (isRepeat){
                binding.songRepeatBtn.imageTintList = ColorStateList.valueOf(Color.parseColor("#ff3148CA"))
            }else{
                binding.songRepeatBtn.imageTintList = ColorStateList.valueOf(Color.parseColor("#ff959595"))
            }
        }
    }
}