package com.preview.android.data.sources.auth

import com.preview.android.data.db.auth.ActionDao
import com.preview.android.data.db.auth.entities.ActionEntity
import com.preview.android.data.repositories.auth.AuthLocalDataSource

class AuthDbDataSource(
    private val actionDao: ActionDao
): AuthLocalDataSource {

    override suspend fun getAction(): ActionEntity? {
        return actionDao.getAction()
    }

    override suspend fun saveAction(action: ActionEntity) {
        actionDao.insert(action)
    }
}