package com.preview.android.data.repositories.config

import com.preview.android.data.dto.config.ConfigDto

interface ConfigRemoteDataSource {

    suspend fun loadConfig(): ConfigDto
}