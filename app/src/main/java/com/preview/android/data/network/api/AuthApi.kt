package com.preview.android.data.network.api

import com.preview.android.data.dto.auth.ActionDto
import com.preview.android.data.dto.auth.ConfirmCodeRequest
import com.preview.android.data.dto.auth.ConfirmCodeResponse
import retrofit2.http.*

interface AuthApi {

    @GET("pAction")
    suspend fun getCurrentAction(@Query("paramPhone") phone: String): ActionDto?

    @GET("nAction")
    suspend fun getNextAction(@Query("paramPhone") phone: String): ActionDto?

    @Headers("Content-Type: application/json")
    @POST("checkCode")
    suspend fun confirmCode(@Body request: ConfirmCodeRequest): ConfirmCodeResponse
}