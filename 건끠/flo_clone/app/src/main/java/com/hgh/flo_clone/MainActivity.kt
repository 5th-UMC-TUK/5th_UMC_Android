package com.hgh.flo_clone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.hgh.flo_clone.adapter.MusicRVAdapter
import com.hgh.flo_clone.data.MusicModel
import com.hgh.flo_clone.databinding.ActivityMainBinding
import com.hgh.flo_clone.databinding.FragmentHomeBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NavigationUI.setupWithNavController(
            binding.mainBottomNav,
            findNavController(R.id.nav_host)
        )
    }


}