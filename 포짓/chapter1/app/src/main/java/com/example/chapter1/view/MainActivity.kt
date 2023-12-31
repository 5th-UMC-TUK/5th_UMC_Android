package com.example.chapter1.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.chapter1.R
import com.example.chapter1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainFrag: MainFragment
    private lateinit var lockFrag: LockFragment
    private lateinit var lookFrag: LookAroundFragment
//    private var mediaPlayer: MediaPlayer? = null
//    private lateinit var serviceIntent: Intent
//    private lateinit var job: Job
//    val songs = arrayListOf<Song>()
//    val playingSong = arrayListOf<Song>()
//    lateinit var songDB: SongDB
//    private var songId = 0
//    private var nowPos = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CoroutineScope(Dispatchers.IO).launch {
//            inputDummyAlbum()
//            inputDummySongs()
//            initPlayList()
//        }
        mainFrag = MainFragment()
        lockFrag = LockFragment()
        lookFrag = LookAroundFragment()

        this.onBackPressedDispatcher.addCallback(this, callback)
        binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplayer_play)


//        serviceIntent = Intent(this@MainActivity, FloService::class.java)
//        startService(serviceIntent)

        binding.imgPlalistStart.setOnClickListener {
//            playSong()
        }

        binding.imgPlalistPrevious.setOnClickListener {
//            moveSong(-1)
        }
        binding.imgPlaylistNext.setOnClickListener {
//            moveSong(+1)
        }

        supportFragmentManager.beginTransaction()
            .add(binding.fragmentFrame.id, mainFrag, "main")
            .add(binding.fragmentFrame.id, lockFrag, "lock")
            .add(binding.fragmentFrame.id, lookFrag, "look")
            .hide(lockFrag)
            .hide(lookFrag)
            .show(mainFrag)
            .commit()

        binding.playSongFrame.setOnClickListener {
//            if (job.isActive) {
//                job.cancel()
//                mediaPlayer!!.pause()
//            }
//            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
//            editor.putInt("songId", song.id)
//            editor.apply()
//            song.second = mediaPlayer!!.currentPosition
//            Log.d("songinfomainsecond", song.second.toString())
//            mediaPlayer!!.pause()
//            job.cancel()
//            CoroutineScope(Dispatchers.IO).launch {
//                val dao = songDB.songDao()
//                dao.update(song)
//            }
            binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplayer_play)
            val intent = Intent(this@MainActivity, SongActivity::class.java)
            startActivity(intent)
        }

        binding.imgGoPlaylist.setOnClickListener {
//            if (job.isActive) {
//                job.cancel()
//                mediaPlayer!!.pause()
//            }
//            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
//            editor.putInt("songId", song.id)
//            editor.apply()
//            song.second = mediaPlayer!!.currentPosition
//            Log.d("songinfomainsecond", song.second.toString())
//            mediaPlayer!!.pause()
//            job.cancel()
//            CoroutineScope(Dispatchers.IO).launch {
//                val dao = songDB.songDao()
//                dao.update(song)
//            }
            binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplayer_play)
            val intent = Intent(this@MainActivity, SongActivity::class.java)
            startActivity(intent)
        }

        binding.mainNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_main -> {
                    supportFragmentManager.beginTransaction()
                        .show(mainFrag)
                        .hide(lockFrag)
                        .hide(lookFrag)
                        .commit()
                }

                R.id.bottom_locker -> {
                    supportFragmentManager.beginTransaction()
                        .remove(lockFrag).commit()
                    lockFrag = LockFragment()
                    supportFragmentManager.beginTransaction()
                        .add(binding.fragmentFrame.id, lockFrag, "lock")
                        .hide(mainFrag)
                        .hide(lookFrag)
                        .show(lockFrag)
                        .commit()
                }

                R.id.bottom_lookaround -> {
                    supportFragmentManager.beginTransaction()
                        .remove(lookFrag).commit()
                    lookFrag = LookAroundFragment()
                    supportFragmentManager.beginTransaction()
                        .add(binding.fragmentFrame.id, lookFrag, "look")
                        .hide(mainFrag)
                        .hide(lockFrag)
                        .show(lookFrag)
                        .commit()
                }
            }
            true
        }
    }

//    private fun initPlayList() {
//        songDB = SongDB.getDB(this)
//        val allSongs = songDB.songDao().getAllSong()
//        songs.addAll(allSongs)
//        playingSong.clear()
//        playingSong.addAll(allSongs)
//    }

