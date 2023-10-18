package com.example.ch04_mission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ch04_mission.databinding.ActivityCheckBinding

class CheckActivity : AppCompatActivity() {
    lateinit var binding : ActivityCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.memoText.text = intent.getStringExtra("memo")
    }
}