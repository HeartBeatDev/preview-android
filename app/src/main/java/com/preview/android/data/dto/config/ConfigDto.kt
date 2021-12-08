package com.preview.android.data.dto.config

data class ConfigDto(
    val accessFull: Boolean?,
    val keyOnesignal: String?,
    val keyAppsflyer: String?,
    val countriesAllowed: String?,
    val urlSupport: String?,
    val linkPolicy: String?,
    val locale: ConfigLocaleDto?
)