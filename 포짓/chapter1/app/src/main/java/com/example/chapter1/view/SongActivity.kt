package com.example.chapter1.view

import android.graphics.drawable.BitmapDrawable
import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.chapter1.R
import com.example.chapter1.databinding.ActivitySongBinding
import com.example.chapter1.db.Song
import com.example.chapter1.db.SongDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    private var mediaPlayer: MediaPlayer? = null
    private val timeFormat = SimpleDateFormat("mm:ss")
    private lateinit var job: Job
    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDB
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.onBackPressedDispatcher.addCallback(this, callback)

        CoroutineScope(Dispatchers.IO).launch {
            initPlayList()
            initSong()
        }

        binding.arrowDown.setOnClickListener {
            songs[nowPos].second = mediaPlayer!!.currentPosition
            Log.d("songinfofinishsecond", songs[nowPos].second.toString())
            CoroutineScope(Dispatchers.IO).launch {
                val dao = songDB.songDao()
                dao.update(songs[nowPos])
            }
            finish()
        }
        binding.songProgressbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (!this@SongActivity.isFinishing) {
                    seekBar?.let {
                        mediaPlayer!!.seekTo(it.progress)
                        binding.currentPlayTime.text =
                            timeFormat.format(mediaPlayer!!.currentPosition)
                    }
                }
            }

        })
        binding.songMiniplayer.setOnClickListener {
            if (comparePlayPauseDrawable(binding.songMiniplayer, R.drawable.btn_miniplayer_play)) {
                binding.songMiniplayer.setImageResource(R.drawable.btn_miniplay_pause)
                mediaPlayer!!.start()
                job = CoroutineScope(Dispatchers.IO).launch {
                    while (!this@SongActivity.isFinishing && mediaPlayer!!.isPlaying) {
                        withContext(Dispatchers.Main) {
                            binding.songProgressbar.progress =
                                mediaPlayer!!.currentPosition  // seekBar에 현재 진행 상활 표현
                            binding.currentPlayTime.text =
                                timeFormat.format(mediaPlayer!!.currentPosition)
                        }
                        SystemClock.sleep(200)
                    }
                }
                job.start()
            } else {
                binding.songMiniplayer.setImageResource(R.drawable.btn_miniplayer_play)
                job.cancel()
                mediaPlayer!!.pause()
            }
        }

        binding.songPrevious.setOnClickListener {
            moveSong(-1)
        }

        binding.songNext.setOnClickListener {
            moveSong(+1)
        }

        binding.songRandom.setOnClickListener {
            if (comparePlayPauseDrawable(binding.songRandom, R.drawable.nugu_btn_random_inactive)) {
                binding.songRandom.setImageResource(R.drawable.random_active)
            } else {
                binding.songRandom.setImageResource(R.drawable.nugu_btn_random_inactive)
            }
        }

        binding.songRepeat.setOnClickListener {
            if (comparePlayPauseDrawable(binding.songRepeat, R.drawable.nugu_btn_repeat_inactive)) {
                binding.songRepeat.setImageResource(R.drawable.repeat_active)
            } else {
                binding.songRepeat.setImageResource(R.drawable.nugu_btn_repeat_inactive)
            }
        }
    }

    private fun initPlayList() {
        songDB = SongDB.getDB(this)
        songs.addAll(songDB.songDao().getAllSong())
    }

    private suspend fun initSong() {
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("nowSongID", songs[nowPos].id.toString())
        setPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId: Int): Int {
        for (i in 0 until songs.size) {
            if (songs[i].id == songId) {
                return i
            }
        }
        return 0
    }

    private suspend fun setPlayer(song: Song) {
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        Log.d(
            "songinfo",
            "${song.coverImg}, ${song.music}, $music, ${R.raw.music_lilac}, ${song.second}"
        )
        mediaPlayer = MediaPlayer.create(this, music)
        withContext(Dispatchers.Main) {
            binding.songProgressbar.max = mediaPlayer!!.duration
            binding.songProgressbar.progress = song.second
            mediaPlayer!!.seekTo(song.second)
            binding.currentPlayTime.text = timeFormat.format(mediaPlayer!!.currentPosition)
            binding.albumImg.setImageResource(song.coverImg!!)
            binding.musicTitle.text = song.title
            binding.singerName.text = song.singer
            binding.fullPlayTime.text = timeFormat.format(mediaPlayer!!.duration)
        }
        job = CoroutineScope(Dispatchers.IO).launch {
            while (!this@SongActivity.isFinishing && mediaPlayer != null && mediaPlayer!!.isPlaying) {
                withContext(Dispatchers.Main) {
                    binding.songProgressbar.progress =
                        mediaPlayer!!.currentPosition  // seekBar에 현재 진행 상활 표현
                    binding.currentPlayTime.text =
                        timeFormat.format(mediaPlayer!!.currentPosition)
                }
                SystemClock.sleep(200)
            }
        }
        if (song.isPlaying) {
            binding.songMiniplayer.setImageResource(R.drawable.btn_miniplay_pause)
            mediaPlayer!!.start()
            job.start()
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
        val drawableBitmap =
            (ContextCompat.getDrawable(this@SongActivity, resId) as? BitmapDrawable)?.bitmap

        // 두 비트맵을 비교합니다.
        return imageDrawable.bitmap == drawableBitmap
    }

    private fun moveSong(direct: Int) {
        if (nowPos + direct < 0) {
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }

        if (nowPos + direct >= songs.size) {
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }
        songs[nowPos].second = binding.songProgressbar.progress
        mediaPlayer!!.release()
        mediaPlayer = null
        job.cancel()
        CoroutineScope(Dispatchers.IO).launch {
            val dao = songDB.songDao()
            dao.update(songs[nowPos])
        }
        nowPos += direct

        CoroutineScope(Dispatchers.IO).launch {
            setPlayer(songs[nowPos])
        }
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            songs[nowPos].second = mediaPlayer!!.currentPosition
            Log.d("songinfofinishsecond", songs[nowPos].second.toString())
            CoroutineScope(Dispatchers.IO).launch {
                val dao = songDB.songDao()
                dao.update(songs[nowPos])
            }
            finish()
//            val intent = Intent(this@SongActivity, MainActivity::class.java)
//            intent.putExtra("song", binding.musicTitle.text.toString())
//            intent.putExtra("singer", binding.singerName.text.toString())
//            intent.putExtra("progress", binding.songProgressbar.progress)
//            setResult(0, intent)
//            finish()

        }
    }

    override fun onPause() {
        super.onPause()
//        songs[nowPos].second =
//            ((binding.songProgressbar.progress * songs[nowPos].playTime) / 100) / 1000
//        songs[nowPos].isPlaying = false

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() // 에디터

        editor.putInt("songId", songs[nowPos].id)

        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.release()
        mediaPlayer = null
        job.cancel()

    }
}