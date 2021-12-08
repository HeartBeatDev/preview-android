package com.preview.android.presentation.ui.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import com.preview.android.R


fun View.slideUp(
    distance: Float,
    onAnimationStart: () -> Unit = {},
    onAnimationEnd: () -> Unit = {},
    duration: Long = 400,
) {
    animate()
        .translationY(-distance)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                onAnimationStart()
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                onAnimationEnd()
            }
        })
}

fun View.slideDown(
    distance: Float,
    onAnimationStart: () -> Unit = {},
    onAnimationEnd: () -> Unit = {},
    duration: Long = 400,
) {
    animate()
        .translationYBy(distance)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                onAnimationStart()
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                onAnimationEnd()
            }
        })
}

@SuppressLint("ClickableViewAccessibility")
fun View.applyButtonClickAnimation() {
    val scaleUp = AnimationUtils.loadAnimation(context, R.anim.button_scale_up).apply {
        duration = 100
        isFillEnabled = true
        fillAfter = true
    }
    val scaleDown = AnimationUtils.loadAnimation(context, R.anim.button_scale_down).apply {
        duration = 100
        isFillEnabled = true
        fillAfter = true
    }
    setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            startAnimation(scaleUp)
        } else if (event.action == MotionEvent.ACTION_DOWN) {
            startAnimation(scaleDown)
        }
        false
    }
}