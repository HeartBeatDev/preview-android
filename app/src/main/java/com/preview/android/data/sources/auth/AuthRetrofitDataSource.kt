package com.preview.android.data.sources.auth

import com.preview.android.data.dto.auth.ActionDto
import com.preview.android.data.dto.auth.ConfirmCodeRequest
import com.preview.android.data.dto.auth.ConfirmCodeResponse
import com.preview.android.data.network.api.AuthApi
import com.preview.android.data.repositories.auth.AuthRemoteDataSource

class AuthRetrofitDataSource(
    private val authApi: AuthApi
): AuthRemoteDataSource {

    override suspend fun register(phone: String): ActionDto? {
        return authApi.getCurrentAction(phone)
    }

    override suspend fun resendCode(phone: String): ActionDto? {
        return authApi.getNextAction(phone)
    }

    override suspend fun confirmCode(
        code: String,
        paramsApps: String,
        paramAId: String,
        paramOs: String,
        paramPhone: String
    ): ConfirmCodeResponse {
        val request = ConfirmCodeRequest(
            paramCode = code,
            paramsApps = paramsApps,
            paramAId = paramAId,
            paramOs = paramOs,
            paramPhone = paramPhone
        )
        return authApi.confirmCode(request)
    }
}