package com.lacuna.memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.lacuna.memo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var title: String = ""
    private var memo: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnBtn.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("title", binding.memoTitle.text.toString())
            intent.putExtra("data", binding.memoContent.text.toString())
            startActivity(intent)
        }

        Toast.makeText(this.applicationContext, "onCreate", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        if(title != "") {
            binding.memoTitle.setText(title)
        }
        if(memo != "") {
            binding.memoContent.setText(memo)
        }
        Toast.makeText(this.applicationContext, "onResume", Toast.LENGTH_SHORT).show()

    }

    override fun onPause() {
        super.onPause()
        title = binding.memoTitle.text.toString()
        memo = binding.memoContent.text.toString()
        Toast.makeText(this.applicationContext, "onPause", Toast.LENGTH_SHORT).show()

    }

    override fun onRestart() {
        super.onRestart()
        AlertDialog.Builder(this).apply {
            setMessage("메모를 다시 작성하시겠습니까?")
            setPositiveButton("예"){ _, _ ->
                binding.memoTitle.text = null
                binding.memoContent.text = null
            }
            setNegativeButton("아니오"){ _, _ ->
                title = ""
                memo = ""
            }
        }.show()
        Toast.makeText(this.applicationContext, "onRestart", Toast.LENGTH_SHORT).show()
        Log.d("onRestart", "onRestart()")
    }
}