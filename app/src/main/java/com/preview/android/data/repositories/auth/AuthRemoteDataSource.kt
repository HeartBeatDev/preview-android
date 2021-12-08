package com.preview.android.data.repositories.auth

import com.preview.android.data.dto.auth.ActionDto
import com.preview.android.data.dto.auth.ConfirmCodeResponse

interface AuthRemoteDataSource {

    suspend fun register(phone: String): ActionDto?

    suspend fun resendCode(phone: String): ActionDto?

    suspend fun confirmCode(
        code: String,
        paramsApps: String,
        paramAId: String,
        paramOs: String,
        paramPhone: String
    ): ConfirmCodeResponse
}