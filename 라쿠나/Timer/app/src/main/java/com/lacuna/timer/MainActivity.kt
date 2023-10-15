package com.lacuna.timer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lacuna.timer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var count = 0

        //시작 버튼
        binding.startBtn.setOnClickListener {
            val min = binding.minutesEdit.text.toString().toInt()
            val sec = binding.secondsEdit.text.toString().toInt()
            count ++
            if(count == 1) startTimer(min, sec)
            setPlayerStatus(true)
        }

        //정지 버튼
        binding.pauseBtn.setOnClickListener {
            setPlayerStatus(false)
        }

    }

    private fun setPlayerStatus(isPlaying : Boolean) {
        timer.isPlaying = isPlaying

        if(isPlaying) {
            binding.timeEditLinearLayout.visibility = View.GONE
            binding.timeTextViewLinearLayout.visibility = View.VISIBLE
        }
        else {
            binding.timeEditLinearLayout.visibility = View.VISIBLE
            binding.timeTextViewLinearLayout.visibility = View.GONE
        }
    }

    private fun startTimer(min: Int, sec: Int) {
        binding.timerTextView.text = String.format("%02d:%02d", min, sec)
        timer = Timer(60*min + sec, true)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true): Thread() {

        private var second: Int = 0
        private var mills: Float = 0f
        override fun run() {
            super.run()
            try {
                while (true){
                    if(second >= playTime){
                        break
                    }

                    if(isPlaying){
                        sleep(50)
                        mills += 50

                        //mills 1000당 1초임
                        if(mills % 1000 == 0f){
                            runOnUiThread {
                                binding.timerTextView.text = String.format("%02d:%02d", (playTime - second) / 60, (playTime - second) % 60)
                                binding.minutesEdit.setText(String.format("%02d", (playTime - second) / 60))
                                binding.secondsEdit.setText(String.format("%02d", (playTime - second) % 60))
                            }
                            second++
                        }
                    }

                }
            }catch (e: InterruptedException){
                Log.d("timer", "쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }
}