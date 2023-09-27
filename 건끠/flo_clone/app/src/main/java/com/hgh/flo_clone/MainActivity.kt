package com.hgh.flo_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.hgh.flo_clone.adapter.MusicRVAdapter
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.ActivityMainBinding
import com.hgh.flo_clone.databinding.FragmentHomeBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val songIntentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val resultMusic = result.data?.getParcelableExtra<MusicModel>("return")
                Toast.makeText(this,"${resultMusic?.title ?: " "}",Toast.LENGTH_LONG).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NavigationUI.setupWithNavController(
            binding.mainBottomNav,
            findNavController(R.id.nav_host)
        )

        binding.nowPlayListLayout.setOnClickListener{
            intent = Intent(this, SongActivity::class.java).apply {
                putExtra("song",MusicModel(binding.nowPlayTitle.text.toString(), binding.nowPlaySinger.text.toString()))
            }
            songIntentLauncher.launch(intent)
        }
    }


}