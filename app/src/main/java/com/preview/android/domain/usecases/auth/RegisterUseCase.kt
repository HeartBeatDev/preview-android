package com.preview.android.domain.usecases.auth

import com.preview.android.domain.models.auth.Action
import com.preview.android.domain.repositories.AuthRepository
import com.preview.android.domain.repositories.UserRepository
import com.preview.android.domain.usecases.AbstractUseCase
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
): AbstractUseCase<String, Action?>() {

    override suspend fun execute(parameters: String?): Action? {
        val action = authRepository.register(parameters.orEmpty())
        userRepository.setAuthorizedState(action?.hasAccess == true)
        return action
    }
}