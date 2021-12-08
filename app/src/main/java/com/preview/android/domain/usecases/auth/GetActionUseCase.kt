package com.preview.android.domain.usecases.auth

import com.preview.android.domain.models.auth.Action
import com.preview.android.domain.repositories.AuthRepository
import com.preview.android.domain.usecases.AbstractUseCase
import javax.inject.Inject

class GetActionUseCase @Inject constructor(
    private val repository: AuthRepository
): AbstractUseCase<Nothing, Action?>() {

    override suspend fun execute(parameters: Nothing?): Action? {
        return repository.getAction()
    }
}