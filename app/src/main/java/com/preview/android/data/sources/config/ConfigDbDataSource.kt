package com.preview.android.data.sources.config

import com.preview.android.data.db.config.ConfigDao
import com.preview.android.data.db.config.entities.ConfigEntity
import com.preview.android.data.repositories.config.ConfigLocalDataSource

class ConfigDbDataSource(
    private val configDao: ConfigDao
): ConfigLocalDataSource {

    override suspend fun getConfig(): ConfigEntity? {
        return configDao.getConfig()
    }

    override suspend fun saveConfig(config: ConfigEntity) {
        configDao.insert(config)
    }
}