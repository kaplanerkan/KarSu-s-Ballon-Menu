package com.nightonke.boommenu

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
import android.util.DisplayMetrics
import android.util.Log
import android.util.StateSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Created by Weiping Huang at 01:12 on 2016/3/19
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 *
 * Converted to Kotlin
 */
object Util {

    private val colors = intArrayOf(
        Color.parseColor("#F44336"),
        Color.parseColor("#E91E63"),
        Color.parseColor("#9C27B0"),
        Color.parseColor("#673AB7"),
        Color.parseColor("#3F51B5"),
        Color.parseColor("#2196F3"),
        Color.parseColor("#03A9F4"),
        Color.parseColor("#00BCD4"),
        Color.parseColor("#009688"),
        Color.parseColor("#4CAF50"),
        Color.parseColor("#009688"),
        Color.parseColor("#CDDC39"),
        Color.parseColor("#FFEB3B"),
        Color.parseColor("#FF9800"),
        Color.parseColor("#FF5722"),
        Color.parseColor("#795548"),
        Color.parseColor("#9E9E9E"),
        Color.parseColor("#607D8B")
    )

    private val usedColor = ArrayList<Int>()

    @JvmStatic
    fun scanForActivity(context: Context?): Activity? {
        if (context == null) {
            Log.w(BoomMenuButton.TAG, "scanForActivity: context is null!")
            return null
        }
        return when (context) {
            is Activity -> context
            is ContextWrapper -> scanForActivity(context.baseContext)
            else -> {
                Log.w(BoomMenuButton.TAG, "scanForActivity: context is null!")
                null
            }
        }
    }

    @JvmStatic
    fun setVisibility(visibility: Int, vararg views: View?) {
        views.forEach { view -> view?.visibility = visibility }
    }

    @JvmStatic
    fun dp2px(dp: Float): Int {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return Math.round(px)
    }

