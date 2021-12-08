package com.preview.android.data.mappers

import com.preview.android.data.db.config.entities.*
import com.preview.android.data.dto.config.ConfigColorDto
import com.preview.android.data.dto.config.ConfigDto
import com.preview.android.domain.models.common.ColoredContent
import com.preview.android.domain.models.config.*

fun ConfigEntity?.mapToDomain(): Config? {
    return if (this == null) null else Config(
        accessFull = this.accessFull,
        keyAppsflyer = this.keyAppsflyer,
        keyOnesignal = this.keyOnesignal,
        countriesAllowed = this.countriesAllowed,
        urlSupport = this.urlSupport,
        linkPolicy = this.linkPolicy,
        slotsGameSpin = this.slotsGameSpin,
        slotsGameBalance = this.slotsGameBalance,
        slotsGameWin = this.slotsGameWin,
        slotsGameAuthorize = this.slotsGameAuthorize,
        slotsGameWelcome = this.slotsGameWelcome,
        slotsGameAvailableAfterAuth = this.slotsGameAvailableAfterAuth,
        slotsGameRateMinErr = this.slotsGameRateMinErr,
        slotsGameYourLevel = this.slotsGameYourLevel,
        textSupport = this.textSupport,
        errorNumber = this.errorNumber,
        errorPin = this.errorPin,
        errorServer = this.errorServer,
        dialogPhone = ConfigDialogPhone(this.dialogPhone.title, this.dialogPhone.message),
        dialogsPhone = ConfigDialogsPhone(this.dialogsPhone.no, this.dialogsPhone.yes),
        dialogAction = ConfigDialogAction(
            this.dialogAction.title,
            this.dialogAction.message,
            this.dialogAction.yes,
            this.dialogAction.no
        ),
        authTitle = ColoredContent(
            this.authTitleText,
            this.authTitleColors.mapToDomain()
        ),
        authSubTitle = ColoredContent(
            this.authSubTitleText,
            this.authSubTitleColors.mapToDomain()
        ),
        authChangeNumber = this.authChangeNumber,
        authPPAccept = this.authPPAccept,
        authPP = ColoredContent(
            this.authPPText,
            this.authPPColors.mapToDomain()
        ),
        authGetAccess = this.authGetAccess,
        authDemo = this.authDemo
    )
}

fun ConfigDto.mapToEntity(previouslyAccessState: Boolean?): ConfigEntity {
    return ConfigEntity(
        accessFull = if (previouslyAccessState == true) true else this.accessFull ?: false,
        keyAppsflyer = this.keyAppsflyer.orEmpty(),
        keyOnesignal = this.keyOnesignal.orEmpty(),
        countriesAllowed = this.countriesAllowed.orEmpty(),
        urlSupport = this.urlSupport.orEmpty(),
        linkPolicy = this.linkPolicy.orEmpty(),
        slotsGameSpin = this.locale?.slotsGameSpin.orEmpty(),
        slotsGameBalance = this.locale?.slotsGameBalance.orEmpty(),
        slotsGameWin = this.locale?.slotsGameWin.orEmpty(),
        slotsGameAuthorize = this.locale?.slotsGameAuthorize.orEmpty(),
        slotsGameWelcome = this.locale?.slotsGameWelcome.orEmpty(),
        slotsGameAvailableAfterAuth = this.locale?.slotsGameAvailableAfterAuth.orEmpty(),
        slotsGameRateMinErr = this.locale?.slotsGameRateMinErr.orEmpty(),
        slotsGameYourLevel = this.locale?.slotsGameYourLevel.orEmpty(),
        textSupport = this.locale?.textSupport.orEmpty(),
        errorNumber = this.locale?.errorNumber.orEmpty(),
        errorPin = this.locale?.errorPin.orEmpty(),
        errorServer = this.locale?.errorServer.orEmpty(),
        dialogPhone = ConfigDialogPhoneEntity(
            this.locale?.dialogPhone?.title.orEmpty(),
            this.locale?.dialogPhone?.message.orEmpty()
        ),
        dialogsPhone = ConfigDialogsPhoneEntity(
            this.locale?.dialogsPhone?.no.orEmpty(),
            this.locale?.dialogsPhone?.yes.orEmpty()
        ),
        dialogAction = ConfigDialogActionEntity(
            this.locale?.dialogAction?.title.orEmpty(),
            this.locale?.dialogAction?.message.orEmpty(),
            this.locale?.dialogAction?.yes.orEmpty(),
            this.locale?.dialogAction?.no.orEmpty()
        ),
        authTitleText = this.locale?.authTitle?.txt.orEmpty(),
        authTitleColors = this.locale?.authTitle?.colors?.mapToEntity() ?: emptyList(),
        authSubTitleText = this.locale?.authSubTitle?.txt.orEmpty(),
        authSubTitleColors = this.locale?.authSubTitle?.colors?.mapToEntity() ?: emptyList(),
        authChangeNumber = this.locale?.authChangeNumber.orEmpty(),
        authPPAccept = this.locale?.authPPAccept.orEmpty(),
        authPPText = this.locale?.authPP?.txt.orEmpty(),
        authPPColors = this.locale?.authPP?.colors?.mapToEntity() ?: emptyList(),
        authGetAccess = this.locale?.authGetAccess.orEmpty(),
        authDemo = this.locale?.authDemo.orEmpty()
    )
}

