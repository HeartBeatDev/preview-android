package com.preview.android.domain.usecases.auth

import com.preview.android.domain.models.auth.Action
import com.preview.android.domain.repositories.AuthRepository
import com.preview.android.domain.usecases.AbstractUseCase
import javax.inject.Inject

class ResendCodeUseCase @Inject constructor(
    private val repository: AuthRepository
): AbstractUseCase<String, Action?>() {

    override suspend fun execute(parameters: String?): Action? {
        return repository.resendCode(parameters.orEmpty())
    }
}