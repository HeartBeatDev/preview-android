package com.preview.android.domain.models.user

data class UserPhone(
    val code: Int,
    val number: String,
) {
    val fullPhone: String
        get() = "+$code $number"
}