package com.example.ch07_mission

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.SeekBar
import com.example.ch07_mission.databinding.ActivityMain2Binding
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class MainActivity2 : AppCompatActivity() {
    lateinit var binding :ActivityMain2Binding
    var player : MediaPlayer? =null
    var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser)
                    player?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        binding.playControlImageView.setOnClickListener {
            if(player==null) {
                player = MediaPlayer.create(this, R.raw.koreasong)
                binding.playerSeekBar.max = player!!.duration
            }
            if(!isPlaying) {
                isPlaying=true
                player?.start()
                var min = player!!.duration/60000
                var sec = (player!!.duration-60000*min)/1000
                binding.totalTimeTextView.text ="$min:$sec"
                binding.playControlImageView.setImageResource(R.drawable.baseline_pause_24)
                thread{
                    while (isPlaying){
                        runOnUiThread {
                            if (player==null) return@runOnUiThread
                            binding.playTimeTextView.text =
                                "${player!!.currentPosition/60000}:${ (player!!.currentPosition-60000*(player!!.currentPosition/60000))/1000 }"
                            binding.playerSeekBar.progress =player!!.currentPosition
                        }
                        sleep(1000)
                    }
                }
            }else{
                player?.pause()
                binding.playControlImageView.setImageResource(R.drawable.baseline_play_arrow_24)
                isPlaying=false
            }
        }
        binding.playStopImageVIew.setOnClickListener {
            player?.stop()
            player =null
            isPlaying = false
            binding.playControlImageView.setImageResource(R.drawable.baseline_play_arrow_24)
            binding.playerSeekBar.progress =0
            binding.playTimeTextView.text="0:0"
        }

    }

}