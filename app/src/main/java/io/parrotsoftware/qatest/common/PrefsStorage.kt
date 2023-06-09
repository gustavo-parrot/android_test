package io.parrotsoftware.qatest.common

import android.content.Context

abstract class PrefsStorage(context: Context, prefsName: String) {

    private val sharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String, default: String = ""): String {
        return sharedPreferences.getString(key, default)!!
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun setBoolean(key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    fun getInt(key: String, default: Int = 0): Int {
        return sharedPreferences.getInt(key, default)
    }

    fun setInt(key: String, value: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, value)
            apply()
        }
    }

    fun clear(key: String) {
        with(sharedPreferences.edit()) {
            remove(key)
            clear()
            apply()
        }
    }
}