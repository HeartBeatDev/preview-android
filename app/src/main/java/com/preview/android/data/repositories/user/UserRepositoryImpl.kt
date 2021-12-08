package com.preview.android.data.repositories.user

import com.preview.android.data.db.user.entities.UserEntity
import com.preview.android.data.mappers.mapToDomain
import com.preview.android.data.mappers.mapToPhoneDomain
import com.preview.android.domain.models.user.User
import com.preview.android.domain.models.user.UserPhone
import com.preview.android.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun getUser(): User? = withContext(Dispatchers.IO) {
        return@withContext localDataSource.getUser().mapToDomain()
    }

    override suspend fun savePhone(phone: UserPhone) = withContext(Dispatchers.IO) {
        val localUser: UserEntity = localDataSource.getUser()?.apply {
            this.code = phone.code
            this.number = phone.number
        } ?: UserEntity(code = phone.code, number = phone.number)
        localDataSource.saveUser(localUser)
    }

    override suspend fun getPhone(): UserPhone? = withContext(Dispatchers.IO) {
        return@withContext localDataSource.getUser().mapToPhoneDomain()
    }

    override suspend fun getAuthorizedState(): Boolean = withContext(Dispatchers.IO) {
        return@withContext localDataSource.getUser()?.isAuthorized ?: false
    }

    override suspend fun setAuthorizedState(isAuthorized: Boolean) = withContext(Dispatchers.IO) {
        val localUser: UserEntity = localDataSource.getUser()?.apply {
            this.isAuthorized = isAuthorized
        } ?: UserEntity(isAuthorized = isAuthorized)
        localDataSource.saveUser(localUser)
    }
}