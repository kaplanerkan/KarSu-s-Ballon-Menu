/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.BoomButtons

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.text.TextUtils
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.karsu.ballonsmenu.BMBShadow
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.R
import com.karsu.ballonsmenu.Util

abstract class BoomButton(context: Context) : FrameLayout(context) {

    // Basic
    @JvmField protected var context: Context = context
    @JvmField protected var index: Int = -1
    @JvmField protected var listener: InnerOnBoomButtonClickListener? = null
    @JvmField protected var onBMClickListener: OnBMClickListener? = null
    @JvmField protected var lastStateIsNormal: Boolean = true
    @JvmField protected var ableToHighlight: Boolean = true
    @JvmField protected var button: FrameLayout? = null
    @JvmField protected var buttonRadius: Int = 0  // for simple/text-inside/text-outside circle button
    @JvmField protected var buttonWidth: Int = 0  // for ham button
    @JvmField protected var buttonHeight: Int = 0  // for ham button
    @JvmField protected var buttonCornerRadius: Int = 0  // for ham button
    @JvmField protected var isRound: Boolean = false
    @JvmField protected var rotateImage: Boolean = false
    @JvmField protected var rotateText: Boolean = false
    @JvmField protected var containsSubText: Boolean = false  // for ham button
    @JvmField protected var buttonEnum: ButtonEnum = ButtonEnum.Unknown
    private var touchable: Boolean = false

    // piece
    @JvmField var pieceColor: Int? = null
    @JvmField var pieceColorRes: Int = 0

    // Shadow
    @JvmField protected var shadowEffect: Boolean = true
    @JvmField protected var shadowOffsetX: Int = 0
    @JvmField protected var shadowOffsetY: Int = 0
    @JvmField protected var shadowRadius: Int = 0
    @JvmField protected var shadowCornerRadius: Int = 0
    @JvmField protected var shadowColor: Int = 0
    @JvmField protected var shadow: BMBShadow? = null

    // Images
    @JvmField var normalImageRes: Int = 0
    @JvmField var normalImageDrawable: Drawable? = null
    @JvmField var highlightedImageRes: Int = 0
    @JvmField var highlightedImageDrawable: Drawable? = null
    @JvmField var unableImageRes: Int = 0
    @JvmField var unableImageDrawable: Drawable? = null
    @JvmField var imageRect: Rect? = null
    @JvmField var imagePadding: Rect? = null

    // Text
    @JvmField var normalTextRes: Int = 0
    @JvmField var normalText: String? = null
    @JvmField var highlightedTextRes: Int = 0
    @JvmField var highlightedText: String? = null
    @JvmField var unableTextRes: Int = 0
    @JvmField var unableText: String? = null
    @JvmField var normalTextColor: Int = 0
    @JvmField var normalTextColorRes: Int = 0
    @JvmField var highlightedTextColor: Int = 0
    @JvmField var highlightedTextColorRes: Int = 0
    @JvmField var unableTextColor: Int = 0
    @JvmField var unableTextColorRes: Int = 0
    @JvmField var textRect: Rect? = null
    @JvmField var textPadding: Rect? = null
    @JvmField protected var typeface: Typeface? = null
    @JvmField protected var maxLines: Int = 0
    @JvmField protected var textGravity: Int = 0
    @JvmField protected var ellipsize: TextUtils.TruncateAt? = null
    @JvmField protected var textSize: Int = 0

