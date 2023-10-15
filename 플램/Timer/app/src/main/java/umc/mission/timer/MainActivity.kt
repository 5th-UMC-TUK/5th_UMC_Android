package umc.mission.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker.OnValueChangeListener
import androidx.core.view.isVisible
import umc.mission.timer.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var timer: Timer? = null
    private var currentTime = 0
    private var timerTime = 0
    private var hour = 0
    private var minute = 0
    private var second = 0
    private var toggle = false
    private var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        startTimer()
        pauseTimer()
        cancelTimer()
        runThreadExample()
    }

    private fun runThreadExample() {
        val a = A()
        val b = B()
        a.start()
        b.start()
    }

    private fun updateView() {
        binding.apply {
            startFab.isVisible = !toggle
            cancelFab.isEnabled = toggle
            pauseFab.isVisible = toggle
            timerSetLayout.isVisible = !toggle
            timerCircularProgressbar.isVisible = toggle
            timerTv.isVisible = toggle
        }
    }

    private fun initView() {
        binding.setTimerHourNumberPicker.apply {
            maxValue = 23
            minValue = 0
            value = 0
            wrapSelectorWheel = false
        }
        binding.setTimerMinuteNumberPicker.apply {
            maxValue = 59
            minValue = 0
            value = 0
            wrapSelectorWheel = false
        }
        binding.setTimerSecondNumberPicker.apply {
            maxValue = 59
            minValue = 0
            value = 0
            wrapSelectorWheel = false
        }
        val listener = OnValueChangeListener { _, _, _ ->
            hour = binding.setTimerHourNumberPicker.value
            minute = binding.setTimerMinuteNumberPicker.value
            second = binding.setTimerSecondNumberPicker.value
            binding.startFab.isEnabled = !(hour == 0 && minute == 0 && second == 0)
        }
        binding.setTimerHourNumberPicker.setOnValueChangedListener(listener)
        binding.setTimerMinuteNumberPicker.setOnValueChangedListener(listener)
        binding.setTimerSecondNumberPicker.setOnValueChangedListener(listener)
    }

    private fun startTimer() {
        binding.startFab.setOnClickListener {
            toggle = true
            updateView()
            updateTimerView(hour, minute, second)
            if (!isPaused) {
                timerTime = (hour * 60 * 60) + (minute * 60) + second
                currentTime = timerTime
            }
            binding.timerCircularProgressbar.max = timerTime
            timer = timer(initialDelay = 0, period = 1000) {
                currentTime -= 1
                val hourText = currentTime.div(3600)
                val minuteText = currentTime % 3600 / 60
                val secondText = currentTime % 3600 % 60
                runOnUiThread {
                    updateTimerView(hourText, minuteText, secondText)
                    binding.timerCircularProgressbar.progress =
                        binding.timerCircularProgressbar.max - currentTime
                    Log.d("time", "$currentTime")
                }
            }
        }
    }

    private fun updateTimerView(hour: Int?, minute:Int, second:Int){
        binding.apply {
            timerTv.text = if (hour == 0) {
                String.format(
                    "%02d:%02d", minute, second
                )
            } else {
                String.format(
                    "%d:%02d:%02d", hour, minute, second
                )
            }
        }
    }

    private fun pauseTimer() {
        binding.pauseFab.setOnClickListener {
            binding.startFab.isVisible = true
            binding.pauseFab.isVisible = false
            timer?.cancel()
            timer = null
            isPaused = true
        }
    }

    private fun cancelTimer() {
        binding.cancelFab.setOnClickListener {
            toggle = false
            updateView()
            timer?.cancel()
            timer = null
            isPaused = false
        }
    }

    class A : Thread() {
        override fun run() {
            super.run()
            for (i in 1..1000) {
                Log.d("test", "first: $i")
            }
        }
    }

    class B : Thread() {
        override fun run() {
            super.run()
            for (i in 1000 downTo 1) {
                Log.d("test", "second: $i")
            }
        }
    }
}