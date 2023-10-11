package com.example.ch07_mission

import android.content.Intent
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.ch07_mission.databinding.ActivityMainBinding
import java.lang.String.format

import java.lang.Thread.sleep
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var timer = 0
    private val soundPool = SoundPool.Builder().build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bellSoundId = soundPool.load(this, R.raw.timer_bell, 1)

        binding.startBtn.setOnClickListener {
            if (timer ==0) return@setOnClickListener
            binding.startBtn.isGone =true
            thread{
                while (timer >0){
                    sleep(1000)
                    timer -=1
                    runOnUiThread {
                          binding.min.text = format(getString(R.string.min),timer/60)
                          binding.sec.text = format(getString(R.string.sec),timer%60)
                    }
                }
                runOnUiThread {
                    timer =0
                    binding.sec.text = format(getString(R.string.sec),0)
                    binding.startBtn.isVisible =true
                    bellSoundId?.let { soundId ->
                        soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
                    }
                }
            }
        }
        binding.b10.setOnClickListener {
            timer +=10
            binding.min.text = format(getString(R.string.min),timer/60)
            binding.sec.text = format(getString(R.string.sec),timer%60)
        }
        binding.b30.setOnClickListener {
            timer +=30
            binding.min.text = format(getString(R.string.min),timer/60)
            binding.sec.text = format(getString(R.string.sec),timer%60)
        }
        binding.b60.setOnClickListener {
            timer +=60
            binding.min.text = format(getString(R.string.min),timer/60)
            binding.sec.text = format(getString(R.string.sec),timer%60)
        }
        binding.reset.setOnClickListener {
            timer =0
            binding.min.text = format(getString(R.string.min),timer/60)
            binding.sec.text = format(getString(R.string.sec),timer%60)
        }
        binding.MusicBtn.setOnClickListener {
            intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

}