package com.preview.android.domain.repositories

import com.preview.android.domain.models.auth.Action

interface AuthRepository {

    suspend fun getAction(): Action?

    suspend fun register(phone: String): Action?

    suspend fun resendCode(phone: String): Action?

    suspend fun confirmCode(code: String, phone: String, advertisingId: String, appsFlyerId: String): Boolean
}