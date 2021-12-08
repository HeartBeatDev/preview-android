package com.preview.android.data.db.user.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val id: Int = 1,
    var code: Int = -1,
    var number: String = "",
    var isAuthorized: Boolean = false,
)