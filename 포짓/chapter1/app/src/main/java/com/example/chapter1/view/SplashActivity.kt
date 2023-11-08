package com.example.chapter1.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock.sleep
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.example.chapter1.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val content: View = findViewById(android.R.id.content)
        // SplashScreen이 생성되고 그려질 때 계속해서 호출된다.
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    Handler(Looper.getMainLooper()).postDelayed({
                        content.viewTreeObserver.removeOnPreDrawListener(
                            this
                        )
                    }, 5000)
                    sleep(3000)
                    return true
                }
            }
        )
    }
}