/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout

import com.karsu.ballonsmenu.Animation.AnimationManager

@SuppressLint("ViewConstructor")
internal class BackgroundView(
    context: Context,
    private val bmb: KarSuMenuButton
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

    fun reLayout(bmb: KarSuMenuButton) {
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
