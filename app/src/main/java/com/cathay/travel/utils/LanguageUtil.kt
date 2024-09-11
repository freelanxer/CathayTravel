package com.cathay.travel.utils

import android.content.Context
import com.cathay.travel.model.lang.Language
import java.util.Locale

object LanguageUtil {
    val langList get() = listOf(
        Language.ZhTw,
        Language.ZhCn,
        Language.En,
        Language.Ja,
        Language.Ko,
        Language.Es,
        Language.Id,
        Language.Th,
        Language.Vi
    )

    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resource = context.resources
        val config = resource.configuration
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    fun convertLangTagToCode(langTag: String): String {
        return when(langTag) {
            Language.ZhTw.langTag -> Language.ZhTw.langCode
            Language.ZhCn.langTag -> Language.ZhCn.langCode
            else -> langTag
        }
    }
}