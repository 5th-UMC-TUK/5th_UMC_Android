package com.hgh.flo_clone.db.preference

import android.content.Context
import android.content.SharedPreferences

class ApplicationPreferenceManager(
    private val context: Context
) {
    companion object {
        const val NAME = "FLO"
        const val JWT = "jwt"
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return  context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    private val prefs by lazy { getPreferences(context) }

    private val editor by lazy { prefs.edit() }

    fun putJwt(jwt: String) {
        editor.putString(JWT, jwt)
        editor.apply()
    }

    fun getJwt(): String? {
        return prefs.getString(JWT, null)
    }

}