package com.cathay.travel.utils

import android.content.Context
import java.util.Locale

object LanguageUtil {
    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resource = context.resources
        val config = resource.configuration
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}