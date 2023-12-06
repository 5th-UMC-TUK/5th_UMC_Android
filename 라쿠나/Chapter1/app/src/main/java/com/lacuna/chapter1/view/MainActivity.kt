package com.lacuna.chapter1.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.gson.Gson
import com.lacuna.chapter1.R
import com.lacuna.chapter1.SearchFragment
import com.lacuna.chapter1.data.entity.Song
import com.lacuna.chapter1.data.SongDatabase
import com.lacuna.chapter1.data.TodayMusic
import com.lacuna.chapter1.databinding.ActivityMainBinding
import com.lacuna.chapter1.view.home.HomeFragment
import com.lacuna.chapter1.view.locker.LockerFragment
import com.lacuna.chapter1.view.look.LookFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var song: Song = Song()
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Chapter1)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val message = data.getStringExtra("message")
                    Log.d("message", message!!)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            activityResultLauncher.launch(intent)
        }

        inputDummySongs()
        inputDummyAlbums() // 앨범 더미데이터 db에 추가
        Log.d("MAIN/JWT_TO_SERVER", getJwt().toString())
        initBottomNavigation()
    }

    private fun initBottomNavigation(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainProgressSb.progress = (song.second*100000)/song.playTime
    }
//    fun updateMainPlayerCl(todayMusic: TodayMusic) {
//        binding.mainMiniplayerTitleTv.text = todayMusic.title
//        binding.mainMiniplayerSingerTv.text = todayMusic.singer
//        binding.mainProgressSb.progress = 0
//    }

    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!! // 인스턴스를 받아옴
        val songs = songDB.songDao().getSongs()

        // songs에 데이터가 이미 존재해 더미 데이터를 삽입할 필요가 없음
        if (songs.isNotEmpty()) return

        // songs에 데이터가 없을 때에는 더미 데이터를 삽입해주어야 함
        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
                1
            )
        )

        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_flu",
                R.drawable.img_album_exp2,
                false,
                1
            )
        )

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_butter",
                R.drawable.img_album_exp1,
                false,
                2
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_next",
                R.drawable.img_album_exp3,
                false,
                3
            )
        )


        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "music_boy",
                0,
                230,
                false,
                "music_boy",
                R.drawable.img_album_exp4,
                false,
                4
            )
        )


        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_bboom",
                R.drawable.img_album_exp5,
                false,
                5
            )
        )

        // DB에 데이터가 잘 들어갔는지 확인
        val songDBData = songDB.songDao().getSongs()
        Log.d("DB data", songDBData.toString())
    }

    private fun inputDummyAlbums(){
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.albumDao().getAlbums()

        if(albums.isNotEmpty()) return
        songDB.albumDao().insert(
            TodayMusic(
                1,
                "IU 5th Album 'LILAC'",
                "아이유 (IU)",
                R.drawable.img_album_exp2
            )
        )

        songDB.albumDao().insert(
            TodayMusic(
                2,
                "Butter",
                "방탄소년단 (BTS)",
                R.drawable.img_album_exp1
            )
        )

        songDB.albumDao().insert(
            TodayMusic(
                3,
                "Next Level",
                "에스파 (AESPA)",
                R.drawable.img_album_exp3
            )
        )

        songDB.albumDao().insert(
            TodayMusic(
                4,
                "Map of the Soul Persona",
                "방탄소년단",
                R.drawable.img_album_exp4,
            )
        )


        songDB.albumDao().insert(
            TodayMusic(
                5,
                "Great!",
                "모모랜드 (MOMOLAND)",
                R.drawable.img_album_exp5
            )
        )

        val songDBData = songDB.albumDao().getAlbums()
        Log.d("DB data", songDBData.toString())
    }
    private fun getJwt(): String? {
        val spf = this.getSharedPreferences("auth2" , AppCompatActivity.MODE_PRIVATE)

        return spf!!.getString("jwt", "")
    }
    override fun onStart() { // SongActivity에서 창을 닫고 MainActivity로 돌아올 때 onStart() 호출
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        // songId는 SongActivity에서 onPause가 호출되었을 때
        // 재생 중이던 노래의 id(pk)이다.
        val songId = sharedPreferences.getInt("songId", 0)

        // SongDatabase의 인스턴스를 가져온다.
        val songDB = SongDatabase.getInstance(this)!!

        song = if (songId == 0){ // 재생 중이던 노래가 없었으면
            songDB.songDao().getSong(1)
        } else{ // 재생 중이던 노래가 있었으면
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song) // song의 정보로 MiniPlayer를 setting
    }


}