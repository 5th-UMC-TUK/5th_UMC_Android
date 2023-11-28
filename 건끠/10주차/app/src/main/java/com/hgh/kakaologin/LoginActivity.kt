package com.hgh.kakaologin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.hgh.kakaologin.databinding.ActivityLoginBinding
import com.hgh.kakaologin.databinding.ActivityMainBinding
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    private val idLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("KaKao", "로그인 실패 $error")
        } else if (token != null) {
            Log.d("KaKao", "로그인 성공 ${token.accessToken}")

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        KakaoSdk.init(this, "95694cd95be3021339a8bfbff0dc516c")

        //자동 로그인
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error == null) {
                    //토큰 유효성 체크 성공
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }

        binding.loginBtn.setOnClickListener {
            // 카카오톡 설치 확인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                // 카카오톡 로그인
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    // 로그인 실패 부분
                    if (error != null) {
                        Log.e("KaKao", "로그인 실패 $error")
                        // 사용자가 취소
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        // 다른 오류 -> 이메일로 로그인 유도
                        } else {
                            UserApiClient.instance.loginWithKakaoAccount(this, callback = idLoginCallback)
                        }
                    // 로그인 성공 부분
                    } else if (token != null) {
                        Log.d("KaKao", "로그인 성공 ${token.accessToken}")
                        Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            } else {
                //카카오톡 없음 -> 이메일로 로그인 유도
                UserApiClient.instance.loginWithKakaoAccount(this, callback = idLoginCallback)
            }
        }
    }
}