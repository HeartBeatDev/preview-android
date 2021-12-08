package com.preview.android.presentation.ui.code

sealed class ConfirmCodeState {

    object Loading : ConfirmCodeState()

    object ResendCodeSuccess : ConfirmCodeState()

    object NavigateToOffers : ConfirmCodeState()

    object InvalidCodeError : ConfirmCodeState()

    object ConfirmCodeError : ConfirmCodeState()

    object ResendCodeError : ConfirmCodeState()
}