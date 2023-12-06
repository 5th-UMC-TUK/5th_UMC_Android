package com.lacuna.umc.kakaologinex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.lacuna.umc.kakaologinex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("KaKao", "사용자 정보 요청 실패 $error")
            }
            else if (user != null) {
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