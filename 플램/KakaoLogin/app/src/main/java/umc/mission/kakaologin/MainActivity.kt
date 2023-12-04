package umc.mission.kakaologin


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import umc.mission.kakaologin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("TAG", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i("TAG", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                Glide.with(this).load(user.kakaoAccount?.profile?.thumbnailImageUrl).into(binding.profileIv)
                binding.nameTv.text = user.kakaoAccount?.profile?.nickname
            }
        }
    }
}