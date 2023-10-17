package com.posite.memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.posite.memo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySecondBinding.inflate(layoutInflater) }
    private var title = ""
    private var content = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = intent.getStringExtra("title")!!
        content = intent.getStringExtra("content")!!

        binding.memoTitle.text = title
        binding.memoContent.text = content

        binding.saveBtn.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            setResult(0, intent)
            finish()
        }
    }
}