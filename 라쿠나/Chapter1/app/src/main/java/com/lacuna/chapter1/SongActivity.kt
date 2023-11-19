package com.lacuna.chapter1

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.lacuna.chapter1.data.Song
import com.lacuna.chapter1.data.SongDatabase
import com.lacuna.chapter1.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    var isRepeat: Boolean = false
    var isShuffle: Boolean = false

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList() // SongDB에 있는 모든 노래를 songs에 저장
        initSong()
        initClickListener()

    }

    // 사용자가 포커스를 잃었을 때 음악이 중지
    override fun onPause() {
        super.onPause()
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime) / 100) / 1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() // 에디터로 sharedpreference의 데이터를 조작

        editor.putInt("songId", songs[nowPos].id) // song자체를 저장하는게 아니라 songId값을 저장
        editor.apply() // 실제 저장공간에 저장작업이 완료

    }
    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // 미디어플레이어가 갖고 있던 리소스 헤제
        mediaPlayer = null // 미디어 플레이어 헤제
        
    }

    private fun initPlayList(){ // 해당 데이터베이스에 있는 songList를 뽑아와서 songs에 저장
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener() {
        binding.songDownIb.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message", songs[nowPos].title + "_" + songs[nowPos].singer)
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
        binding.songNextIv.setOnClickListener {
            moveSong(+1)
        }
        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }
        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }

    private fun initSong(){
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)
        Log.d("now Song ID", songs[nowPos].id.toString())

        startTimer()
        setPlayer(songs[nowPos])
    }
    // songId로 position을 얻는 메서드
    private fun getPlayingSongPosition(songId: Int): Int{
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }
    private fun moveSong(direct: Int){
        if (nowPos + direct < 0){
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size){
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }
        nowPos += direct

        timer.interrupt() // 타이머 멈춤
        startTimer() // 다시 시작

        mediaPlayer?.release() // 미디어플레이어 해지시킴
        mediaPlayer = null

        setPlayer(songs[nowPos]) // 데이터 렌더링 다시
    }
    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike, songs[nowPos].id)

        if (!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

    }
    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music) // MediaPlayer에게 어떤 음악을 재생할지 알려줌

        if(song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
        setPlayerStatus(song.isPlaying)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("message", "뒤로가기 버튼 클릭")

        setResult(RESULT_OK, intent)
        finish()
    }

    private fun setPlayerStatus(isPlaying : Boolean) {
        songs[nowPos].isPlaying = isPlaying
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
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }
    private fun setRepeatStatus() {
        isRepeat = !isRepeat
        if (isRepeat) {
            binding.songRepeatIv.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#ff002080"))
        } else {
            binding.songRepeatIv.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#ff959595"))
        }
    }
    private fun setShuppleStatus() {
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