//    private fun moveSong(direct: Int) {
//        if (nowPos + direct < 0) {
//            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if (nowPos + direct >= playingSong.size) {
//            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
//            return
//        }
//        nowPos += direct
//        Log.d("aftersong", playingSong.size.toString())
//        Log.d("aftermove", nowPos.toString())
//        mediaPlayer!!.release()
//        binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplayer_play)
//        CoroutineScope(Dispatchers.IO).launch {
//            song = playingSong[nowPos]
//            setMedia()
//        }
//    }


    override fun onStart() {
        super.onStart()
//        val spf = getSharedPreferences("song", MODE_PRIVATE)
//        songId = spf.getInt("songId", 0)
//        nowPos = getPlayingSongPosition(songId)
//        val songDB = SongDB.getDB(this)
//
//        CoroutineScope(Dispatchers.IO).launch {
//            songs.clear()
//            songs.addAll(songDB.songDao().getAllSong())
//            song = if (songId == 0) {
//                songDB.songDao().getSong(1)
//            } else {
//                songDB.songDao().getSong(songId)
//            }
//            Log.d("nameandsinger", "${song.title}, ${song.singer}, $songId, $nowPos")
//            withContext(Dispatchers.Main) {
//                binding.playSongTitle.text = song.title
//                binding.playSongSinger.text = song.singer
//                song
//                setMedia()
//            }
//        }
    }

//    fun setCurrentSong(song: Song) {
//        this.song = song
//    }

//    fun getCurrentSong() = song
//    private fun getPlayingSongPosition(songId: Int): Int {
//        for (i in 0 until songs.size) {
//            if (songs[i].id == songId) {
//                return i
//            }
//        }
//        return 0
//    }

//    fun playSong() {
//        Log.d(
//            "img",
//            comparePlayPauseDrawable(
//                binding.imgPlalistStart,
//                R.drawable.btn_miniplayer_play
//            ).toString()
//        )
//        if (comparePlayPauseDrawable(binding.imgPlalistStart, R.drawable.btn_miniplay_pause)) {
//            binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplayer_play)
//            job.cancel()
//            mediaPlayer!!.pause()
//        } else {
//            binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplay_pause)
//            job = CoroutineScope(Dispatchers.IO).launch {
//                while (!this@MainActivity.isFinishing && mediaPlayer != null && mediaPlayer!!.isPlaying) {
//                    withContext(Dispatchers.Main) {
//                        binding.songProgressbar.progress = mediaPlayer!!.currentPosition
//                    }
//                    SystemClock.sleep(200)
//                }
//            }
//            mediaPlayer!!.start()
//            job.start()
//        }
//    }

//    fun playAlbumSong(albumIdx: Int) {
//        val albumSongs = songs.filter { it.albumIdx == albumIdx }
//        Log.d("albumsong", albumSongs.toString())
//        playingSong.clear()
//        playingSong.addAll(albumSongs)
//        if (albumSongs.isNotEmpty()) {
//            val music =
//                resources.getIdentifier(
//                    albumSongs.first().music,
//                    "raw",
//                    this@MainActivity.packageName
//                )
//            var index = 0
//            binding.playSongTitle.text = albumSongs.first().title
//            binding.playSongSinger.text = albumSongs.first().singer
//            job.cancel()
//            mediaPlayer!!.release()
//            mediaPlayer = MediaPlayer.create(this@MainActivity, music)
//            mediaPlayer!!.setOnCompletionListener {
//                playNextSong(albumSongs, index)
//            }
//            binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplay_pause)
//            job = CoroutineScope(Dispatchers.IO).launch {
//                while (!this@MainActivity.isFinishing && mediaPlayer != null && mediaPlayer!!.isPlaying) {
//                    withContext(Dispatchers.Main) {
//                        binding.songProgressbar.progress = mediaPlayer!!.currentPosition
//                    }
//                    SystemClock.sleep(200)
//                }
//            }
//            mediaPlayer!!.start()
//            job.start()
//        }
//    }

//    private fun playNextSong(albumSongs: List<Song>, index: Int) {
//        mediaPlayer!!.stop()
//        mediaPlayer!!.release()
//        if (albumSongs.size >= index) {
//            val music =
//                resources.getIdentifier(
//                    albumSongs.first().music,
//                    "raw",
//                    this@MainActivity.packageName
//                )
//            mediaPlayer = MediaPlayer.create(this@MainActivity, music)
//            mediaPlayer!!.start()
//            binding.playSongTitle.text = albumSongs.first().title
//            binding.playSongSinger.text = albumSongs.first().singer
//            return
//        }
//    }


//    private suspend fun setMedia() {
//        val music =
//            resources.getIdentifier(song.music, "raw", this@MainActivity.packageName)
//        Log.d(
//            "songinfomain",
//            "${song.coverImg}, ${song.music}, $music, ${R.raw.music_lilac} ${song.second}, ${song.isLike}"
//        )
//        mediaPlayer = MediaPlayer.create(this@MainActivity, music)
//        withContext(Dispatchers.Main) {
//            binding.songProgressbar.max = mediaPlayer!!.duration
//            mediaPlayer!!.seekTo(song.second)
//            binding.songProgressbar.progress = mediaPlayer!!.currentPosition
//            binding.playSongTitle.text = song.title
//            binding.playSongSinger.text = song.singer
//        }
//        Log.d("song ID", song.id.toString())
//        job = CoroutineScope(Dispatchers.IO).launch {
//            while (!this@MainActivity.isFinishing && mediaPlayer != null && mediaPlayer!!.isPlaying) {
//                withContext(Dispatchers.Main) {
//                    binding.songProgressbar.progress = mediaPlayer!!.currentPosition
//                }
//                SystemClock.sleep(200)
//            }
//        }
//
//        binding.songProgressbar.setOnSeekBarChangeListener(object :
//            SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                if (!this@MainActivity.isFinishing) {
//                    seekBar?.let {
//                        mediaPlayer!!.seekTo(it.progress)
//                    }
//                }
//            }
//
//        })
//    }


