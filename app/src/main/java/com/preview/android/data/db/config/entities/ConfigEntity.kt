package com.preview.android.data.db.config.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConfigEntity(
    @PrimaryKey
    val id: Int = 1,
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
    @Embedded(prefix = "dialogPhone")
    val dialogPhone: ConfigDialogPhoneEntity,
    @Embedded(prefix = "dialogsPhone")
    val dialogsPhone: ConfigDialogsPhoneEntity,
    @Embedded(prefix = "dialogAction")
    val dialogAction: ConfigDialogActionEntity,
    val authTitleText: String,
    val authTitleColors: List<ConfigColorEntity>,
    val authSubTitleText: String,
    val authSubTitleColors: List<ConfigColorEntity>,
    val authChangeNumber: String,
    val authPPAccept: String,
    val authPPText: String,
    val authPPColors: List<ConfigColorEntity>,
    val authGetAccess: String,
    val authDemo: String
)
