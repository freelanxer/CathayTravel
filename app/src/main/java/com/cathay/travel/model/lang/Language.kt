package com.cathay.travel.model.lang

sealed class Language(
    val name: String,
    val langCode: String,
    val langTag: String
) {
    data object ZhTw : Language(name = "繁體中文", langCode = "zh-tw", langTag = "zh-TW")
    data object ZhCn : Language(name = "简体中文", langCode = "zh-cn", langTag = "zh")
    data object En : Language(name = "English", langCode = "en", langTag = "en")
    data object Ja : Language(name = "日本語", langCode = "ja", langTag = "ja")
    data object Ko : Language(name = "한국어", langCode = "ko", langTag = "ko")
    data object Es : Language(name = "Español", langCode = "es", langTag = "es")
    data object Id : Language(name = "Indonesia", langCode = "id", langTag = "in")
    data object Th : Language(name = "แบบไทย", langCode = "th", langTag = "th")
    data object Vi : Language(name = "Tiếng Việt", langCode = "vi", langTag = "vi")
}