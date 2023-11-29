package com.example.chapter1.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.chapter1.databinding.ActivityLoginBinding
import com.example.chapter1.model.Login
import com.example.chapter1.network.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val repository = AuthRepository()

    //    private lateinit var userDB: UserDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val editor = getSharedPreferences("token", MODE_PRIVATE)
        val token = editor.getString("token", "")
        Log.d("id", editor.getString("token", "").toString())
        if (!token.isNullOrEmpty()) {
            //토큰 있는경우
            CoroutineScope(Dispatchers.IO).launch {
                val auto = CoroutineScope(Dispatchers.IO).async {
                    repository.autoLoginUser(token)
                }.await()
                auto?.let {
                    if (it.isSuccess && it.code == 1000) {
                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }

            }
        }
        window.apply {
            clearFlags(this.attributes.flags)
        }
        binding.registBtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
//        userDB = UserDB.getDB(this@LoginActivity)
        val editor = getSharedPreferences("token", MODE_PRIVATE)
        binding.loginBtn.setOnClickListener {
            if (binding.loginId.text.isNotEmpty() && binding.loginEndpoint.text.isNotEmpty() && binding.inputPassword.text.isNotEmpty()) {
                val id =
                    binding.loginId.text.toString() + "@" + binding.loginEndpoint.text.toString()
                Log.d("loginid", "$id    ${binding.inputPassword.text}")
                val loginData = Login(id, binding.inputPassword.text.toString())
                CoroutineScope(Dispatchers.IO).launch {
                    val login = CoroutineScope(Dispatchers.IO).async {
                        repository.loginUser(loginData)
                    }.await()
                    login?.let {
                        when (it.code) {
                            1000 -> {
                                editor.edit().putString("token", it.result.jwt).apply()
                                withContext(Dispatchers.Main) {
                                    val intent =
                                        Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                }
                            }

                            2015 -> {
                                CustomSnackBar.createSnackBar(binding.root, "이메일을 입력해주세요.")
                                    .show()
                            }

                            2019 -> {
                                CustomSnackBar.createSnackBar(binding.root, "존재하지 않는 계정입니다.")
                                    .show()
                            }

                            3014 -> {
                                CustomSnackBar.createSnackBar(
                                    binding.root,
                                    "없는 아이디거나 비밀번호가 틀렸습니다."
                                ).show()
                            }
                        }
                    }

                }
            }
        }
    }
}