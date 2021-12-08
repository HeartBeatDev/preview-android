package com.preview.android.data.repositories.config

import com.preview.android.data.db.config.entities.ConfigEntity

interface ConfigLocalDataSource {

    suspend fun getConfig(): ConfigEntity?

    suspend fun saveConfig(config: ConfigEntity)
}