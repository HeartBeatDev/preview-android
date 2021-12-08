package com.preview.android.data.repositories.user

import com.preview.android.data.db.user.entities.UserEntity

interface UserLocalDataSource {

    suspend fun getUser(): UserEntity?

    suspend fun saveUser(phone: UserEntity)
}