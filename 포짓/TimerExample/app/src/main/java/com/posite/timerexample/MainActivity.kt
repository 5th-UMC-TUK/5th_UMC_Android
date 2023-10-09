package com.posite.timerexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.posite.timerexample.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var play = false
    private var timer = TimerThread()
    private var currentTime = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.playBtn.setOnClickListener {
            if(play) {
                binding.playBtn.text = "시작"
                play = false
                timer.interrupt()
            } else {
                timer = TimerThread()
                binding.playBtn.text = "정지"
                play = true
                timer.start()
            }
        }
    }
    inner class TimerThread : Thread() {
        override fun run()  {
            while(currentTime<30){
               try{
                   currentTime+=1
                   runOnUiThread{
                       binding.timerTv.text = currentTime.toString()
                   }
                   sleep(1000)
               } catch(e: InterruptedException) {
                  return
               }
            }
            currentTime = 0
        }
    }
}