fun ConfigDto.mapToDomain(): Config {
    return Config(
        accessFull = this.accessFull ?: false,
        keyAppsflyer = this.keyAppsflyer.orEmpty(),
        keyOnesignal = this.keyOnesignal.orEmpty(),
        countriesAllowed = this.countriesAllowed.orEmpty(),
        urlSupport = this.urlSupport.orEmpty(),
        linkPolicy = this.linkPolicy.orEmpty(),
        slotsGameSpin = this.locale?.slotsGameSpin.orEmpty(),
        slotsGameBalance = this.locale?.slotsGameBalance.orEmpty(),
        slotsGameWin = this.locale?.slotsGameWin.orEmpty(),
        slotsGameAuthorize = this.locale?.slotsGameAuthorize.orEmpty(),
        slotsGameWelcome = this.locale?.slotsGameWelcome.orEmpty(),
        slotsGameAvailableAfterAuth = this.locale?.slotsGameAvailableAfterAuth.orEmpty(),
        slotsGameRateMinErr = this.locale?.slotsGameRateMinErr.orEmpty(),
        slotsGameYourLevel = this.locale?.slotsGameYourLevel.orEmpty(),
        textSupport = this.locale?.textSupport.orEmpty(),
        errorNumber = this.locale?.errorNumber.orEmpty(),
        errorPin = this.locale?.errorPin.orEmpty(),
        errorServer = this.locale?.errorServer.orEmpty(),
        dialogPhone = ConfigDialogPhone(
            this.locale?.dialogPhone?.title.orEmpty(),
            this.locale?.dialogPhone?.message.orEmpty()
        ),
        dialogsPhone = ConfigDialogsPhone(
            this.locale?.dialogsPhone?.no.orEmpty(),
            this.locale?.dialogsPhone?.yes.orEmpty()
        ),
        dialogAction = ConfigDialogAction(
            this.locale?.dialogAction?.title.orEmpty(),
            this.locale?.dialogAction?.message.orEmpty(),
            this.locale?.dialogAction?.yes.orEmpty(),
            this.locale?.dialogAction?.no.orEmpty()
        ),
        authTitle = ColoredContent(
            this.locale?.authTitle?.txt.orEmpty(),
            this.locale?.authTitle?.colors?.mapDtoToDomain().orEmpty()
        ),
        authSubTitle = ColoredContent(
            this.locale?.authSubTitle?.txt.orEmpty(),
            this.locale?.authSubTitle?.colors?.mapDtoToDomain().orEmpty()
        ),
        authChangeNumber = this.locale?.authChangeNumber.orEmpty(),
        authPPAccept = this.locale?.authPPAccept.orEmpty(),
        authPP = ColoredContent(
            this.locale?.authPP?.txt.orEmpty(),
            this.locale?.authPP?.colors?.mapDtoToDomain().orEmpty()
        ),
        authGetAccess = this.locale?.authGetAccess.orEmpty(),
        authDemo = this.locale?.authDemo.orEmpty()
    )
}

private fun List<ConfigColorEntity>.mapToDomain(): List<ConfigColor> {
    return this.map {
        ConfigColor(it.strt, it.end, it.hex)
    }
}

private fun List<ConfigColorDto>.mapToEntity(): List<ConfigColorEntity> {
    return this.map {
        ConfigColorEntity(it.strt ?: -1, it.end ?: -1, it.hex ?: "")
    }
}

private fun List<ConfigColorDto>.mapDtoToDomain(): List<ConfigColor> {
    return this.map {
        ConfigColor(it.strt ?: -1, it.end ?: -1, it.hex ?: "")
    }
}