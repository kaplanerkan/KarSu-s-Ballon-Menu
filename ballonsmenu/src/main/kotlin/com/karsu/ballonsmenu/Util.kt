/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import java.util.Random
import kotlin.math.abs

internal object Util {

    private var density: Float = -1f
    private val random: Random = Random()

    @JvmStatic
    fun dp2px(dpValue: Float): Int {
        if (density <= 0) density = Resources.getSystem().displayMetrics.density
        return (dpValue * density + 0.5f).toInt()
    }

    @JvmStatic
    fun px2dp(pxValue: Float): Float {
        if (density <= 0) density = Resources.getSystem().displayMetrics.density
        return pxValue / density
    }

    @JvmStatic
    @ColorInt
    fun getColor(): Int {
        return Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )
    }

    @JvmStatic
    @ColorInt
    fun getColor(context: Context, @ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(context, colorRes)
    }

    @JvmStatic
    @ColorInt
    fun getColor(context: Context, @ColorRes colorRes: Int, @ColorInt defaultColor: Int): Int {
        return if (colorRes == 0) defaultColor else ContextCompat.getColor(context, colorRes)
    }

    @JvmStatic
    fun getDrawable(
        view: View,
        @DrawableRes drawableRes: Int,
        defaultDrawable: Drawable?
    ): Drawable {
        return if (drawableRes == 0) defaultDrawable!!
        else ContextCompat.getDrawable(view.context, drawableRes)!!
    }

    @JvmStatic
    fun setDrawable(view: View, drawable: Drawable?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.background = drawable
        } else {
            @Suppress("DEPRECATION")
            view.setBackgroundDrawable(drawable)
        }
    }

    @JvmStatic
    fun getActivityFromView(view: View): Activity? {
        var context = view.context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

    @JvmStatic
    fun distance(p1x: Float, p1y: Float, p2x: Float, p2y: Float): Float {
        return abs(kotlin.math.hypot((p1x - p2x).toDouble(), (p1y - p2y).toDouble())).toFloat()
    }

    @JvmStatic
    fun getRootView(activity: Activity): ViewGroup? {
        return activity.window?.decorView?.findViewById(android.R.id.content)
    }
}
