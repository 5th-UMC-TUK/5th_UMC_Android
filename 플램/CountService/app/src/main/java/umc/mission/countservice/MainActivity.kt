package umc.mission.countservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umc.mission.countservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            val intent = Intent(this, CountService::class.java)
            startService(intent)
        }
    }
}