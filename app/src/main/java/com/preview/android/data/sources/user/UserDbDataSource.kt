package com.preview.android.data.sources.user

import com.preview.android.data.db.user.UserDao
import com.preview.android.data.db.user.entities.UserEntity
import com.preview.android.data.repositories.user.UserLocalDataSource

class UserDbDataSource(
    private val userDao: UserDao
): UserLocalDataSource {

    override suspend fun getUser(): UserEntity? {
        return userDao.getUser()
    }

    override suspend fun saveUser(phone: UserEntity) {
        userDao.insert(phone)
    }
}