package com.preview.android.data.repositories.config

import com.preview.android.data.mappers.mapToDomain
import com.preview.android.data.mappers.mapToEntity
import com.preview.android.domain.models.config.Config
import com.preview.android.domain.repositories.ConfigRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConfigRepositoryImpl(
    private val remoteDataSource: ConfigRemoteDataSource,
    private val localDataSource: ConfigLocalDataSource
): ConfigRepository {

    override suspend fun loadConfig(): Config = withContext(Dispatchers.IO) {
        val localConfig = getConfig()
        val remoteConfig = remoteDataSource.loadConfig()
        localDataSource.saveConfig(remoteConfig.mapToEntity(localConfig?.accessFull))
        return@withContext remoteConfig.mapToDomain()
    }

    override suspend fun getConfig(): Config? = withContext(Dispatchers.IO) {
        val localConfig = localDataSource.getConfig()
        return@withContext localConfig.mapToDomain()
    }
}