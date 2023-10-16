package umc.mission.memo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umc.mission.memo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var memo: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveBtn.setOnClickListener {
            val intent = Intent(this, CheckActivity::class.java)
            intent.putExtra("memo", "${binding.edit.text}")
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if(memo != null){
            binding.edit.setText(memo)
        }
    }

    override fun onPause() {
        super.onPause()
        memo = binding.edit.text.toString()
    }

    override fun onRestart() {
        super.onRestart()
        AlertDialog.Builder(this).apply {
            setMessage("메모를 다시 작성하시겠습니까?")
            setPositiveButton("예"){ _, _ ->
                binding.edit.text = null
            }
            setNegativeButton("아니오"){ _, _ ->
                memo = null
            }
        }.show()
    }
}