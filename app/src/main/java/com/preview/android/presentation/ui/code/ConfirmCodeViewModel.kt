package com.preview.android.presentation.ui.code

import android.app.Application
import androidx.ads.identifier.AdvertisingIdClient.getAdvertisingIdInfo
import androidx.ads.identifier.AdvertisingIdInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appsflyer.AppsFlyerLib
import com.preview.android.domain.models.auth.Action
import com.preview.android.domain.models.config.Config
import com.preview.android.domain.models.user.UserPhone
import com.preview.android.domain.usecases.auth.ConfirmCodeParams
import com.preview.android.domain.usecases.auth.ConfirmCodeUseCase
import com.preview.android.domain.usecases.auth.GetActionUseCase
import com.preview.android.domain.usecases.auth.ResendCodeUseCase
import com.preview.android.domain.usecases.config.GetConfigUseCase
import com.preview.android.domain.usecases.user.GetPhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmCodeViewModel @Inject constructor(
    private val getPhoneUseCase: GetPhoneUseCase,
    private val getConfigUseCase: GetConfigUseCase,
    private val getActionUseCase: GetActionUseCase,
    private val resendCodeUseCase: ResendCodeUseCase,
    private val confirmCodeUseCase: ConfirmCodeUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val _state = MutableLiveData<ConfirmCodeState>()
    val state: LiveData<ConfirmCodeState>
        get() = _state

    private val _phone = MutableLiveData<UserPhone>()
    val phone: LiveData<UserPhone>
        get() = _phone

    private val _config = MutableLiveData<Config>()
    val config: LiveData<Config>
        get() = _config

    private val _action = MutableLiveData<Action>()
    val action: LiveData<Action>
        get() = _action

    fun init() = viewModelScope.launch {
        _phone.value = getPhoneUseCase.execute()
        _config.value = getConfigUseCase.execute()
        _action.value = getActionUseCase.execute()
    }

    fun resendCode() = viewModelScope.launch {
        try {
            _state.value = ConfirmCodeState.Loading
            val action = resendCodeUseCase.execute(_phone.value?.fullPhone)
            _state.value = ConfirmCodeState.ResendCodeSuccess
            _action.value = action
        } catch (e: Exception) {
            _state.value = ConfirmCodeState.ResendCodeError
        }
    }

    fun confirmCode(code: String) = viewModelScope.launch {
        try {
            _state.value = ConfirmCodeState.Loading
            val params = ConfirmCodeParams(
                code = code,
                phone = phone.value?.fullPhone.orEmpty().replace(" ", ""),
                advertisingId = getAdvertisingId(),
                appsFlyerId = AppsFlyerLib.getInstance().getAppsFlyerUID(getApplication<Application>().applicationContext)
            )
            val isAuthorized = confirmCodeUseCase.execute(params)
            if (isAuthorized) {
                _state.value = ConfirmCodeState.NavigateToOffers
            } else {
                _state.value = ConfirmCodeState.InvalidCodeError
            }
        } catch (e: Exception) {
            _state.value = ConfirmCodeState.ConfirmCodeError
        }
    }

    private suspend fun getAdvertisingId(): String {
        var adInfo: AdvertisingIdInfo? = null
        try {
            @Suppress("BlockingMethodInNonBlockingContext")
            adInfo = getAdvertisingIdInfo(getApplication<Application>().applicationContext).get()
        } catch (e: Exception) {}
        return adInfo?.id ?: "none"
    }
}