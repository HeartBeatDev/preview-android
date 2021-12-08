package com.preview.android.data.db.auth.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActionEntity(
    @PrimaryKey
    val id: Int = 1,
    val hasNext: Boolean,
    val changePhone: String,
    val actionTextNext: String,
    val actionTextPatternNext: String,
    val titleActual: String,
    val titleExpired: String,
    val subTitle: String,
    val whenTime: Long,
    val actionIn: Int,
    val hasAccess: Boolean
)
