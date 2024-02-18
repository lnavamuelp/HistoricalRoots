package com.lnavamuelp.historicalroots.di

import android.content.Context
import java.util.Locale

object LocaleUtils {

    fun setLocale(context: Context) {
        val language = UserPreferences.getLanguage(context)
        updateResources(context, language)
    }

    private fun updateResources(context: Context, language: String) {
        val locale = Locale(language)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}