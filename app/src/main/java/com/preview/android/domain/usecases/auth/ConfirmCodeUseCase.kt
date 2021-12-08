package com.preview.android.domain.usecases.auth

import com.preview.android.domain.repositories.AuthRepository
import com.preview.android.domain.repositories.UserRepository
import com.preview.android.domain.usecases.AbstractUseCase
import javax.inject.Inject

class ConfirmCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
): AbstractUseCase<ConfirmCodeParams, Boolean>() {

    override suspend fun execute(parameters: ConfirmCodeParams?): Boolean {
        val isAuthorized = authRepository.confirmCode(
            parameters?.code.orEmpty(),
            parameters?.phone.orEmpty(),
            parameters?.advertisingId.orEmpty(),
            parameters?.appsFlyerId.orEmpty()
        )
        userRepository.setAuthorizedState(isAuthorized)
        return isAuthorized
    }
}

data class ConfirmCodeParams(
    val code: String,
    val phone: String,
    val advertisingId: String,
    val appsFlyerId: String,
)