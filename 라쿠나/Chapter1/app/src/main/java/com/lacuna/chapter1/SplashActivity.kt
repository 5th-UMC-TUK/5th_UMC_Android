package com.lacuna.chapter1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity(), AutoLoginView  {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(
            {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
            },2000)
        autoLogin()
    }

    private fun autoLogin(){
        val authService = AuthService()
        authService.setAutoLoginView(this)
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        val jwt = spf.getString("jwt","")!!
        Log.d("jwt", jwt)
        authService.autoLogin(jwt)
    }
    override fun onAutoLoginSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        Toast.makeText(this,"자동로그인 성공",Toast.LENGTH_SHORT).show()
    }

    override fun onAutoLoginFailure() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}