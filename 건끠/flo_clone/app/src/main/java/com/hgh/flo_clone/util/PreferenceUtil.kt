package com.hgh.flo_clone.util

import com.hgh.flo_clone.FloApplication
import com.hgh.flo_clone.db.preference.ApplicationPreferenceManager

object UserCode {

    //login
    const val jwt = "jwt"
    const val username = "username"
}

fun saveJwt(jwt: String) {
    val spf = FloApplication.sSharedPreference
    val editor = spf.edit()

    editor.putString(UserCode.jwt, jwt)
    editor.apply()
}

fun getJwt(): String? {
    val spf = FloApplication.sSharedPreference
    return spf.getString(UserCode.jwt, null)
}
