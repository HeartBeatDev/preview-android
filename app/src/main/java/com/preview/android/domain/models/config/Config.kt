package com.preview.android.domain.models.config

import com.preview.android.domain.models.common.ColoredContent

data class Config(
    val accessFull: Boolean,
    val keyAppsflyer: String,
    val keyOnesignal: String,
    val countriesAllowed: String,
    val urlSupport: String,
    val linkPolicy: String,
    val slotsGameSpin: String,
    val slotsGameBalance: String,
    val slotsGameWin: String,
    val slotsGameAuthorize: String,
    val slotsGameWelcome: String,
    val slotsGameAvailableAfterAuth: String,
    val slotsGameRateMinErr: String,
    val slotsGameYourLevel: String,
    val textSupport: String,
    val errorNumber: String,
    val errorPin: String,
    val errorServer: String,
    val dialogPhone: ConfigDialogPhone,
    val dialogsPhone: ConfigDialogsPhone,
    val dialogAction: ConfigDialogAction,
    val authTitle: ColoredContent,
    val authSubTitle: ColoredContent,
    val authChangeNumber: String,
    val authPPAccept: String,
    val authPP: ColoredContent,
    val authGetAccess: String,
    val authDemo: String
)