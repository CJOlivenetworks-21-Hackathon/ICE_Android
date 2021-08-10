package com.cj_onlyone.hackathon.ice

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context : Context) {
    private val prefs : SharedPreferences =
            context.getSharedPreferences("pref_name", Context.MODE_PRIVATE)
    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setStrign(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
}
