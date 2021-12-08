package com.preview.android.data.dto.config

import com.google.gson.annotations.SerializedName

data class ConfigLocaleDto(
    @SerializedName("slotsGame_spin")
    val slotsGameSpin: String?,
    @SerializedName("slotsGame_balance")
    val slotsGameBalance: String?,
    @SerializedName("slotsGame_win")
    val slotsGameWin: String?,
    @SerializedName("slotsGame_authorize")
    val slotsGameAuthorize: String?,
    @SerializedName("slotsGame_welcome")
    val slotsGameWelcome: String?,
    @SerializedName("slotsGame_available_after_auth")
    val slotsGameAvailableAfterAuth: String?,
    @SerializedName("slotsGame_rate_min_err")
    val slotsGameRateMinErr: String?,
    @SerializedName("slotsGame_your_level")
    val slotsGameYourLevel: String?,
    val textSupport: String?,
    val errorNumber: String?,
    val errorPin: String?,
    val errorServer: String?,
    val dialogPhone: ConfigDialogPhoneDto?,
    val dialogsPhone: ConfigDialogsPhoneDto?,
    val dialogAction: ConfigDialogActionDto?,
    val authTitle: ConfigAuthPairDto?,
    val authSubTitle: ConfigAuthPairDto?,
    val authChangeNumber: String?,
    val authPPAccept: String?,
    val authPP: ConfigAuthPairDto?,
    val authGetAccess: String?,
    val authDemo: String?
)