package com.example.chapter1.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.chapter1.R
import com.example.chapter1.databinding.ActivitySignUpBinding
import com.example.chapter1.model.User
import com.example.chapter1.network.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val repository = AuthRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            clearFlags(this.attributes.flags)
        }

        if (Build.VERSION.SDK_INT >= 30) {    // API 30 에 적용
            WindowCompat.setDecorFitsSystemWindows(window, true)
        }
        //회원가입 버튼 눌렀을 때 api 연동
        binding.signupFinishBtn.setOnClickListener {
            val id = binding.inputId.text.toString() + "@" + binding.inputEndpoint.text.toString()
            val regist =
                User(id, binding.inputPassword.text.toString(), binding.inputName.text.toString())
            CoroutineScope(Dispatchers.IO).launch {
                val result = CoroutineScope(Dispatchers.IO).async {
                    repository.registUser(regist)
                }.await()
                if (result != null) {
                    when (result.code) {
                        1000 -> {
                            finish()
                        }

                        2016 -> {
                            CustomSnackBar.createSnackBar(binding.root, "이메일 형식을 확인해주세요.").show()
                        }

                        2000 -> {
                            CustomSnackBar.createSnackBar(binding.root, "입력값을 확인해주세요.").show()
                        }

                        2017 -> {
                            CustomSnackBar.createSnackBar(binding.root, "중복된 이메일입니다.").show()
                        }
                    }
                } else {
                    Log.d("signup", "등록 network오류")
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                if (binding.inputId.text.isNotBlank() && binding.inputEndpoint.text.isNotBlank() &&
                    binding.inputPassword.text.isNotBlank() && binding.inputCheckPassword.text.isNotBlank() &&
                    binding.inputPassword.text.toString() == binding.inputCheckPassword.text.toString() &&
                    binding.inputName.text.toString().isNotBlank()
                ) {
                    withContext(Dispatchers.Main) {
                        binding.signupFinishBtn.isEnabled = true
                        binding.signupFinishBtn.setTextColor(
                            ContextCompat.getColor(
                                this@SignUpActivity,
                                R.color.white
                            )
                        )
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.signupFinishBtn.isEnabled = false
                        binding.signupFinishBtn.setTextColor(
                            ContextCompat.getColor(
                                this@SignUpActivity,
                                R.color.gray_aaaaaa
                            )
                        )
                    }

                }
                delay(100)
            }
        }

    }
}