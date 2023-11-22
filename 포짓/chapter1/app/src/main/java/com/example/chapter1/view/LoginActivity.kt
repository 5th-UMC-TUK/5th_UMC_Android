package com.example.chapter1.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.chapter1.databinding.ActivityLoginBinding
import com.example.chapter1.db.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDB: UserDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            clearFlags(this.attributes.flags)
        }
        val editor = getSharedPreferences("id", MODE_PRIVATE)
        Log.d("id", editor.getString("userId", "").toString())
        if (editor.getString("userId", "").isNullOrEmpty()) {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        userDB = UserDB.getDB(this@LoginActivity)
        binding.loginBtn.setOnClickListener {
            if (binding.loginId.text.isNotEmpty() && binding.loginEndpoint.text.isNotEmpty() && binding.inputPassword.text.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val userInfo = userDB.userDao().getUser(binding.loginId.text.toString())
                    if (userInfo != null) {
                        Log.d("password", userInfo.password)
                        Log.d("password", binding.inputPassword.text.toString())
                        if (userInfo.endpoint == binding.loginEndpoint.text.toString()) {
                            if (userInfo.password == binding.inputPassword.text.toString()) {
                                val editor = getSharedPreferences("id", MODE_PRIVATE).edit()
                                editor.putString("userId", userInfo.id)
                                editor.apply()
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                CustomSnackBar.createSnackBar(it, "이이디 혹은 비밀번호가 틀렸습니다!").show()
                            }
                        } else {
                            CustomSnackBar.createSnackBar(it, "없는 계정입니다!").show()
                        }
                    } else {
                        CustomSnackBar.createSnackBar(it, "없는 계정입니다!").show()
                    }
                }
            }
        }
    }
}