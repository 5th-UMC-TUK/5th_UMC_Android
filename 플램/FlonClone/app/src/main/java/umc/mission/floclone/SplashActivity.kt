package umc.mission.floclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import umc.mission.floclone.data.AUTH
import umc.mission.floclone.data.JWT
import umc.mission.floclone.data.User
import umc.mission.floclone.network.AuthService
import umc.mission.floclone.network.Result

class SplashActivity : AppCompatActivity(), LoginAutoView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val authService = AuthService()
        authService.setLoginAutoView(this)

        val spf = getSharedPreferences(AUTH, MODE_PRIVATE)
        val token = spf.getString(JWT, "")!!
        Log.d("token", token.toString())
        authService.loginAuto(token)
    }

    override fun onLoginSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onLoginFailure() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}