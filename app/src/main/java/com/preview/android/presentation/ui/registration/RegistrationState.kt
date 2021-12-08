package com.preview.android.presentation.ui.registration

sealed class RegistrationState {

    object Loading : RegistrationState()

    object NavigateToOffers : RegistrationState()

    object NavigateToConfirmCode : RegistrationState()

    object Error : RegistrationState()
}