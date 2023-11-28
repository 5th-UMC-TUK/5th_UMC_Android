package com.hgh.kakaologin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hgh.kakaologin.databinding.ActivityMainBinding
import com.kakao.sdk.user.UserApiClient


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("KaKao", "사용자 정보 요청 실패 $error")
            } else if (user != null) {
                Log.d("KaKao", "사용자 정보 요청 성공 : $user")
                binding.nameT.text = user.kakaoAccount?.profile?.nickname
                Glide.with(this)
                    .load(user.kakaoAccount?.profile?.profileImageUrl)
                    .error(R.drawable.profile_img)
                    .into(binding.profileImg)
            }
        }
    }
}