//    private fun comparePlayPauseDrawable(image: ImageView, resId: Int): Boolean {
//        val imageDrawable = image.drawable as? BitmapDrawable
//        if (imageDrawable == null) {
//            return false
//        }
//        val drawableBitmap =
//            (ContextCompat.getDrawable(this@MainActivity, resId) as? BitmapDrawable)?.bitmap
//        return imageDrawable.bitmap == drawableBitmap
//    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (mainFrag.isVisible) {
                finishAffinity()
            } else {
                binding.mainNav.selectedItemId = R.id.bottom_main
                supportFragmentManager.beginTransaction()
                    .show(mainFrag)
                    .hide(lockFrag)
                    .hide(lookFrag)
                    .commit()
                return
            }

        }
    }

//    private fun inputDummySongs() {
//        val songDB = SongDB.getDB(this)
//        val songs = songDB.songDao().getAllSong()
//        Log.d("DB_data", songs.toString())
//        if (songs.isNotEmpty()) return
//
//        songDB.songDao().insert(
//            Song(
//                "Lilac",
//                "아이유 (IU)",
//                0,
//                200,
//                false,
//                "music_lilac",
//                R.drawable.img_album_exp2,
//                false,
//                1
//            )
//        )
//
//        songDB.songDao().insert(
//            Song(
//                "Flu",
//                "아이유 (IU)",
//                0,
//                200,
//                false,
//                "music_flu",
//                R.drawable.img_album_exp2,
//                false,
//                1
//            )
//        )
//
//        songDB.songDao().insert(
//            Song(
//                "Butter",
//                "방탄소년단 (BTS)",
//                0,
//                190,
//                false,
//                "music_butter",
//                R.drawable.img_album_exp,
//                false,
//                2
//            )
//        )
//
//        songDB.songDao().insert(
//            Song(
//                "Next Level",
//                "에스파 (AESPA)",
//                0,
//                210,
//                false,
//                "music_next",
//                R.drawable.img_album_exp3,
//                false,
//                3
//            )
//        )
//
//
//        songDB.songDao().insert(
//            Song(
//                "Boy with Luv",
//                "music_boy",
//                0,
//                230,
//                false,
//                "music_boy",
//                R.drawable.img_album_exp4,
//                false,
//                4
//            )
//        )
//
//
//        songDB.songDao().insert(
//            Song(
//                "BBoom BBoom",
//                "모모랜드 (MOMOLAND)",
//                0,
//                240,
//                false,
//                "music_bboom",
//                R.drawable.img_album_exp5,
//                false,
//                5
//            )
//        )
//
//        val _songs = songDB.songDao().getAllSong()
//        Log.d("DB_data", _songs.toString())
//    }
//
//    private fun inputDummyAlbum() {
//        val songDB = SongDB.getDB(this)
//        val albums = songDB.albumDao().getAllAlbum()
//        Log.d("DB_data", albums.toString())
//        if (albums.isNotEmpty()) return
//
//        songDB.albumDao().insert(
//            Album(
//                "IU 5th Album Lilac",
//                "아이유(IU)",
//                R.drawable.img_album_exp2
//            )
//        )
//
//        songDB.albumDao().insert(
//            Album(
//                "Butter",
//                "방탄소년단",
//                R.drawable.img_album_exp
//            )
//        )
//        songDB.albumDao().insert(
//            Album(
//                "Next Level Remixes",
//                "에스파 (AESPA)",
//                R.drawable.img_album_exp3,
//            )
//        )
//        songDB.albumDao().insert(
//            Album(
//                "MAP OF THE SOUL : PERSONA",
//                "방탄소년단 (BTS)",
//                R.drawable.img_album_exp4,
//            )
//        )
//        songDB.albumDao().insert(
//            Album(
//                "GREAT!",
//                "모모랜드",
//                R.drawable.img_album_exp5,
//            )
//        )
//
//        val _albums = songDB.albumDao().getAllAlbum()
//        Log.d("DB_data", _albums.toString())
//    }

    fun setNavVisibility(visibility: Int) {
        binding.mainNav.visibility = visibility
    }

    override fun onDestroy() {
        super.onDestroy()
//        stopService(serviceIntent)
//        job.cancel()
//        mediaPlayer!!.release()
    }
}