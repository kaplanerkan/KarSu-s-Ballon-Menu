/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import java.util.Random
import kotlin.math.abs

object Util {

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
    fun setDrawable(view: View?, drawable: Drawable?) {
        if (view == null) return
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
        return activity.window?.decorView as? ViewGroup
    }

    @JvmStatic
    fun distance(p1: Point, p2: Point): Float {
        return distance(p1.x.toFloat(), p1.y.toFloat(), p2.x.toFloat(), p2.y.toFloat())
    }

    @JvmStatic
    fun getOvalDrawable(view: View?, @ColorInt color: Int): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.OVAL
        drawable.setColor(color)
        return drawable
    }

    @JvmStatic
    fun getRectangleDrawable(view: View?, cornerRadius: Int, @ColorInt color: Int): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.cornerRadius = cornerRadius.toFloat()
        drawable.setColor(color)
        return drawable
    }

    @JvmStatic
    fun getOvalStateListBitmapDrawable(
        view: View?,
        radius: Int,
        @ColorInt normalColor: Int,
        @ColorInt highlightedColor: Int,
        @ColorInt unableColor: Int
    ): StateListDrawable {
        val stateListDrawable = StateListDrawable()
        val diameter = radius * 2

        // Unable state
        val unableBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        val unableCanvas = Canvas(unableBitmap)
        val unablePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        unablePaint.color = unableColor
        unableCanvas.drawOval(RectF(0f, 0f, diameter.toFloat(), diameter.toFloat()), unablePaint)
        val unableDrawable = BitmapDrawable(view?.resources, unableBitmap)

        // Pressed state
        val pressedBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        val pressedCanvas = Canvas(pressedBitmap)
        val pressedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        pressedPaint.color = highlightedColor
        pressedCanvas.drawOval(RectF(0f, 0f, diameter.toFloat(), diameter.toFloat()), pressedPaint)
        val pressedDrawable = BitmapDrawable(view?.resources, pressedBitmap)

        // Normal state
        val normalBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        val normalCanvas = Canvas(normalBitmap)
        val normalPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        normalPaint.color = normalColor
        normalCanvas.drawOval(RectF(0f, 0f, diameter.toFloat(), diameter.toFloat()), normalPaint)
        val normalDrawable = BitmapDrawable(view?.resources, normalBitmap)

        stateListDrawable.addState(intArrayOf(-android.R.attr.state_enabled), unableDrawable)
        stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
        stateListDrawable.addState(intArrayOf(), normalDrawable)

        return stateListDrawable
    }

    @JvmStatic
    fun getRectangleStateListBitmapDrawable(
        view: View?,
        width: Int,
        height: Int,
        cornerRadius: Int,
        @ColorInt normalColor: Int,
        @ColorInt highlightedColor: Int,
        @ColorInt unableColor: Int
    ): StateListDrawable {
        val stateListDrawable = StateListDrawable()

        // Unable state
        val unableBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val unableCanvas = Canvas(unableBitmap)
        val unablePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        unablePaint.color = unableColor
        unableCanvas.drawRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat()),
            cornerRadius.toFloat(),
            cornerRadius.toFloat(),
            unablePaint
        )
        val unableDrawable = BitmapDrawable(view?.resources, unableBitmap)

        // Pressed state
        val pressedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val pressedCanvas = Canvas(pressedBitmap)
        val pressedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        pressedPaint.color = highlightedColor
        pressedCanvas.drawRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat()),
            cornerRadius.toFloat(),
            cornerRadius.toFloat(),
            pressedPaint
        )
        val pressedDrawable = BitmapDrawable(view?.resources, pressedBitmap)

        // Normal state
        val normalBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val normalCanvas = Canvas(normalBitmap)
        val normalPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        normalPaint.color = normalColor
        normalCanvas.drawRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat()),
            cornerRadius.toFloat(),
            cornerRadius.toFloat(),
            normalPaint
        )
        val normalDrawable = BitmapDrawable(view?.resources, normalBitmap)

        stateListDrawable.addState(intArrayOf(-android.R.attr.state_enabled), unableDrawable)
        stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
        stateListDrawable.addState(intArrayOf(), normalDrawable)

        return stateListDrawable
    }

    @JvmStatic
    fun pointInView(point: PointF, view: View?): Boolean {
        if (view == null) return false
        return point.x >= 0 && point.x < view.width && point.y >= 0 && point.y < view.height
    }

    @JvmStatic
    fun setText(textView: TextView?, @StringRes textRes: Int, text: String?) {
        if (textView == null) return
        if (textRes != 0) {
            textView.setText(textRes)
        } else {
            textView.text = text ?: ""
        }
    }

    @JvmStatic
    fun setTextColor(textView: TextView?, @ColorRes colorRes: Int, @ColorInt color: Int) {
        if (textView == null) return
        if (colorRes != 0) {
            textView.setTextColor(ContextCompat.getColor(textView.context, colorRes))
        } else {
            textView.setTextColor(color)
        }
    }

    @JvmStatic
    fun setDrawable(imageView: ImageView?, @DrawableRes drawableRes: Int, drawable: Drawable?) {
        if (imageView == null) return
        if (drawableRes != 0) {
            imageView.setImageResource(drawableRes)
        } else {
            imageView.setImageDrawable(drawable)
        }
    }

    @JvmStatic
    fun getSystemDrawable(context: Context, @AttrRes attrRes: Int): Drawable? {
        val attrs = intArrayOf(attrRes)
        val typedArray = context.obtainStyledAttributes(attrs)
        val drawable = typedArray.getDrawable(0)
        typedArray.recycle()
        return drawable
    }

    @JvmStatic
    fun getBoolean(typedArray: TypedArray, index: Int, defaultResId: Int): Boolean {
        return typedArray.getBoolean(
            index,
            typedArray.resources.getBoolean(defaultResId)
        )
    }

    @JvmStatic
    fun getDimenSize(typedArray: TypedArray, index: Int, defaultResId: Int): Int {
        return typedArray.getDimensionPixelSize(
            index,
            typedArray.resources.getDimensionPixelSize(defaultResId)
        )
    }

    @JvmStatic
    fun getDimenOffset(typedArray: TypedArray, index: Int, defaultResId: Int): Int {
        return typedArray.getDimensionPixelOffset(
            index,
            typedArray.resources.getDimensionPixelOffset(defaultResId)
        )
    }

    @JvmStatic
    @ColorInt
    fun getColor(typedArray: TypedArray, index: Int, defaultResId: Int): Int {
        val defaultColor = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                typedArray.resources.getColor(defaultResId, null)
            } else {
                @Suppress("DEPRECATION")
                typedArray.resources.getColor(defaultResId)
            }
        } catch (e: Exception) {
            Color.TRANSPARENT
        }
        return typedArray.getColor(index, defaultColor)
    }

    @JvmStatic
    fun getInt(typedArray: TypedArray, index: Int, defaultResId: Int): Int {
        return typedArray.getInt(
            index,
            typedArray.resources.getInteger(defaultResId)
        )
    }

    @JvmStatic
    @ColorInt
    fun getDarkerColor(@ColorInt color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= 0.8f // Reduce brightness by 20%
        return Color.HSVToColor(hsv)
    }

    @JvmStatic
    @ColorInt
    fun getLighterColor(@ColorInt color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] = minOf(hsv[2] * 1.2f, 1f) // Increase brightness by 20%, max 1
        return Color.HSVToColor(hsv)
    }

    @JvmStatic
    fun setVisibility(visibility: Int, vararg views: View?) {
        for (view in views) {
            view?.visibility = visibility
        }
    }

    @JvmStatic
    fun scanForActivity(context: Context?): Activity? {
        if (context == null) return null
        if (context is Activity) return context
        if (context is ContextWrapper) {
            return scanForActivity(context.baseContext)
        }
        return null
    }
}
