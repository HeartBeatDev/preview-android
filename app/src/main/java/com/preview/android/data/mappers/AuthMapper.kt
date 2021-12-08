package com.preview.android.data.mappers

import com.preview.android.data.db.auth.entities.ActionEntity
import com.preview.android.data.dto.auth.ActionDto
import com.preview.android.domain.models.auth.Action

fun ActionDto?.mapToDomain(): Action? {
    return if (this == null) null else Action(
        hasNext = this.hasNext ?: false,
        changePhone = this.changePhone.orEmpty(),
        actionTextNext = this.actionTextNext.orEmpty(),
        actionTextPatternNext = this.actionTextPatternNext.orEmpty(),
        titleActual = this.titleActual.orEmpty(),
        titleExpired = this.titleExpired.orEmpty(),
        subTitle = this.subTitle.orEmpty(),
        whenTime = this.whenTime ?: -1,
        actionIn = this.actionIn ?: -1,
        hasAccess = this.hasAccess ?: false
    )
}

fun ActionDto?.mapToEntity(): ActionEntity? {
    return if (this == null) null else ActionEntity(
        hasNext = this.hasNext ?: false,
        changePhone = this.changePhone.orEmpty(),
        actionTextNext = this.actionTextNext.orEmpty(),
        actionTextPatternNext = this.actionTextPatternNext.orEmpty(),
        titleActual = this.titleActual.orEmpty(),
        titleExpired = this.titleExpired.orEmpty(),
        subTitle = this.subTitle.orEmpty(),
        whenTime = this.whenTime ?: -1,
        actionIn = this.actionIn ?: -1,
        hasAccess = this.hasAccess ?: false
    )
}

fun ActionEntity?.mapToDomain(): Action? {
    return if (this == null) null else Action(
        hasNext = this.hasNext,
        changePhone = this.changePhone,
        actionTextNext = this.actionTextNext,
        actionTextPatternNext = this.actionTextPatternNext,
        titleActual = this.titleActual,
        titleExpired = this.titleExpired,
        subTitle = this.subTitle,
        whenTime = this.whenTime,
        actionIn = this.actionIn,
        hasAccess = this.hasAccess
    )
}