package com.preview.android.data.network.api

import com.preview.android.data.dto.config.ConfigDto
import retrofit2.http.GET

interface ConfigApi {

    @GET("configuration")
    suspend fun getConfig(): ConfigDto
}