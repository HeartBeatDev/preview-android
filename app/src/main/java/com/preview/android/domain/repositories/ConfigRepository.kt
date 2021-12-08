package com.preview.android.domain.repositories

import com.preview.android.domain.models.config.Config

interface ConfigRepository {

    suspend fun loadConfig(): Config

    suspend fun getConfig(): Config?
}