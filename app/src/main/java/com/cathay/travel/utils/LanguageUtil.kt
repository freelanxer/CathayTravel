package com.cathay.travel.utils

import android.content.Context
import com.cathay.travel.model.lang.Language
import java.util.Locale

/**
 * 語言相關工具
 */
object LanguageUtil {
    /**
     * 語言列表
     */
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

    /**
     * 設定地區至 resource config
     */
    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resource = context.resources
        val config = resource.configuration
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    /**
     * 轉換語言Tag成語言代碼
     * 因 Android 識別的 tag 與 API 定義不同
     */
    fun convertLangTagToCode(langTag: String): String {
        return when(langTag) {
            Language.ZhTw.langTag -> Language.ZhTw.langCode
            Language.ZhCn.langTag -> Language.ZhCn.langCode
            else -> langTag
        }
    }
}