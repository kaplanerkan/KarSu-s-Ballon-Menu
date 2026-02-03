package com.nightonke.boommenu

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlin.math.abs

/**
 * Created by Weiping Huang at 19:09 on 16/11/6
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 *
 * Converted to Kotlin
 */
class BMBShadow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var shadowEffect = true
    private var shadowOffsetX = 0
    private var shadowOffsetY = 0
    private var shadowRadius = 0
    private var shadowCornerRadius = 0
    private var shadowColor = 0

    private fun initPadding() {
        val xPadding = shadowRadius + abs(shadowOffsetX)
        val yPadding = shadowRadius + abs(shadowOffsetY)
        setPadding(xPadding, yPadding, xPadding, yPadding)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            createShadow()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        createShadow()
    }

    private fun createShadow() {
        if (shadowEffect) {
            val shadowBitmap = createShadowBitmap() ?: return
            val shadowDrawable = BitmapDrawable(resources, shadowBitmap)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                @Suppress("DEPRECATION")
                setBackgroundDrawable(shadowDrawable)
            } else {
                background = shadowDrawable
            }
        } else {
            clearShadow()
        }
    }

    private fun createShadowBitmap(): Bitmap? {
        if (width <= 0 || height <= 0) {
            return null
        }

        val shadowBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
        val canvas = Canvas(shadowBitmap)
        val shadowRect = RectF(
            (shadowRadius + abs(shadowOffsetX)).toFloat(),
            (shadowRadius + abs(shadowOffsetY)).toFloat(),
            (width - shadowRadius - abs(shadowOffsetX)).toFloat(),
            (height - shadowRadius - abs(shadowOffsetY)).toFloat()
        )
        val shadowPaint = Paint().apply {
            isAntiAlias = true
            color = Color.TRANSPARENT
            style = Paint.Style.FILL
            if (!isInEditMode) {
                setShadowLayer(shadowRadius.toFloat(), shadowOffsetX.toFloat(), shadowOffsetY.toFloat(), shadowColor)
            }
        }
        canvas.drawRoundRect(shadowRect, shadowCornerRadius.toFloat(), shadowCornerRadius.toFloat(), shadowPaint)
        return shadowBitmap
    }

    fun setShadowOffsetX(shadowOffsetX: Int) {
        this.shadowOffsetX = shadowOffsetX
        initPadding()
    }

    fun setShadowOffsetY(shadowOffsetY: Int) {
        this.shadowOffsetY = shadowOffsetY
        initPadding()
    }

    fun setShadowRadius(shadowRadius: Int) {
        this.shadowRadius = shadowRadius
        initPadding()
    }

    fun setShadowCornerRadius(shadowCornerRadius: Int) {
        this.shadowCornerRadius = shadowCornerRadius
        initPadding()
    }

    fun setShadowColor(shadowColor: Int) {
        this.shadowColor = shadowColor
    }

    fun setShadowEffect(shadowEffect: Boolean) {
        this.shadowEffect = shadowEffect
    }

    fun clearShadow() {
        Util.setDrawable(this, null)
    }
}
