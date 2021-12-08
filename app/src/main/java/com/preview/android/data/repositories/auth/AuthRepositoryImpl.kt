package com.preview.android.data.repositories.auth

import com.preview.android.data.mappers.mapToDomain
import com.preview.android.data.mappers.mapToEntity
import com.preview.android.domain.models.auth.Action
import com.preview.android.domain.repositories.AuthRepository
import com.onesignal.OneSignal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
): AuthRepository {

    override suspend fun getAction(): Action? = withContext(Dispatchers.IO) {
        val localAction = authLocalDataSource.getAction()
        return@withContext localAction.mapToDomain()
    }

    override suspend fun register(phone: String): Action? = withContext(Dispatchers.IO) {
        val remoteAction = authRemoteDataSource.register(phone)
        remoteAction.mapToEntity()?.also {
            authLocalDataSource.saveAction(it)
        }
        return@withContext remoteAction.mapToDomain()
    }

    override suspend fun resendCode(phone: String): Action? = withContext(Dispatchers.IO) {
        val remoteAction = authRemoteDataSource.resendCode(phone)
        remoteAction.mapToEntity()?.also {
            authLocalDataSource.saveAction(it)
        }
        return@withContext remoteAction.mapToDomain()
    }

    override suspend fun confirmCode(
        code: String,
        phone: String,
        advertisingId: String,
        appsFlyerId: String
    ): Boolean = withContext(Dispatchers.IO) {
        val paramOs: String = OneSignal.getDeviceState()?.userId ?: "none"
        return@withContext authRemoteDataSource.confirmCode(code, appsFlyerId, advertisingId, paramOs, phone).paramValidity
    }
}