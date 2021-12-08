package com.preview.android.data.dto.auth

data class ActionDto(
    val hasNext: Boolean?,
    val changePhone: String?,
    val actionTextNext: String?,
    val actionTextPatternNext: String?,
    val titleActual: String?,
    val titleExpired: String?,
    val subTitle: String?,
    val whenTime: Long?,
    val actionIn: Int?,
    val hasAccess: Boolean?
)