    // Sub text
    @JvmField protected var subNormalTextRes: Int = 0
    @JvmField protected var subNormalText: String? = null
    @JvmField protected var subHighlightedTextRes: Int = 0
    @JvmField protected var subHighlightedText: String? = null
    @JvmField protected var subUnableTextRes: Int = 0
    @JvmField protected var subUnableText: String? = null
    @JvmField protected var subNormalTextColor: Int = 0
    @JvmField protected var subNormalTextColorRes: Int = 0
    @JvmField protected var subHighlightedTextColor: Int = 0
    @JvmField protected var subHighlightedTextColorRes: Int = 0
    @JvmField protected var subUnableTextColor: Int = 0
    @JvmField protected var subUnableTextColorRes: Int = 0
    @JvmField var subTextRect: Rect? = null
    @JvmField var subTextPadding: Rect? = null
    @JvmField protected var subTypeface: Typeface? = null
    @JvmField protected var subMaxLines: Int = 0
    @JvmField protected var subTextGravity: Int = 0
    @JvmField protected var subEllipsize: TextUtils.TruncateAt? = null
    @JvmField protected var subTextSize: Int = 0

    // Text for text-outside-circle-button
    @JvmField protected var textTopMargin: Int = 0
    @JvmField protected var textWidth: Int = 0
    @JvmField protected var textHeight: Int = 0
    @JvmField protected var trueRadius: Int = 0

    // Button Colors
    @JvmField protected var rippleEffect: Boolean = true
    @JvmField var normalColor: Int = 0
    @JvmField var normalColorRes: Int = 0
    @JvmField var highlightedColor: Int = 0
    @JvmField var highlightedColorRes: Int = 0
    @JvmField var unableColor: Int = 0
    @JvmField var unableColorRes: Int = 0
    @JvmField var unable: Boolean = false
    @JvmField protected var rippleEffectWorks: Boolean = true
    @JvmField protected var rippleDrawable: RippleDrawable? = null
    @JvmField protected var nonRippleBitmapDrawable: StateListDrawable? = null
    @JvmField protected var nonRippleGradientDrawable: GradientDrawable? = null

    // Views
    @JvmField protected var layout: ViewGroup? = null
    @JvmField protected var image: ImageView? = null
    @JvmField protected var text: TextView? = null
    @JvmField protected var subText: TextView? = null

    @JvmField var centerPoint: PointF? = null

