package com.hgh.flo_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.hgh.flo_clone.databinding.ActivitySplashBinding
import com.hgh.flo_clone.server.repository.UserRepository
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    val userService by inject<UserRepository>()
    var loginOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            loginOk = userService.postAutoLogin()?.isSuccess ?: false

        }

        Handler(Looper.getMainLooper()).postDelayed(
            {
                if (loginOk) startActivity(Intent(this, MainActivity::class.java))
                else startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 1500
        )
    }
}