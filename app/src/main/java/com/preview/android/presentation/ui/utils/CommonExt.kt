package com.preview.android.presentation.ui.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.initKeyboardStateListener(state: (Boolean) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener {
        val rec = Rect()
        getWindowVisibleDisplayFrame(rec)
        val screenHeight = rootView.height
        val keypadHeight = screenHeight - rec.bottom
        state(keypadHeight > screenHeight * 0.15)
    }
}

fun View.hideKeyboard() {
    val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun View.isIntersectedWith(otherView: View): Boolean {
    val rect = Rect()
    this.getHitRect(rect)

    val otherViewRect = Rect()
    otherView.getHitRect(otherViewRect)

    return Rect.intersects(rect, otherViewRect)
}

fun View.visible() {
    isVisible = true
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    isVisible = false
}