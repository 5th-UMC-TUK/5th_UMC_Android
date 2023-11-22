package com.hgh.flo_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.hgh.flo_clone.databinding.ActivityLoginBinding
import com.hgh.flo_clone.server.repository.UserRepository
import com.hgh.flo_clone.server.response.login.request.loginRequest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val userService by inject<UserRepository>()
    val liveData = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (binding.editId.length() > 0)
                    liveData.value =
                        (binding.editPassword.length() > 0) && (binding.editEmail.length() > 0)
                else
                    liveData.value = false
            }
        })

        binding.editEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (binding.editEmail.length() > 0)
                    liveData.value =
                        (binding.editId.length() > 0) && (binding.editPassword.length() > 0)
                else
                    liveData.value = false
            }
        })

        binding.editPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (binding.editPassword.length() > 0)
                    liveData.value =
                        (binding.editId.length() > 0) && (binding.editEmail.length() > 0)
                else
                    liveData.value = false
            }
        })
        liveData.observe(this) {
            binding.loginBtn.isEnabled = it
        }

        binding.loginBtn.setOnClickListener {
            lifecycleScope.launch {
                val jwt = userService.postLogin(
                    info = loginRequest(
                        email = binding.editId.text.toString(),
                        password = binding.editPassword.text.toString()
                    )
                )
                if (jwt !=null){
                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@LoginActivity, "회원을 찾을수 없습니다.", Toast.LENGTH_SHORT).show()
                    binding.editPassword.setText("")
                    liveData.value = false
                }
            }
        }


        binding.sginBtn.setOnClickListener {
            startActivity(Intent(this, SignActivity::class.java))
        }
    }
}