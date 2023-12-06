package com.posite.kakaooauth

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.posite.kakaooauth.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var keyHash = Utility.getKeyHash(this)
        Log.d("key", "key: $keyHash")
        setContentView(binding.root)
        binding.kakaoBtn.setOnClickListener {
            lifecycleScope.launch {
                try {
                    // 서비스 코드에서는 간단하게 로그인 요청하고 oAuthToken 을 받아올 수 있다.
                    val oAuthToken = UserApiClient.loginWithKakao(applicationContext)
                    Log.d("MainActivity", "beanbean > $oAuthToken")
                    UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                        if (error != null) {
                            Log.e("user", "토큰 정보 보기 실패", error)
                        } else if (tokenInfo != null) {
                            Log.i(
                                "user", "토큰 정보 보기 성공" +
                                        "\n회원번호: ${tokenInfo.id}" +
                                        "\n만료시간: ${tokenInfo.expiresIn} 초"
                            )
                        }
                    }
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e("user", "사용자 정보 요청 실패", error)
                        } else if (user != null) {
                            Log.i(
                                "user", "사용자 정보 요청 성공" +
                                        "\n회원번호: ${user.id}" +
                                        "\n이메일: ${user.kakaoAccount?.email}" +
                                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                            )

                        }
                    }
                } catch (error: Throwable) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Log.d("MainActivity", "${error.message} 사용자가 명시적으로 취소")
                    } else {
                        Log.e("MainActivity", "인증 에러 발생", error)
                    }
                }
            }
        }

    }

}