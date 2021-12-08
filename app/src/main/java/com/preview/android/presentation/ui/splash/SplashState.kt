package com.preview.android.presentation.ui.splash

sealed class SplashState {

    object NavigateToRegistration : SplashState()

    object NavigateToConfirmCode : SplashState()

    object NavigateToOffers : SplashState()

    object Error : SplashState()
}