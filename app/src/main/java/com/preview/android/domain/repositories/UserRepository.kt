package com.preview.android.domain.repositories

import com.preview.android.domain.models.user.User
import com.preview.android.domain.models.user.UserPhone

interface UserRepository {

    suspend fun getUser(): User?

    suspend fun savePhone(phone: UserPhone)

    suspend fun getPhone(): UserPhone?

    suspend fun getAuthorizedState(): Boolean

    suspend fun setAuthorizedState(isAuthorized: Boolean)
}