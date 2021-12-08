package com.preview.android.domain.usecases.user

import com.preview.android.domain.models.user.User
import com.preview.android.domain.repositories.UserRepository
import com.preview.android.domain.usecases.AbstractUseCase
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
): AbstractUseCase<Nothing, User?>() {

    override suspend fun execute(parameters: Nothing?): User? {
        return repository.getUser()
    }
}