    protected open fun initAttrs(builder: BoomButtonBuilder<*>) {
        index = builder.index
        listener = builder.listener
        onBMClickListener = builder.onBMClickListener
        rotateImage = builder.rotateImage
        rotateText = builder.rotateText
        containsSubText = builder.containsSubText

        pieceColor = builder.pieceColor
        pieceColorRes = builder.pieceColorRes

        shadowEffect = builder.shadowEffect
        if (shadowEffect) {
            shadowOffsetX = builder.shadowOffsetX
            shadowOffsetY = builder.shadowOffsetY
            shadowRadius = builder.shadowRadius
            shadowCornerRadius = builder.shadowCornerRadius
            shadowColor = builder.shadowColor
        }

        normalImageRes = builder.normalImageRes
        highlightedImageRes = builder.highlightedImageRes
        unableImageRes = builder.unableImageRes
        normalImageDrawable = builder.normalImageDrawable
        highlightedImageDrawable = builder.highlightedImageDrawable
        unableImageDrawable = builder.unableImageDrawable
        imageRect = builder.imageRect
        imagePadding = builder.imagePadding

        normalText = builder.normalText
        normalTextRes = builder.normalTextRes
        highlightedText = builder.highlightedText
        highlightedTextRes = builder.highlightedTextRes
        unableText = builder.unableText
        unableTextRes = builder.unableTextRes
        normalTextColor = builder.normalTextColor
        normalTextColorRes = builder.normalTextColorRes
        highlightedTextColor = builder.highlightedTextColor
        highlightedTextColorRes = builder.highlightedTextColorRes
        unableTextColor = builder.unableTextColor
        unableTextColorRes = builder.unableTextColorRes
        textRect = builder.textRect
        textPadding = builder.textPadding
        typeface = builder.typeface
        maxLines = builder.maxLines
        textGravity = builder.textGravity
        ellipsize = builder.ellipsize
        textSize = builder.textSize

        subNormalText = builder.subNormalText
        subNormalTextRes = builder.subNormalTextRes
        subHighlightedText = builder.subHighlightedText
        subHighlightedTextRes = builder.subHighlightedTextRes
        subUnableText = builder.subUnableText
        subUnableTextRes = builder.subUnableTextRes
        subNormalTextColor = builder.subNormalTextColor
        subNormalTextColorRes = builder.subNormalTextColorRes
        subHighlightedTextColor = builder.subHighlightedTextColor
        subHighlightedTextColorRes = builder.subHighlightedTextColorRes
        subUnableTextColor = builder.subUnableTextColor
        subUnableTextColorRes = builder.subUnableTextColorRes
        subTextRect = builder.subTextRect
        subTextPadding = builder.subTextPadding
        subTypeface = builder.subTypeface
        subMaxLines = builder.subMaxLines
        subTextGravity = builder.subTextGravity
        subEllipsize = builder.subEllipsize
        subTextSize = builder.subTextSize

        rippleEffect = builder.rippleEffect
        normalColor = builder.normalColor
        normalColorRes = builder.normalColorRes
        highlightedColor = builder.highlightedColor
        highlightedColorRes = builder.highlightedColorRes
        unableColor = builder.unableColor
        unableColorRes = builder.unableColorRes
        unable = builder.unable
        buttonRadius = builder.buttonRadius
        buttonWidth = builder.buttonWidth
        buttonHeight = builder.buttonHeight
        isRound = builder.isRound
        buttonCornerRadius = when {
            buttonEnum == ButtonEnum.SimpleCircle ||
            buttonEnum == ButtonEnum.TextInsideCircle ||
            buttonEnum == ButtonEnum.TextOutsideCircle -> {
                if (isRound) builder.buttonRadius else builder.buttonCornerRadius
            }
            else -> builder.buttonCornerRadius
        }
        buttonCornerRadius = builder.buttonCornerRadius
        rippleEffectWorks = rippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

        // for text-outside-circle-button
        textTopMargin = builder.textTopMargin
        textWidth = builder.textWidth
        textHeight = builder.textHeight
        if (builder is TextOutsideCircleButton.Builder) {
            val buttonAndShadowWidth = buttonRadius * 2 + shadowOffsetX * 2 + shadowRadius * 2
            textRect = if (textWidth > buttonAndShadowWidth) {
                Rect(
                    0,
                    shadowOffsetY + shadowRadius + buttonRadius * 2 + textTopMargin,
                    textWidth,
                    shadowOffsetY + shadowRadius + buttonRadius * 2 + textTopMargin + textHeight
                )
            } else {
                Rect(
                    (buttonAndShadowWidth - textWidth) / 2,
                    shadowOffsetY + shadowRadius + buttonRadius * 2 + textTopMargin,
                    (buttonAndShadowWidth - textWidth) / 2 + textWidth,
                    shadowOffsetY + shadowRadius + buttonRadius * 2 + textTopMargin + textHeight
                )
            }
            trueRadius = (Util.distance(
                Point(
                    shadowOffsetX + shadowRadius + buttonRadius,
                    shadowOffsetY + shadowRadius + buttonRadius
                ),
                Point(
                    textRect!!.right,
                    textRect!!.bottom
                )
            ) + 1).toInt()
            if (textWidth > buttonAndShadowWidth) {
                textRect!!.offset(
                    trueRadius - textWidth / 2,
                    trueRadius - (shadowOffsetY + shadowRadius + buttonRadius)
                )
            } else {
                textRect!!.offset(
                    trueRadius - (shadowOffsetX + shadowRadius + buttonRadius),
                    trueRadius - (shadowOffsetY + shadowRadius + buttonRadius)
                )
            }
        }
    }

    protected fun initTextOutsideCircleButtonLayout() {
        layout = findViewById<ViewGroup>(R.id.layout)
        val params = LayoutParams(trueRadius * 2, trueRadius * 2)
        layout?.layoutParams = params
    }

