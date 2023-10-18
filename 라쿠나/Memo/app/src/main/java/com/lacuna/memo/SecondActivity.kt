package com.lacuna.memo

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.lacuna.memo.databinding.ActivitySecondBinding

class SecondActivity: AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val data = intent.getStringExtra("data")

        binding.memoTitle.text = title
        binding.memoContent.text = data

        binding.saveBtn.setOnClickListener {
            finish()
        }
    }
}