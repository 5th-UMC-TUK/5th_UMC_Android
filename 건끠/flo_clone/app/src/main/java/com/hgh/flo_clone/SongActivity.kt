package com.hgh.flo_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.ActivitySongBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
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
    }
}