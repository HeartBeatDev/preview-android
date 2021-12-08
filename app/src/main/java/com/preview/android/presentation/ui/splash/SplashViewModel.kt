package com.preview.android.presentation.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preview.android.domain.models.config.Config
import com.preview.android.domain.usecases.auth.GetActionUseCase
import com.preview.android.domain.usecases.config.GetConfigUseCase
import com.preview.android.domain.usecases.config.LoadConfigUseCase
import com.preview.android.domain.usecases.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loadConfigUseCase: LoadConfigUseCase,
    private val getConfigUseCase: GetConfigUseCase,
    private val getActionUseCase: GetActionUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    private val _state = MutableLiveData<SplashState>()
    val state: LiveData<SplashState>
        get() = _state

    private val _config = MutableLiveData<Config>()
    val config: LiveData<Config>
        get() = _config

    fun init()  = viewModelScope.launch {
        try {
            _config.value = loadConfigUseCase.execute()
            val localAction = getActionUseCase.execute()
            val localUser = getUserUseCase.execute()
            _state.value = when {
                localAction == null -> SplashState.NavigateToRegistration
                localUser?.isAuthorized == true -> SplashState.NavigateToOffers
                else -> SplashState.NavigateToConfirmCode
            }
        } catch (e: Exception) {
            val localConfig = getConfigUseCase.execute()
            if (localConfig == null) {
                _state.value = SplashState.Error
            } else {
                _state.value = SplashState.NavigateToRegistration
            }
        }
    }
}