    protected fun initShadow(shadowCornerRadius: Int) {
        if (shadowEffect) {
            shadow = findViewById<BMBShadow>(R.id.shadow)
            shadow?.apply {
                setShadowOffsetX(this@BoomButton.shadowOffsetX)
                setShadowOffsetY(this@BoomButton.shadowOffsetY)
                setShadowColor(this@BoomButton.shadowColor)
                setShadowRadius(this@BoomButton.shadowRadius)
                setShadowCornerRadius(shadowCornerRadius)
            }
        }
    }

    protected fun initImage() {
        image = ImageView(context)
        updateImageRect()
        updateImagePadding()
        button?.addView(image)
        lastStateIsNormal = false
        toNormal()
    }

    protected fun initText(parent: ViewGroup?) {
        text = TextView(context).apply {
            typeface?.let { setTypeface(it) }
            maxLines = this@BoomButton.maxLines
            setTextSize(TypedValue.COMPLEX_UNIT_SP, this@BoomButton.textSize.toFloat())
            gravity = textGravity
            ellipsize = this@BoomButton.ellipsize
            if (this@BoomButton.ellipsize == TextUtils.TruncateAt.MARQUEE) {
                setSingleLine(true)
                marqueeRepeatLimit = -1
                setHorizontallyScrolling(true)
                isFocusable = true
                isFocusableInTouchMode = true
                freezesText = true
            }
        }
        updateTextRect()
        updateTextPadding()
        parent?.addView(text)
    }

    protected fun initSubText(parent: ViewGroup?) {
        if (!containsSubText) return
        subText = TextView(context).apply {
            subTypeface?.let { setTypeface(it) }
            maxLines = this@BoomButton.maxLines
            setTextSize(TypedValue.COMPLEX_UNIT_SP, subTextSize.toFloat())
            gravity = subTextGravity
            ellipsize = subEllipsize
            if (subEllipsize == TextUtils.TruncateAt.MARQUEE) {
                setSingleLine(true)
                marqueeRepeatLimit = -1
                setHorizontallyScrolling(true)
                isFocusable = true
                isFocusableInTouchMode = true
                freezesText = true
            }
        }
        updateSubTextRect()
        updateSubTextPadding()
        parent?.addView(subText)
    }

    @SuppressLint("NewApi")
    protected fun initCircleButtonDrawable() {
        if (rippleEffectWorks) {
            val gradientDrawable = if (isRound) {
                Util.getOvalDrawable(button, if (unable) unableColor() else normalColor())
            } else {
                Util.getRectangleDrawable(button, buttonCornerRadius, if (unable) unableColor() else normalColor())
            }
            val rippleDrawable = RippleDrawable(
                ColorStateList.valueOf(highlightedColor()),
                gradientDrawable,
                null
            )
            Util.setDrawable(button, rippleDrawable)
            this.rippleDrawable = rippleDrawable
        } else {
            nonRippleBitmapDrawable = if (isRound) {
                Util.getOvalStateListBitmapDrawable(
                    button,
                    buttonRadius,
                    normalColor(),
                    highlightedColor(),
                    unableColor()
                )
            } else {
                Util.getRectangleStateListBitmapDrawable(
                    button,
                    buttonWidth,
                    buttonHeight,
                    buttonCornerRadius,
                    normalColor(),
                    highlightedColor(),
                    unableColor()
                )
            }
            if (isNeededColorAnimation()) {
                // Then we need to create 2 drawables to perform the color-transaction effect.
                // Because gradient-drawable is able to change the color,
                // but not able to perform a click-effect.
                nonRippleGradientDrawable = Util.getOvalDrawable(button, if (unable) unableColor() else normalColor())
            }
            Util.setDrawable(button, nonRippleBitmapDrawable)
        }
    }

