package com.lnavamuelp.historicalroots.di


import android.content.Context
import androidx.core.content.edit

object UserPreferences {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USERNAME = "username"
    private const val KEY_PASSWORD = "password"
    private const val KEY_REMEMBER_CREDENTIALS = "remember_credentials"
    private const val KEY_THEME_COLOR = "theme_color"
    private const val KEY_LANGUAGE = "language"

    fun saveCredentials(context: Context, username: String, password: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit {
            putString(KEY_USERNAME, username)
            putString(KEY_PASSWORD, password)
        }
    }

    fun getRememberCredentials(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_REMEMBER_CREDENTIALS, false)
    }

    fun saveRememberCredentials(context: Context, remember: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit {
            putBoolean(KEY_REMEMBER_CREDENTIALS, remember)
        }
    }

    fun getSavedUsername(context: Context): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_USERNAME, null)
    }

    fun getSavedPassword(context: Context): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_PASSWORD, null)
    }

    fun saveLanguage(context: Context, language: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_LANGUAGE, language)
        editor.apply()
    }

    fun getLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_LANGUAGE, "en") ?: "en"
    }
    fun getThemeColor(context: Context): Float {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getFloat(KEY_THEME_COLOR, 0.5f)
    }

}
