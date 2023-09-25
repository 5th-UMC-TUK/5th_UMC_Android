package com.example.chapter1.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chapter1.R
import com.example.chapter1.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.arrowDown.setOnClickListener {
            val intent = Intent(this@SongActivity, MainActivity::class.java)
            intent.putExtra("song", binding.musicTitle.text.toString())
            intent.putExtra("singer", binding.signerName.text.toString())
            setResult(0, intent)
            finish()
        }
    }
}