package com.nightonke.boommenu

import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout

import com.nightonke.boommenu.Animation.AnimationManager

/**
 * Created by Weiping Huang at 11:47 on 2017/5/15
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 *
 * Converted to Kotlin
 */
@SuppressLint("ViewConstructor")
internal class BackgroundView(
    context: Context,
    private val bmb: BoomMenuButton
) : FrameLayout(context) {

    private val dimColor: Int = bmb.dimColor

    init {
        val rootView = bmb.parentView
        val params = ViewGroup.LayoutParams(
            rootView.width,
            rootView.height
        )
        layoutParams = params
        setBackgroundColor(Color.TRANSPARENT)
        setOnClickListener { bmb.onBackgroundClicked() }
        isMotionEventSplittingEnabled = false
        rootView.addView(this)
    }

    fun reLayout(bmb: BoomMenuButton) {
        val rootView = bmb.parentView
        val params = layoutParams as LayoutParams
        params.width = rootView.width
        params.height = rootView.height
        layoutParams = params
    }

    fun dim(duration: Long, completeListener: AnimatorListenerAdapter?) {
        visibility = VISIBLE
        AnimationManager.animate(
            this, "backgroundColor", 0, duration, ArgbEvaluator(), completeListener,
            Color.TRANSPARENT, dimColor
        )
    }

    fun light(duration: Long, completeListener: AnimatorListenerAdapter?) {
        AnimationManager.animate(
            this, "backgroundColor", 0, duration, ArgbEvaluator(), completeListener,
            dimColor, Color.TRANSPARENT
        )
    }
}
