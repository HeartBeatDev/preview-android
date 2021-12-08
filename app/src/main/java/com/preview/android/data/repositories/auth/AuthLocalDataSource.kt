package com.preview.android.data.repositories.auth

import com.preview.android.data.db.auth.entities.ActionEntity

interface AuthLocalDataSource {

    suspend fun getAction(): ActionEntity?

    suspend fun saveAction(action: ActionEntity)
}