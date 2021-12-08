package com.preview.android.data.dto.auth

import com.google.gson.annotations.SerializedName

data class ConfirmCodeRequest(
    val paramCode: String,
    val paramsApps: String,
    @SerializedName("paramAID")
    val paramAId: String,
    @SerializedName("paramOS")
    val paramOs: String,
    val paramPhone: String
)
