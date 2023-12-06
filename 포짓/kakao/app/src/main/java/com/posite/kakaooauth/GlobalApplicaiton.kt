package com.posite.kakaooauth

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplicaiton : Application() {
    override fun onCreate() {
        super.onCreate()

        // Kakao Sdk 초기화
        KakaoSdk.init(this, resources.getString(R.string.kakao_value))
    }
}