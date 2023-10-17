package com.example.chapter1.view

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.chapter1.R
import com.example.chapter1.adapter.TitleAdapter
import com.example.chapter1.adapter.TodaySongAdapter
import com.example.chapter1.adapter.VideoAdapter
import com.example.chapter1.databinding.ActivityMainBinding
import com.example.chapter1.model.SongModel
import com.example.chapter1.model.TitleModel
import com.example.chapter1.model.TodaySongModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainFrag: MainFragment
    private lateinit var lockFrag: LockFragment
    private lateinit var mediaPlayer: MediaPlayer
    private val activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == 0) {
                val songIntent = it.data
                songIntent?.let { sIntent ->
                    val songName = sIntent.getStringExtra("song")
                    songName?.let { sName ->
                        binding.playSongTitle.text = sName
                    }
                    val singerName = songIntent.getStringExtra("singer")
                    singerName?.let{ singerN->
                        binding.playSongSinger.text = singerN
                    }
                    val songProgress = songIntent.getIntExtra("progress", 0)
                    Log.d("progress", songProgress.toString())
                    if(songProgress != 0) {
                        binding.songProgressbar.progress = songProgress
                        mediaPlayer.seekTo(songProgress)
                    }
                    Toast.makeText(applicationContext, "제목: $songName, 가수: $singerName", Toast.LENGTH_SHORT).show()
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val content: View = findViewById(android.R.id.content)
//        // SplashScreen이 생성되고 그려질 때 계속해서 호출된다.
//        content.viewTreeObserver.addOnPreDrawListener(
//            object : ViewTreeObserver.OnPreDrawListener {
//                override fun onPreDraw(): Boolean {
//                    // Check if the initial data is ready.
//                    Handler(Looper.getMainLooper()).postDelayed({content.viewTreeObserver.removeOnPreDrawListener(this)}, 2000)
//                    SystemClock.sleep(1500)
//                    return true
//                }
//            }
//        )

        mainFrag = MainFragment()
        lockFrag = LockFragment()

        this.onBackPressedDispatcher.addCallback(this, callback)
        binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplayer_play)

        setMedia()

        binding.imgPlalistStart.setOnClickListener {
            Log.d("img", comparePlayPauseDrawable(binding.imgPlalistStart, R.drawable.btn_miniplayer_play).toString())
            if(comparePlayPauseDrawable(binding.imgPlalistStart, R.drawable.btn_miniplay_pause)) {
                binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplayer_play)
                mediaPlayer.pause()
            } else {
                binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplay_pause)
                mediaPlayer.start()
                CoroutineScope(Dispatchers.IO).launch {
                    while (!this@MainActivity.isFinishing && mediaPlayer.isPlaying) {
                        withContext(Dispatchers.Main) {
                            binding.songProgressbar.progress = mediaPlayer.currentPosition  // seekBar에 현재 진행 상활 표현
                        }
                        SystemClock.sleep(200)
                    }
                }
            }
        }

        supportFragmentManager.beginTransaction()
            .add(binding.fragmentFrame.id, mainFrag, "main")
            .add(binding.fragmentFrame.id, lockFrag, "lock")
            .hide(lockFrag)
            .show(mainFrag)
            .commit()

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
//        val controller = navHostFragment.navController
//        binding.mainNav.setupWithNavController(controller)

        binding.playSongFrame.setOnClickListener {
            val intent = Intent(this@MainActivity, SongActivity::class.java)
            activityResultLauncher.launch(intent)
        }

        binding.imgGoPlaylist.setOnClickListener {
            val intent = Intent(this@MainActivity, SongActivity::class.java)
            activityResultLauncher.launch(intent)
        }

        binding.mainNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.bottom_main -> {
                    supportFragmentManager.beginTransaction()
                        .show(mainFrag)
                        .hide(lockFrag)
                        .commit()
                }
                R.id.bottom_locker -> {
                    supportFragmentManager.beginTransaction()
                        .hide(mainFrag)
                        .show(lockFrag)
                        .commit()
                }
            }
            true
        }
    }

    private fun setMedia() {
        mediaPlayer = MediaPlayer.create(this, R.raw.music_lilac)     // 미디어 플레이어 객체 생성
        binding.songProgressbar.max = mediaPlayer.duration

        binding.songProgressbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if(!this@MainActivity.isFinishing) {
                    seekBar?.let {
                        mediaPlayer.seekTo(it.progress)
                    }
                }
            }

        })
    }

    override fun onStart() {
        super.onStart()
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.btn_main_ticket)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return true
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//
//            }
//            R.id.main_mic -> {
//
//            }
//            R.id.main_notice -> {
//
//            }
//            R.id.main_settings -> {
//
    
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun comparePlayPauseDrawable(image: ImageView, resId: Int): Boolean {
        // ImageButton의 현재 이미지를 가져옵니다.
        val imageDrawable = image.drawable as? BitmapDrawable
        if (imageDrawable == null) {
            // ImageButton에 비트맵 이미지가 아닌 다른 유형의 이미지가 있는 경우 처리
            return false
        }

        // Drawable 리소스에서 비트맵 이미지를 가져옵니다.
        val drawableBitmap = (ContextCompat.getDrawable(this@MainActivity, resId) as? BitmapDrawable)?.bitmap

        // 두 비트맵을 비교합니다.
        return imageDrawable.bitmap == drawableBitmap
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

            if (mainFrag.isVisible) {
                finishAffinity()
            } else {
                binding.mainNav.selectedItemId = R.id.bottom_main
                supportFragmentManager.beginTransaction()
                    .show(mainFrag)
                    .hide(lockFrag)
                    .commit()
                return
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}