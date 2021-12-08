package com.preview.android.presentation.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preview.android.domain.models.config.Config
import com.preview.android.domain.models.user.UserPhone
import com.preview.android.domain.usecases.auth.RegisterUseCase
import com.preview.android.domain.usecases.config.GetConfigUseCase
import com.preview.android.domain.usecases.user.GetPhoneUseCase
import com.preview.android.domain.usecases.user.SavePhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val getConfigUseCase: GetConfigUseCase,
    private val savePhoneUseCase: SavePhoneUseCase,
    private val getPhoneUseCase: GetPhoneUseCase,
    private val registerUserCase: RegisterUseCase,
): ViewModel() {

    private val _state = MutableLiveData<RegistrationState>()
    val state: LiveData<RegistrationState>
        get() = _state

    private val _config = MutableLiveData<Config>()
    val config: LiveData<Config>
        get() = _config

    private val _phone = MutableLiveData<UserPhone>()
    val phone: LiveData<UserPhone>
        get() = _phone

    fun init() = viewModelScope.launch  {
        getConfig()
        getSavedPhone()
    }

    private suspend fun getConfig() {
        _config.value = getConfigUseCase.execute()
    }

    private suspend fun getSavedPhone() {
        _phone.value = getPhoneUseCase.execute()
    }

    fun saveCurrentPhone(code: Int, number: String) = viewModelScope.launch {
        val params = UserPhone(code, number)
        savePhoneUseCase.execute(params)
    }

    fun register(phone: String) = viewModelScope.launch {
        try {
            _state.value = RegistrationState.Loading
            val action = registerUserCase.execute(phone)
            if (action?.hasAccess == true) {
                _state.value = RegistrationState.NavigateToOffers
            } else {
                _state.value = RegistrationState.NavigateToConfirmCode
            }
        } catch (e: Exception) {
            _state.value = RegistrationState.Error
        }
    }
}