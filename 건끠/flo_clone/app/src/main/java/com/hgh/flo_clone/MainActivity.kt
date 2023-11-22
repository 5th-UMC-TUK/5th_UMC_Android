package com.hgh.flo_clone

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.hgh.flo_clone.data.CurrentMusicModel
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.ActivityMainBinding
import com.hgh.flo_clone.locker.SongLikeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var isPlaying = false
    var currentPosition = 0
    val duration = 200000
    var likeAlbum = mutableListOf<Int>()

    private val songIntentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val resultMusic = result.data?.getParcelableExtra<CurrentMusicModel>("return")
                Toast.makeText(this, "${resultMusic?.title ?: " "}", Toast.LENGTH_LONG).show()
                isPlaying = resultMusic!!.isPlaying
                currentPosition =resultMusic!!.currentPosition
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bottomNav
        NavigationUI.setupWithNavController(
            binding.mainBottomNav,
            findNavController(R.id.nav_host)
        )

        //SongActivity 전환
        binding.nowPlayListLayout.setOnClickListener {
            intent = Intent(this, SongActivity::class.java).apply {
                putExtra(
                    "song",
                    CurrentMusicModel(
                        title = binding.nowPlayTitle.text.toString(),
                        singer = binding.nowPlaySinger.text.toString(),
                        isPlaying = isPlaying,
                        currentPosition = currentPosition
                    )
                )
            }
            songIntentLauncher.launch(intent)
            isPlaying = false
           }

        //miniPlayer
        binding.miniPlayerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    currentPosition = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        binding.miniPlayerSeekBar.max = duration
        binding.playBtn.setOnClickListener {
            isPlaying = !isPlaying
            if (isPlaying) {
                binding.playBtn.setImageResource(R.drawable.baseline_pause_24)
                CoroutineScope(Dispatchers.Main).launch {
                    while (isPlaying) {
                        if (duration < currentPosition) return@launch
                        delay(1000)
                        currentPosition += 1000
                        binding.miniPlayerSeekBar.progress = currentPosition
                    }
                }

            } else {
                binding.playBtn.setImageResource(R.drawable.baseline_play_arrow_24)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isPlaying) {
            binding.playBtn.setImageResource(R.drawable.baseline_pause_24)
            CoroutineScope(Dispatchers.Main).launch {
                while (isPlaying) {
                    if (duration < currentPosition) return@launch
                    delay(1000)
                    currentPosition += 1000
                    binding.miniPlayerSeekBar.progress = currentPosition
                }
            }
        }else{
            binding.miniPlayerSeekBar.progress = currentPosition
            binding.playBtn.setImageResource(R.drawable.baseline_play_arrow_24)
        }
    }

    fun likeSongFragment(count: Int){
        binding.groupLikeSong.isVisible =true
        binding.groupLikeSongNot.isInvisible =true
        binding.count.text = count.toString()
    }

    fun likeSongFragmentNot(){
        binding.groupLikeSong.isInvisible =true
        binding.groupLikeSongNot.isVisible =true
    }

}