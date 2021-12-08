package com.preview.android.data.mappers

import com.preview.android.data.db.user.entities.UserEntity
import com.preview.android.domain.models.user.User
import com.preview.android.domain.models.user.UserPhone

fun UserEntity?.mapToDomain(): User? {
    return if (this == null) null else User(
        phone = UserPhone(this.code, this.number),
        isAuthorized = isAuthorized
    )
}

fun UserEntity?.mapToPhoneDomain(): UserPhone? {
    return if (this == null) null else UserPhone(
        code = this.code,
        number = this.number
    )
}