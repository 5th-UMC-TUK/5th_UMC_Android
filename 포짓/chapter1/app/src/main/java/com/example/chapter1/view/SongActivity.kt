package com.example.chapter1.view

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.example.chapter1.R
import com.example.chapter1.databinding.ActivitySongBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    private lateinit var mediaPlayer: MediaPlayer
    private val timeFormat = SimpleDateFormat("mm:ss")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.onBackPressedDispatcher.addCallback(this, callback)
        mediaPlayer = MediaPlayer.create(this, R.raw.music_lilac)     // 미디어 플레이어 객체 생성
        binding.songProgressbar.max = mediaPlayer.duration
        binding.fullPlayTime.text = timeFormat.format(mediaPlayer.duration)
        binding.currentPlayTime.text = "00:00"



        binding.arrowDown.setOnClickListener {
            val intent = Intent(this@SongActivity, MainActivity::class.java)
            intent.putExtra("song", binding.musicTitle.text.toString())
            intent.putExtra("singer", binding.signerName.text.toString())
            setResult(0, intent)
            finish()
        }

        binding.songMiniplayer.setOnClickListener {
            if(comparePlayPauseDrawable(binding.songMiniplayer, R.drawable.btn_miniplayer_play)) {
                binding.songMiniplayer.setImageResource(R.drawable.btn_miniplay_pause)
                mediaPlayer.start()
                CoroutineScope(Dispatchers.IO).launch {
                    while (mediaPlayer.isPlaying) {
                        withContext(Dispatchers.Main) {
                            binding.songProgressbar.progress = mediaPlayer.currentPosition  // seekBar에 현재 진행 상활 표현
                            binding.currentPlayTime.text = timeFormat.format(mediaPlayer.currentPosition)
                        }
                        SystemClock.sleep(200)
                    }
                }
            } else {
                binding.songMiniplayer.setImageResource(R.drawable.btn_miniplayer_play)
                mediaPlayer.pause()
            }
        }

        binding.songRandom.setOnClickListener {
            if(comparePlayPauseDrawable(binding.songRandom, R.drawable.nugu_btn_random_inactive)) {
                binding.songRandom.setImageResource(R.drawable.random_active)
            } else {
                binding.songRandom.setImageResource(R.drawable.nugu_btn_random_inactive)
            }
        }

        binding.songRepeat.setOnClickListener {
            if(comparePlayPauseDrawable(binding.songRepeat, R.drawable.nugu_btn_repeat_inactive)) {
                binding.songRepeat.setImageResource(R.drawable.repeat_active)
            } else {
                binding.songRepeat.setImageResource(R.drawable.nugu_btn_repeat_inactive)
            }
        }
    }

    private fun comparePlayPauseDrawable(image: ImageView, resId: Int): Boolean {
        // ImageButton의 현재 이미지를 가져옵니다.
        val imageDrawable = image.drawable as? BitmapDrawable
        if (imageDrawable == null) {
            // ImageButton에 비트맵 이미지가 아닌 다른 유형의 이미지가 있는 경우 처리
            return false
        }

        // Drawable 리소스에서 비트맵 이미지를 가져옵니다.
        val drawableBitmap = (ContextCompat.getDrawable(this@SongActivity, resId) as? BitmapDrawable)?.bitmap

        // 두 비트맵을 비교합니다.
        return imageDrawable.bitmap == drawableBitmap
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val intent = Intent(this@SongActivity, MainActivity::class.java)
            intent.putExtra("song", binding.musicTitle.text.toString())
            intent.putExtra("singer", binding.signerName.text.toString())
            setResult(0, intent)
            finish()

        }
    }
}