package com.preview.android.domain.usecases.user

import com.preview.android.domain.models.user.UserPhone
import com.preview.android.domain.repositories.UserRepository
import com.preview.android.domain.usecases.AbstractUseCase
import javax.inject.Inject

class GetPhoneUseCase @Inject constructor(
    private val repository: UserRepository
): AbstractUseCase<Nothing, UserPhone?>() {

    override suspend fun execute(parameters: Nothing?): UserPhone? {
        return repository.getPhone()
    }
}