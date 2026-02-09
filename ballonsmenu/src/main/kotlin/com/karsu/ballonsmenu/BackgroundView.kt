/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import com.karsu.ballonsmenu.animation.AnimationManager

@SuppressLint("ViewConstructor")
internal class BackgroundView(
    context: Context,
    private val bmb: KarSuMenuButton
) : FrameLayout(context) {

    private val dimColor: Int = bmb.dimColor

    init {
        val rootView = bmb.parentView
        val w = rootView.width
        val h = rootView.height
        val params = ViewGroup.LayoutParams(w, h)
        layoutParams = params
        setBackgroundColor(Color.TRANSPARENT)
        setOnClickListener { bmb.onBackgroundClicked() }
        isMotionEventSplittingEnabled = false
        rootView.addView(this)
        forceLayout(w, h)
    }

    fun reLayout(bmb: KarSuMenuButton) {
        val rootView = bmb.parentView
        val w = rootView.width
        val h = rootView.height
        val params = layoutParams
        params.width = w
        params.height = h
        layoutParams = params
        forceLayout(w, h)
    }

    private fun forceLayout(w: Int, h: Int) {
        measure(
            View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(h, View.MeasureSpec.EXACTLY)
        )
        layout(0, 0, w, h)
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
