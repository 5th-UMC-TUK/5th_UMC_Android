package com.lacuna.chapter1

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.lacuna.chapter1.data.Song
import com.lacuna.chapter1.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer

    var isRepeat: Boolean = false
    var isShuffle: Boolean = false

    var title : String? = null
    var singer : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            title = intent.getStringExtra("title")
            singer = intent.getStringExtra("singer")
        }
        initSong()
        setPlayer(song)

        binding.songDownIb.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message", title + " _ " + singer)
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songRepeatIv.setOnClickListener {
            setRepeatStatus()
        }
        binding.songShuppleIv.setOnClickListener {
            setShuppleStatus()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }
    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                title,
                singer,
                intent.getIntExtra("second",0),
                intent.getIntExtra("playTime",0),
                intent.getBooleanExtra("isPlaying",false)
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = title
        binding.songSingerNameTv.text = singer
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        setPlayerStatus(song.isPlaying)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("message", "뒤로가기 버튼 클릭")

        setResult(RESULT_OK, intent)
        finish()
    }

    private fun setPlayerStatus(isPlaying : Boolean) {

        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
        else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }
    fun setRepeatStatus() {
        isRepeat = !isRepeat
        if (isRepeat) {
            binding.songRepeatIv.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#ff002080"))
        } else {
            binding.songRepeatIv.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#ff959595"))
        }
    }
    fun setShuppleStatus() {
        isRepeat = !isRepeat
        if (isRepeat) {
            binding.songShuppleIv.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#ff002080"))
        } else {
            binding.songShuppleIv.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#ff959595"))
        }
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

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }
                        //mills 1000당 1초임
                        if(mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }

                }
            }catch (e: InterruptedException){
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }


        }
    }

}