package com.hgh.flo_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.hgh.flo_clone.databinding.ActivitySignBinding
import com.hgh.flo_clone.server.repository.UserRepository
import com.hgh.flo_clone.server.response.sign.request.signRequest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SignActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignBinding

    private val userService by inject<UserRepository>()
    val liveData = MutableLiveData<Boolean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (binding.editId.length() > 0)
                    liveData.value =
                        (binding.editEmail.length() > 0) && (binding.editPassword.length() > 0)
                                && (binding.editPasswordAgain.length() > 0) && (binding.editPassword.text.toString() ==binding.editPasswordAgain.text.toString())
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
                                && (binding.editPasswordAgain.length() > 0) && (binding.editPassword.text.toString() ==binding.editPasswordAgain.text.toString())
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
                                && (binding.editPasswordAgain.length() > 0) && (binding.editPassword.text.toString() ==binding.editPasswordAgain.text.toString())
                else
                    liveData.value = false
            }
        })

        binding.editPasswordAgain.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (binding.editPasswordAgain.length() > 0)
                    liveData.value =
                        (binding.editId.length() > 0) && (binding.editPassword.length() > 0)
                                && (binding.editEmail.length() > 0) && (binding.editPassword.text.toString() ==binding.editPasswordAgain.text.toString())
                else
                    liveData.value = false
            }
        })

        binding.SignBtn.setOnClickListener {
            lifecycleScope.launch {
                val ok = userService.postSign(
                    signRequest(
                        email = binding.editId.text.toString(),
                        password = binding.editPassword.text.toString(),
                        name = binding.editEmail.text.toString()
                    )
                )
                if (ok !=null && ok.isSuccess){
                     finish()
                }else{
                    Toast.makeText(this@SignActivity, "${ok?.message ?: "가입실패"}", Toast.LENGTH_SHORT).show()
                    binding.editPassword.setText("")
                    binding.editPasswordAgain.setText("")
                    liveData.value = false
                }
            }
        }

        liveData.observe(this) {
            binding.SignBtn.isEnabled = it
        }

    }
}