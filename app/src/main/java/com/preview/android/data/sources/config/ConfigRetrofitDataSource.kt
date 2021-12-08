package com.preview.android.data.sources.config

import com.preview.android.data.dto.config.ConfigDto
import com.preview.android.data.network.api.ConfigApi
import com.preview.android.data.repositories.config.ConfigRemoteDataSource

class ConfigRetrofitDataSource(
    private val configApi: ConfigApi
): ConfigRemoteDataSource {

    override suspend fun loadConfig(): ConfigDto {
        return configApi.getConfig()
    }
}