    @SuppressLint("NewApi")
    protected fun initCircleButton() {
        button = findViewById<FrameLayout>(R.id.button)
        val params = button?.layoutParams as LayoutParams
        params.width = buttonRadius * 2
        params.height = buttonRadius * 2
        button?.layoutParams = params
        button?.isEnabled = !unable
        button?.setOnClickListener {
            if (!touchable) return@setOnClickListener
            listener?.onButtonClick(index, this@BoomButton)
            onBMClickListener?.onBoomButtonClick(index)
        }

        initCircleButtonDrawable()

        button?.setOnTouchListener { _, event ->
            if (!touchable) return@setOnTouchListener false
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (Util.pointInView(PointF(event.x, event.y), button)) {
                        toHighlighted()
                        ableToHighlight = true
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (Util.pointInView(PointF(event.x, event.y), button)) {
                        toHighlighted()
                    } else {
                        ableToHighlight = false
                        toNormal()
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    ableToHighlight = false
                    toNormal()
                }
            }
            false
        }
    }

    @SuppressLint("NewApi")
    protected fun initHamButtonDrawable() {
        if (rippleEffectWorks) {
            val rippleDrawable = RippleDrawable(
                ColorStateList.valueOf(highlightedColor()),
                Util.getRectangleDrawable(button, buttonCornerRadius, if (unable) unableColor() else normalColor()),
                null
            )
            Util.setDrawable(button, rippleDrawable)
            this.rippleDrawable = rippleDrawable
        } else {
            nonRippleBitmapDrawable = Util.getRectangleStateListBitmapDrawable(
                button,
                buttonWidth,
                buttonHeight,
                buttonCornerRadius,
                normalColor(),
                highlightedColor(),
                unableColor()
            )
            if (isNeededColorAnimation()) {
                // Then we need to create 2 drawables to perform the color-transaction effect.
                // Because gradient-drawable is able to change the color,
                // but not able to perform a click-effect.
                nonRippleGradientDrawable = Util.getRectangleDrawable(button, buttonCornerRadius, if (unable) unableColor() else normalColor())
            }
            Util.setDrawable(button, nonRippleBitmapDrawable)
        }
    }

    @SuppressLint("NewApi")
    protected fun initHamButton() {
        button = findViewById<FrameLayout>(R.id.button)
        val params = button?.layoutParams as LayoutParams
        params.width = buttonWidth
        params.height = buttonHeight
        button?.layoutParams = params
        button?.isEnabled = !unable
        button?.setOnClickListener {
            if (!touchable) return@setOnClickListener
            listener?.onButtonClick(index, this@BoomButton)
            onBMClickListener?.onBoomButtonClick(index)
        }

        initHamButtonDrawable()

        button?.setOnTouchListener { _, event ->
            if (!touchable) return@setOnTouchListener false
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (Util.pointInView(PointF(event.x, event.y), button)) {
                        toHighlighted()
                        ableToHighlight = true
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (Util.pointInView(PointF(event.x, event.y), button)) {
                        toHighlighted()
                    } else {
                        ableToHighlight = false
                        toNormal()
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    ableToHighlight = false
                    toNormal()
                }
            }
            false
        }
    }

    fun place(left: Int, top: Int, width: Int, height: Int): LayoutParams {
        val layoutParams = LayoutParams(width, height)
        layoutParams.leftMargin = left
        layoutParams.topMargin = top
        setLayoutParams(layoutParams)
        return layoutParams
    }

    /**
     * When the parameters about image are changed by builder, the corresponding builder should
     * call this method to update the image on the ImageView.
     */
    internal fun updateImage() {
        if (lastStateIsNormal) toNormalImage()
        else toHighlightedImage()
    }

    internal fun updateText() {
        if (lastStateIsNormal) toNormalText()
        else toHighlightedText()
    }

    internal fun updateSubText() {
        if (lastStateIsNormal) toNormalSubText()
        else toHighlightedSubText()
    }

