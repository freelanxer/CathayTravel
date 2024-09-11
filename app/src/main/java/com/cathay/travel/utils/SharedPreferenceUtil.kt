package com.cathay.travel.utils

import android.content.Context
import android.content.SharedPreferences
import com.cathay.travel.model.lang.Language

object SharedPreferenceUtil {
    private const val PREFERENCE_NAME = "travel_preference"
    private const val PREF_LANGUAGE_CODE = "pref_language_code"

    fun getLanguageCode(context: Context): String? =
        getString(context, PREF_LANGUAGE_CODE, Language.ZhTw.langTag)

    fun setLanguageCode(context: Context, langCode: String) =
        setString(context, PREF_LANGUAGE_CODE, langCode)

    private fun getPreference(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    private fun setString(
        context: Context,
        key: String,
        value: String
    ) {
        val pref = getPreference(context)
        pref.edit().putString(key, value).apply()
    }

    private fun getString(
        context: Context,
        key: String,
        defaultValue: String?
    ): String? {
        val pref = getPreference(context)
        return pref.getString(key, defaultValue)
    }

}