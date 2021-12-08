package com.preview.android.domain.models.common

import com.preview.android.domain.models.config.ConfigColor

data class ColoredContent(
    val txt: String,
    val colors: List<ConfigColor>
)