    @JvmStatic
    fun getColor(view: View, id: Int, theme: Resources.Theme?): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.resources.getColor(id, theme)
        } else {
            @Suppress("DEPRECATION")
            view.resources.getColor(id)
        }
    }

    @JvmStatic
    fun getColor(typedArray: TypedArray, id: Int, theme: Resources.Theme?): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            typedArray.resources.getColor(id, theme)
        } else {
            @Suppress("DEPRECATION")
            typedArray.resources.getColor(id)
        }
    }

    @JvmStatic
    fun getColor(view: View, id: Int): Int {
        return getColor(view, id, null)
    }

    @JvmStatic
    fun getColor(typedArray: TypedArray, id: Int): Int {
        return getColor(typedArray, id, null)
    }

    @JvmStatic
    fun getSystemDrawable(context: Context, id: Int): Drawable? {
        val attrs = intArrayOf(id)
        val ta = context.obtainStyledAttributes(attrs)
        val drawable = ta.getDrawable(0)
        ta.recycle()
        return drawable
    }

    @JvmStatic
    fun getDrawable(view: View, id: Int, theme: Resources.Theme?): Drawable? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.resources.getDrawable(id, theme)
        } else {
            @Suppress("DEPRECATION")
            view.resources.getDrawable(id)
        }
    }

    @JvmStatic
    fun setDrawable(image: ImageView?, id: Int, drawable: Drawable?) {
        if (image == null) return
        if (id == 0) {
            if (drawable != null) image.setImageDrawable(drawable)
        } else {
            image.setImageResource(id)
        }
    }

    @JvmStatic
    fun setText(textView: TextView?, id: Int, text: String?) {
        if (textView == null) return
        if (id == 0) {
            if (text != null && text != textView.text.toString()) textView.text = text
        } else {
            val oldText = textView.context.resources.getText(id)
            if (oldText != textView.text) textView.setText(id)
        }
    }

    @JvmStatic
    fun setTextColor(textView: TextView?, id: Int, color: Int) {
        if (textView == null) return
        if (id == 0) {
            textView.setTextColor(color)
        } else {
            textView.setTextColor(getColor(textView.context, id))
        }
    }

    @JvmStatic
    fun getDrawable(view: View, id: Int): Drawable? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.resources.getDrawable(id, null)
        } else {
            @Suppress("DEPRECATION")
            view.resources.getDrawable(id)
        }
    }

    @JvmStatic
    fun getOvalDrawable(view: View, color: Int): GradientDrawable {
        val gradientDrawable = getDrawable(view, R.drawable.shape_oval_normal) as GradientDrawable
        gradientDrawable.setColor(color)
        return gradientDrawable
    }

    @JvmStatic
    fun getOvalBitmapDrawable(view: View, radius: Int, color: Int): BitmapDrawable? {
        if (radius <= 0) {
            return null
        }
        val bitmap = Bitmap.createBitmap(2 * radius, 2 * radius, Bitmap.Config.ARGB_8888)
        val canvasPressed = Canvas(bitmap)
        val paintPressed = Paint().apply {
            isAntiAlias = true
            this.color = color
        }
        canvasPressed.drawCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), paintPressed)
        return BitmapDrawable(view.resources, bitmap)
    }

    @JvmStatic
    fun getRectangleDrawable(view: View, cornerRadius: Int, color: Int): GradientDrawable {
        val gradientDrawable = getDrawable(view, R.drawable.shape_rectangle_normal) as GradientDrawable
        gradientDrawable.cornerRadius = cornerRadius.toFloat()
        gradientDrawable.setColor(color)
        return gradientDrawable
    }

    @JvmStatic
    fun getRectangleBitmapDrawable(view: View, width: Int, height: Int, cornerRadius: Int, color: Int): BitmapDrawable? {
        if (width <= 0 || height <= 0) {
            return null
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvasPressed = Canvas(bitmap)
        val paintPressed = Paint().apply {
            isAntiAlias = true
            this.color = color
        }
        canvasPressed.drawRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat()),
            cornerRadius.toFloat(),
            cornerRadius.toFloat(),
            paintPressed
        )
        return BitmapDrawable(view.resources, bitmap)
    }

    @JvmStatic
    fun distance(a: Point, b: Point): Float {
        return sqrt(((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)).toDouble()).toFloat()
    }

    // Bitmap drawable in state-list drawable is able to perform a click-effect.
    @JvmStatic
    fun getOvalStateListBitmapDrawable(
        view: View,
        radius: Int,
        normalColor: Int,
        highlightedColor: Int,
        unableColor: Int
    ): StateListDrawable {
        return StateListDrawable().apply {
            addState(
                intArrayOf(android.R.attr.state_pressed),
                getOvalBitmapDrawable(view, radius, highlightedColor)
            )
            addState(
                intArrayOf(-android.R.attr.state_enabled),
                getOvalBitmapDrawable(view, radius, unableColor)
            )
            addState(
                StateSet.WILD_CARD,
                getOvalBitmapDrawable(view, radius, normalColor)
            )
        }
    }

    // Gradient drawable in state-list drawable is not able to perform a click-effect.
    @JvmStatic
    fun getOvalStateListGradientDrawable(
        view: View,
        normalColor: Int,
        highlightedColor: Int,
        unableColor: Int
    ): StateListDrawable {
        return StateListDrawable().apply {
            addState(
                intArrayOf(android.R.attr.state_pressed),
                getOvalDrawable(view, highlightedColor)
            )
            addState(
                intArrayOf(-android.R.attr.state_enabled),
                getOvalDrawable(view, unableColor)
            )
            addState(
                StateSet.WILD_CARD,
                getOvalDrawable(view, normalColor)
            )
        }
    }

    // Bitmap drawable in state-list drawable is able to perform a click-effect.
    @JvmStatic
    fun getRectangleStateListBitmapDrawable(
        view: View,
        width: Int,
        height: Int,
        cornerRadius: Int,
        normalColor: Int,
        highlightedColor: Int,
        unableColor: Int
    ): StateListDrawable {
        return StateListDrawable().apply {
            addState(
                intArrayOf(android.R.attr.state_pressed),
                getRectangleBitmapDrawable(view, width, height, cornerRadius, highlightedColor)
            )
            addState(
                intArrayOf(-android.R.attr.state_enabled),
                getRectangleBitmapDrawable(view, width, height, cornerRadius, unableColor)
            )
            addState(
                StateSet.WILD_CARD,
                getRectangleBitmapDrawable(view, width, height, cornerRadius, normalColor)
            )
        }
    }

    // Gradient drawable in state-list drawable is not able to perform a click-effect.
    @JvmStatic
    fun getRectangleStateListGradientDrawable(
        view: View,
        cornerRadius: Int,
        normalColor: Int,
        highlightedColor: Int,
        unableColor: Int
    ): StateListDrawable {
        return StateListDrawable().apply {
            addState(
                intArrayOf(android.R.attr.state_pressed),
                getRectangleDrawable(view, cornerRadius, highlightedColor)
            )
            addState(
                intArrayOf(-android.R.attr.state_enabled),
                getRectangleDrawable(view, cornerRadius, unableColor)
            )
            addState(
                StateSet.WILD_CARD,
                getRectangleDrawable(view, cornerRadius, normalColor)
            )
        }
    }

    @JvmStatic
    fun getInt(typedArray: TypedArray, id: Int, defaultId: Int): Int {
        return typedArray.getInt(id, typedArray.resources.getInteger(defaultId))
    }

    @JvmStatic
    fun getBoolean(typedArray: TypedArray, id: Int, defaultId: Int): Boolean {
        return typedArray.getBoolean(id, typedArray.resources.getBoolean(defaultId))
    }

    @JvmStatic
    fun getDimenSize(typedArray: TypedArray, id: Int, defaultId: Int): Int {
        return typedArray.getDimensionPixelSize(id, typedArray.resources.getDimensionPixelSize(defaultId))
    }

    @JvmStatic
    fun getDimenOffset(typedArray: TypedArray, id: Int, defaultId: Int): Int {
        return typedArray.getDimensionPixelOffset(id, typedArray.resources.getDimensionPixelOffset(defaultId))
    }

    @JvmStatic
    fun getColor(typedArray: TypedArray, id: Int, defaultId: Int): Int {
        return typedArray.getColor(id, getColor(typedArray, defaultId))
    }

    @JvmStatic
    fun getColor(context: Context, id: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(id, null)
        } else {
            @Suppress("DEPRECATION")
            context.resources.getColor(id)
        }
    }

    @JvmStatic
    fun getColor(context: Context, id: Int, color: Int): Int {
        return if (id == 0) color else getColor(context, id)
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
    fun getDarkerColor(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= 0.9f
        return Color.HSVToColor(hsv)
    }

    @JvmStatic
    fun getLighterColor(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= 1.1f
        return Color.HSVToColor(hsv)
    }

    @JvmStatic
    fun getColor(): Int {
        while (true) {
            val colorIndex = Random.nextInt(colors.size)
            if (!usedColor.contains(colorIndex)) {
                usedColor.add(colorIndex)
                while (usedColor.size > 6) usedColor.removeAt(0)
                return colors[colorIndex]
            }
        }
    }

    @JvmStatic
    fun pointInView(point: PointF, view: View): Boolean {
        return view.left <= point.x && point.x <= view.right &&
                view.top <= point.y && point.y <= view.bottom
    }

    @JvmStatic
    val instance: Util = this
}
