package com.preview.android.domain.models.auth

import com.preview.android.domain.models.common.ColoredContent
import com.preview.android.domain.models.config.ConfigColor
import com.preview.android.domain.models.user.UserPhone
import java.util.*

data class Action (
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
) {

    fun coolDownPassed(): Boolean {
        val now = System.currentTimeMillis()
        val passed = now - whenTime

        return actionIn * 1000 < passed
    }

    fun title(userPhone: UserPhone): ColoredContent {
        return if (coolDownPassed()) {
            val actionCalendar = Calendar.getInstance().apply { time = Date(whenTime) }
            val actionTimeString = String.format(
                "%02d:%02d", actionCalendar.get(Calendar.HOUR_OF_DAY),
                actionCalendar.get(Calendar.MINUTE)
            )

            val fullPhone = userPhone.fullPhone
            val text = String.format(Locale.getDefault(), titleExpired, actionTimeString, fullPhone)
            val hhIndex = text.indexOf(actionTimeString)
            val numberIndex = text.indexOf(fullPhone)

            ColoredContent(
                txt = text,
                colors = listOf(
                    ConfigColor(hhIndex, hhIndex + actionTimeString.length, "#F0DE40"),
                    ConfigColor(numberIndex, numberIndex + fullPhone.length, "#F0DE40")
                )
            )
        } else {
            val fullPhone = userPhone.fullPhone
            val text = String.format(Locale.getDefault(), titleActual, fullPhone)
            val numberIndex = text.indexOf(fullPhone)

            ColoredContent(
                txt = text,
                colors = listOf(
                    ConfigColor(numberIndex, numberIndex + fullPhone.length, "#F0DE40")
                )
            )
        }
    }

    fun nextCaption(): String {
        return if (coolDownPassed()) actionTextNext else {
            val timePassed = (System.currentTimeMillis() - whenTime) / 1000
            String.format(
                Locale.getDefault(), actionTextPatternNext,
                if (timePassed < 0) actionIn else actionIn - timePassed
            )
        }
    }
}
