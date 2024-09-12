package com.cathay.travel.utils

import android.content.Context
import android.content.SharedPreferences
import com.cathay.travel.model.lang.Language

/**
 * SharedPreference 管理工具
 */
object SharedPreferenceUtil {
    private const val PREFERENCE_NAME = "travel_preference"
    private const val PREF_LANGUAGE_CODE = "pref_language_code"

    /**
     * 取得語言代碼
     */
    fun getLanguageCode(context: Context): String? =
        getString(context, PREF_LANGUAGE_CODE, Language.ZhTw.langTag)

    /**
     * 儲存語言代碼
     */
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