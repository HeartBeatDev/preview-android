package com.preview.android.domain.usecases.user

import com.preview.android.domain.models.user.UserPhone
import com.preview.android.domain.repositories.UserRepository
import com.preview.android.domain.usecases.AbstractUseCase
import javax.inject.Inject

class SavePhoneUseCase @Inject constructor(
    private val repository: UserRepository
): AbstractUseCase<UserPhone, Unit>() {

    override suspend fun execute(parameters: UserPhone?) {
        parameters?.also { repository.savePhone(it) }
    }
}