    internal fun updateImageRect() {
        val rect = imageRect ?: return
        val params = LayoutParams(
            rect.right - rect.left,
            rect.bottom - rect.top
        )
        params.leftMargin = rect.left
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params.marginStart = rect.left
        }
        params.topMargin = rect.top
        image?.layoutParams = params
    }

    internal fun updateImagePadding() {
        imagePadding?.let { padding ->
            image?.setPadding(
                padding.left,
                padding.top,
                padding.right,
                padding.bottom
            )
        }
    }

    internal fun updateTextRect() {
        val rect = textRect ?: return
        val params = LayoutParams(
            rect.right - rect.left,
            rect.bottom - rect.top
        )
        params.leftMargin = rect.left
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params.marginStart = rect.left
        }
        params.topMargin = rect.top
        text?.layoutParams = params
    }

    internal fun updateTextPadding() {
        textPadding?.let { padding ->
            text?.setPadding(
                padding.left,
                padding.top,
                padding.right,
                padding.bottom
            )
        }
    }

    internal fun updateSubTextRect() {
        val rect = subTextRect ?: return
        val params = LayoutParams(
            rect.right - rect.left,
            rect.bottom - rect.top
        )
        params.leftMargin = rect.left
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params.marginStart = rect.left
        }
        params.topMargin = rect.top
        subText?.layoutParams = params
    }

    internal fun updateSubTextPadding() {
        subTextPadding?.let { padding ->
            subText?.setPadding(
                padding.left,
                padding.top,
                padding.right,
                padding.bottom
            )
        }
    }

    internal fun updateButtonDrawable() {
        if (buttonEnum == ButtonEnum.SimpleCircle ||
            buttonEnum == ButtonEnum.TextInsideCircle ||
            buttonEnum == ButtonEnum.TextOutsideCircle
        ) {
            initCircleButtonDrawable()
        } else {
            initHamButtonDrawable()
        }
    }

    internal fun updateUnable() {
        if (rippleEffectWorks) updateButtonDrawable()
        button?.isEnabled = !unable
        if (lastStateIsNormal) {
            toNormalImage()
            toNormalText()
            toNormalSubText()
        } else {
            toHighlightedImage()
            toHighlightedText()
            toHighlightedSubText()
        }
    }

    protected open fun toNormalImage() {
        if (unable) Util.setDrawable(image, unableImageRes, unableImageDrawable)
        else Util.setDrawable(image, normalImageRes, normalImageDrawable)
    }

    protected open fun toHighlightedImage() {
        if (unable) Util.setDrawable(image, unableImageRes, unableImageDrawable)
        else Util.setDrawable(image, highlightedImageRes, highlightedImageDrawable)
    }

    protected open fun toNormalText() {
        if (unable) {
            Util.setText(text, unableTextRes, unableText)
            Util.setTextColor(text, unableTextColorRes, unableTextColor)
        } else {
            Util.setText(text, normalTextRes, normalText)
            Util.setTextColor(text, normalTextColorRes, normalTextColor)
        }
    }

    protected open fun toHighlightedText() {
        if (unable) {
            Util.setText(text, unableTextRes, unableText)
            Util.setTextColor(text, unableTextColorRes, unableTextColor)
        } else {
            Util.setText(text, highlightedTextRes, highlightedText)
            Util.setTextColor(text, highlightedTextColorRes, highlightedTextColor)
        }
    }

    protected open fun toNormalSubText() {
        if (unable) {
            Util.setText(subText, subUnableTextRes, subUnableText)
            Util.setTextColor(subText, subUnableTextColorRes, subUnableTextColor)
        } else {
            Util.setText(subText, subNormalTextRes, subNormalText)
            Util.setTextColor(subText, subNormalTextColorRes, subNormalTextColor)
        }
    }

    protected open fun toHighlightedSubText() {
        if (unable) {
            Util.setText(subText, subUnableTextRes, subUnableText)
            Util.setTextColor(subText, subUnableTextColorRes, subUnableTextColor)
        } else {
            Util.setText(subText, subHighlightedTextRes, subHighlightedText)
            Util.setTextColor(subText, subHighlightedTextColorRes, subHighlightedTextColor)
        }
    }

    fun pieceColor(): Int {
        return when {
            pieceColor == null && pieceColorRes == 0 -> {
                if (unable) unableColor() else normalColor()
            }
            pieceColor == null -> Util.getColor(context, pieceColorRes)
            else -> Util.getColor(context, pieceColorRes, pieceColor!!)
        }
    }

    fun buttonColor(): Int = if (unable) unableColor() else normalColor()

    fun isNeededColorAnimation(): Boolean {
        val pieceColorVal = pieceColor()
        return if (unable) pieceColorVal.compareTo(unableColor()) != 0
        else pieceColorVal.compareTo(normalColor()) != 0
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        unable = !enabled
    }

    fun willShow() {
        touchable = false
        if (!rippleEffectWorks && isNeededColorAnimation()) {
            Util.setDrawable(button, nonRippleGradientDrawable)
        } else {
            updateButtonDrawable()
        }
    }

    fun didShow() {
        touchable = true
        if (!rippleEffectWorks && isNeededColorAnimation()) {
            Util.setDrawable(button, nonRippleBitmapDrawable)
        }
        text?.isSelected = true
        subText?.isSelected = true
    }

    fun willHide() {
        touchable = false
        if (!rippleEffectWorks && isNeededColorAnimation()) {
            Util.setDrawable(button, nonRippleGradientDrawable)
        }
    }

    fun didHide() {
        text?.isSelected = false
        subText?.isSelected = false
    }

    fun hideAllGoneViews() {
        for (view in goneViews()) view.alpha = 0f
    }

    fun prepareColorTransformAnimation(): Boolean {
        if (rippleEffectWorks) {
            if (rippleDrawable == null) throw RuntimeException("Background drawable is null!")
        } else if (nonRippleGradientDrawable == null) {
            throw RuntimeException("Background drawable is null!")
        }
        return rippleEffectWorks
    }

    protected fun setNonRippleButtonColor(color: Int) {
        nonRippleGradientDrawable?.setColor(color)
    }

    protected fun setRippleButtonColor(color: Int) {
        (rippleDrawable?.getDrawable(0) as? GradientDrawable)?.setColor(color)
    }

    override fun setClickable(clickable: Boolean) {
        super.setClickable(clickable)
        button?.isClickable = clickable
    }

    protected fun normalColor(): Int = Util.getColor(context, normalColorRes, normalColor)

    protected fun highlightedColor(): Int = Util.getColor(context, highlightedColorRes, highlightedColor)

    protected fun unableColor(): Int = Util.getColor(context, unableColorRes, unableColor)

    /**
     * Get the layout view of a boom button.
     *
     * @return layout view
     */
    fun getLayout(): ViewGroup? = layout

    /**
     * Get the button layout view of a boom button.
     *
     * @return button layout view
     */
    fun getButton(): FrameLayout? = button

    /**
     * Get the shadow view of a boom button.
     *
     * @return shadow view
     */
    fun getShadow(): BMBShadow? = shadow

    /**
     * Get the image view of a boom button.
     *
     * @return image view
     */
    fun getImageView(): ImageView? = image

    /**
     * Get the text view of a boom button.
     *
     * @return text view
     */
    fun getTextView(): TextView? = text

    /**
     * Get the sub text view of a boom button.
     *
     * @return sub text view
     */
    fun getSubTextView(): TextView? = subText

    abstract fun type(): ButtonEnum
    abstract fun goneViews(): ArrayList<View>
    abstract fun rotateViews(): ArrayList<View>
    abstract fun trueWidth(): Int
    abstract fun trueHeight(): Int
    abstract fun contentWidth(): Int
    abstract fun contentHeight(): Int
    protected abstract fun toHighlighted()
    protected abstract fun toNormal()
    abstract fun setRotateAnchorPoints()
    abstract fun setSelfScaleAnchorPoints()
}
