package com.example.brorentalapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.brorentalapp.utils.Constants.PHONE_NUMBER
import com.example.brorentalapp.utils.Constants.IS_FIRST_TIME
import com.example.brorentalapp.utils.Constants.IS_LOGIN
import com.example.brorentalapp.utils.Constants.SHARED_PREF
import com.example.brorentalapp.utils.Constants.TERMS_CONDITION

class SessionConfig(ctx: Context) {
    private val prefs: SharedPreferences = ctx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    fun setFirstTime(isFirstTime: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(IS_FIRST_TIME, isFirstTime)
        editor.apply()
    }

    fun getFirstTime(): Boolean {
        return prefs.getBoolean(IS_FIRST_TIME, true)
    }

    fun setTermConditions(isFirstTime: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(TERMS_CONDITION, isFirstTime)
        editor.apply()
    }

    fun getTermConditions(): Boolean {
        return prefs.getBoolean(TERMS_CONDITION, false)
    }

    fun setLogin(isLogin: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(IS_LOGIN, isLogin)
        editor.apply()
    }

    fun getLogin(): Boolean {
        return prefs.getBoolean(IS_LOGIN, false)
    }

    fun setPhone(phone: String) {
        val editor = prefs.edit()
        editor.putString(PHONE_NUMBER, phone)
        editor.apply()
    }

    fun getPhone(): String? {
        return prefs.getString(PHONE_NUMBER, "Null")
    }


}
