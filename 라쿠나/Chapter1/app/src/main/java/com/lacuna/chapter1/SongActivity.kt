package com.lacuna.chapter1

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.lacuna.chapter1.data.Song
import com.lacuna.chapter1.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    var isRepeat: Boolean = false
    var isShuffle: Boolean = false

    var title : String? = null
    var singer : String? = null
    var music: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("music")){
            title = intent.getStringExtra("title")
            singer = intent.getStringExtra("singer")
            music = intent.getStringExtra("music")
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
    // 사용자가 포커스를 잃었을 때 음악이 중지
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songProgressSb.progress * song.playTime) / 100) / 1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() // 에디터로 sharedpreference의 데이터를 조작

        // gson은 자바객체를 json으로, json을 자바객체로 변환시켜주는 것을 간단하게 해주는 객체
        val songJson = gson.toJson(song) // song객체를 json 포맷형식으로 변환
        editor.putString("songData", songJson) // songData는 sharedpreference안에 저장된 데이터의 이름
        editor.apply() // 실제 저장공간에 저장작업이 완료

    }
    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // 미디어플레이어가 갖고 있던 리소스 헤제
        mediaPlayer = null // 미디어 플레이어 헤제
        
    }
    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                title,
                singer,
                intent.getIntExtra("second",0),
                intent.getIntExtra("playTime",0),
                intent.getBooleanExtra("isPlaying",false),
//                intent.getStringExtra("music")!!
                music
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
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music) // MediaPlayer에게 어떤 음악을 재생할지 알려줌

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
            mediaPlayer?.start()
        }
        else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true) { // MediaPlayer는 재생중이 아닐 때 pause를 쓰면 오류가 발생하기 때문에 if문을 넣은 것임
                mediaPlayer?.pause()
            }
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