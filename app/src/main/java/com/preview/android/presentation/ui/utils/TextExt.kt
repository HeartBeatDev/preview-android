package com.preview.android.presentation.ui.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.preview.android.R
import com.preview.android.domain.models.config.ConfigColor

fun TextView.applyRegularStyle() {
    typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)
}

fun TextView.applyBoldStyle() {
    typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)
}

fun TextView.setColoredText(
    content: String,
    colors: List<ConfigColor>,
    isUnderlineEnabled: Boolean = false
) {
    val spannable = SpannableString(content)
    for (span in colors) {
        val color = Color.parseColor(span.hex)
        spannable.setSpan(
            ForegroundColorSpan(color),
            span.strt,
            span.end,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    if (isUnderlineEnabled) spannable.setSpan(UnderlineSpan(),0, content.length,0)
    text = spannable
}

fun TextView.setTextWithUnderline(content: String) {
    val spannable = SpannableString(content)
    spannable.setSpan(UnderlineSpan(),0, content.length,0)
    text = spannable
}