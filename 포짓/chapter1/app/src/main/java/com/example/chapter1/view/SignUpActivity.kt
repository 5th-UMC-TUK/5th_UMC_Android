package com.example.chapter1.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.chapter1.R
import com.example.chapter1.databinding.ActivitySignUpBinding
import com.example.chapter1.db.User
import com.example.chapter1.db.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var userDB: UserDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = UserDB.getDB(this@SignUpActivity)

        window.apply {
            clearFlags(this.attributes.flags)
        }

        if (Build.VERSION.SDK_INT >= 30) {    // API 30 에 적용
            WindowCompat.setDecorFitsSystemWindows(window, true)
        }
        binding.signupFinishBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val singUpDeferred = CoroutineScope(Dispatchers.IO).async {
                    userDB.userDao().getUser(binding.inputId.text.toString())
                }
                val exist = singUpDeferred.await()
                if (exist == null) {
                    userDB.userDao().insert(
                        User(
                            binding.inputId.text.toString(), binding.inputEndpoint.text.toString(),
                            binding.inputPassword.text.toString()
                        )
                    )
                    withContext(Dispatchers.Main) {
                        finish()
                    }
                } else {
                    CustomSnackBar.createSnackBar(it, "이미 계정이 존재합니다!").show()
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                if (binding.inputId.text.isNotBlank() && binding.inputEndpoint.text.isNotBlank() &&
                    binding.inputPassword.text.isNotBlank() && binding.inputCheckPassword.text.isNotBlank() &&
                    binding.inputPassword.text.toString() == binding.inputCheckPassword.text.toString()
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