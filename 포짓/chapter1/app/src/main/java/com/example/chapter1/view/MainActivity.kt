package com.example.chapter1.view

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainFrag: MainFragment
    private lateinit var lockFrag: LockFragment
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
                    Toast.makeText(applicationContext, "제목: $songName, 가수: $singerName", Toast.LENGTH_SHORT).show()
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainFrag = MainFragment()
        lockFrag = LockFragment()

        this.onBackPressedDispatcher.addCallback(this, callback)

        binding.imgPlalistStart.setOnClickListener {
            val imageButtonDrawable = binding.imgPlalistStart.drawable as? BitmapDrawable
            val playDrawable = ContextCompat.getDrawable(this, R.drawable.btn_miniplayer_play) as? BitmapDrawable
            if(playDrawable != null && imageButtonDrawable != null && playDrawable.bitmap == imageButtonDrawable.bitmap) {
                binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplay_pause)
            } else {
                binding.imgPlalistStart.setImageResource(R.drawable.btn_miniplayer_play)
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

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val currentFragment = supportFragmentManager.findFragmentByTag("main")

            if (currentFragment != null) {
                binding.mainNav.selectedItemId = R.id.bottom_main
                supportFragmentManager.beginTransaction()
                    .show(mainFrag)
                    .hide(lockFrag)
                    .commit()
                return
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
}