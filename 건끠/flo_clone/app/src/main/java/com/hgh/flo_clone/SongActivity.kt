package com.hgh.flo_clone

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.hgh.flo_clone.data.CurrentMusicModel
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.ActivitySongBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    var isPlaying: Boolean = false
    var isLike :Boolean = false
    var isRepeat: Boolean = false
    var isShuffle: Boolean = false
    val duration: Int = 200000
    var currentPosition: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = com.hgh.flo_clone.databinding.ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

         val music = intent.getParcelableExtra<CurrentMusicModel>("song")
        binding.songTitleText.text = music?.title ?: "null"
        binding.songSingerText.text = music?.singer ?: "null"
        isPlaying = music?.isPlaying ?: false
        currentPosition = music?.currentPosition ?: 0

        binding.songBackBtn.setOnClickListener {
            intent = Intent(this, MainActivity::class.java).apply {
                putExtra(
                    "return",
                    CurrentMusicModel(
                        title = binding.songTitleText.text.toString(),
                        singer = binding.songSingerText.text.toString(),
                        isPlaying = isPlaying,
                        currentPosition = currentPosition,
                    )
                )
            }
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.playerSeekBar.max = duration

        binding.playerSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    currentPosition = progress
                    binding.playTimeTextView.text = String.format(
                        "%d:%02d",
                        currentPosition / 60000,
                        (currentPosition - 60000 * (currentPosition / 60000)) / 1000
                    )
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        if (isPlaying) {
            binding.songPlayBtn.setImageResource(R.drawable.baseline_pause_24)
            CoroutineScope(Dispatchers.Main).launch {
                while (isPlaying) {
                    if (duration < currentPosition){
                      //  binding.songPlayBtn.setImageResource(R.drawable.baseline_play_arrow_24)
                        isPlaying = false
                        return@launch
                    }
                    delay(1000)
                    currentPosition += 1000
                    binding.playerSeekBar.progress = currentPosition
                    binding.playTimeTextView.text = String.format(
                        "%d:%02d",
                        currentPosition / 60000,
                        (currentPosition - 60000 * (currentPosition / 60000)) / 1000
                    )
                }
            }
        }else{
            binding.playerSeekBar.progress = currentPosition
            binding.songPlayBtn.setImageResource(R.drawable.baseline_play_arrow_24)
            binding.playTimeTextView.text = String.format(
                "%d:%02d",
                currentPosition / 60000,
                (currentPosition - 60000 * (currentPosition / 60000)) / 1000
            )
        }

        binding.songPlayBtn.setOnClickListener {
            isPlaying = !isPlaying
            if (isPlaying) {
                binding.songPlayBtn.setImageResource(R.drawable.baseline_pause_24)
                CoroutineScope(Dispatchers.Main).launch {
                    while (isPlaying) {
                        if (duration < currentPosition) {
                            binding.songPlayBtn.setImageResource(R.drawable.baseline_play_arrow_24)
                            isPlaying=false
                            return@launch
                        }
                        delay(1000)
                        currentPosition += 1000
                        binding.playerSeekBar.progress = currentPosition
                        binding.playTimeTextView.text = String.format(
                            "%d:%02d",
                            currentPosition / 60000,
                            (currentPosition - 60000 * (currentPosition / 60000)) / 1000
                        )
                    }
                }

            } else {
                binding.songPlayBtn.setImageResource(R.drawable.baseline_play_arrow_24)
            }
        }
        binding.songShuffleBtn.setOnClickListener {
            isShuffle = !isShuffle
            if (isShuffle) {
                binding.songShuffleBtn.imageTintList =
                    ColorStateList.valueOf(Color.parseColor("#ff3148CA"))
            } else {
                binding.songShuffleBtn.imageTintList =
                    ColorStateList.valueOf(Color.parseColor("#ff959595"))
            }
        }

        binding.songRepeatBtn.setOnClickListener {
            isRepeat = !isRepeat
            if (isRepeat) {
                binding.songRepeatBtn.imageTintList =
                    ColorStateList.valueOf(Color.parseColor("#ff3148CA"))
            } else {
                binding.songRepeatBtn.imageTintList =
                    ColorStateList.valueOf(Color.parseColor("#ff959595"))
            }
        }

    }
}