package umc.mission.memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umc.mission.memo.databinding.ActivityCheckBinding

class CheckActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.memoTV.text = intent.getStringExtra("memo")
    }
}