/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.os.PowerManager
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout

import com.karsu.ballonsmenu.Animation.AnimationManager
import com.karsu.ballonsmenu.Animation.BoomEnum
import com.karsu.ballonsmenu.Animation.Ease
import com.karsu.ballonsmenu.Animation.EaseEnum
import com.karsu.ballonsmenu.Animation.HideRgbEvaluator
import com.karsu.ballonsmenu.Animation.OrderEnum
import com.karsu.ballonsmenu.Animation.Rotate3DAnimation
import com.karsu.ballonsmenu.Animation.ShareLinesView
import com.karsu.ballonsmenu.Animation.ShowRgbEvaluator
import com.karsu.ballonsmenu.BoomButtons.BoomButton
import com.karsu.ballonsmenu.BoomButtons.BoomButtonBuilder
import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceAlignmentEnum
import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceManager
import com.karsu.ballonsmenu.BoomButtons.HamButton
import com.karsu.ballonsmenu.BoomButtons.InnerOnBoomButtonClickListener
import com.karsu.ballonsmenu.BoomButtons.SimpleCircleButton
import com.karsu.ballonsmenu.BoomButtons.TextInsideCircleButton
import com.karsu.ballonsmenu.BoomButtons.TextOutsideCircleButton
import com.karsu.ballonsmenu.Piece.BoomPiece
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum
import com.karsu.ballonsmenu.Piece.PiecePlaceManager

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@Suppress("unused")
class BoomMenuButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), InnerOnBoomButtonClickListener {

    companion object {
        @JvmStatic
        val TAG = "BoomMenuButton"
    }

    // Basic
    private var needToLayout = true
    private var cacheOptimization = false
    private var boomInWholeScreen = false
    private var inList = false
    private var inFragment = false
    private var isBackPressListened = true
    private var layoutJobsRunnable: Runnable? = null

    // Shadow
    private var shadowEffect = false
    private var shadowOffsetX = 0
    private var shadowOffsetY = 0
    private var shadowRadius = 0
    private var shadowColor = 0
    private var shadow: BMBShadow? = null

    // Button
    var buttonRadius = 0
        private set
    var buttonEnum: ButtonEnum = ButtonEnum.Unknown
        private set
    private var backgroundEffect = false
    private var rippleEffect = false
    var normalColor = 0
        private set
    var highlightedColor = 0
        private set
    var unableColor = 0
        private set
    private var draggable = false
    private var startPositionX = 0f
    private var startPositionY = 0f
    private var ableToStartDragging = false
    private var isDragging = false
    private var lastMotionX = -1f
    private var lastMotionY = -1f
    var edgeInsetsInParentView: Rect = Rect(0, 0, 0, 0)
        set(value) {
            if (field == value) return
            field = value
            preventDragOutside()
        }
    private var button: FrameLayout? = null

    // Piece
    private var pieces: ArrayList<BoomPiece>? = null
    private var piecePositions: ArrayList<RectF>? = null
    var dotRadius = 0f
        private set
    var hamWidth = 0f
        private set
    var hamHeight = 0f
        private set
    var pieceCornerRadius = -1f
        private set
    var pieceHorizontalMargin = 0f
        private set
    var pieceVerticalMargin = 0f
        private set
    var pieceInclinedMargin = 0f
        private set
    var shareLineLength = 0f
        private set
    var shareLine1Color = 0
        private set
    var shareLine2Color = 0
        private set
    var shareLineWidth = 0f
        private set
    private var shareLinesView: ShareLinesView? = null
    var piecePlaceEnum: PiecePlaceEnum = PiecePlaceEnum.Unknown
        private set
    var customPiecePlacePositions: ArrayList<PointF> = ArrayList()
        private set

    // Animation
    private var animatingViewNumber = 0
    var onBoomListener: OnBoomListener? = null
    var dimColor = 0
        set(value) {
            if (field == value) return
            field = value
            if (boomStateEnum == BoomStateEnum.DidBoom && background != null) {
                background?.setBackgroundColor(value)
            }
        }
    var showDuration: Long = 0
        set(value) {
            if (field == value) return
            field = max(1, value)
            setShareLinesViewData()
        }
    var showDelay: Long = 0
        set(value) {
            field = value
            setShareLinesViewData()
        }
    var hideDuration: Long = 0
        set(value) {
            if (field == value) return
            field = max(1, value)
            setShareLinesViewData()
        }
    var hideDelay: Long = 0
        set(value) {
            field = value
            setShareLinesViewData()
        }
    var isCancelable = false
    var isAutoHide = false
    var orderEnum: OrderEnum? = null
    var frames = 0
    var boomEnum: BoomEnum? = null
    var showMoveEaseEnum: EaseEnum? = null
    var showScaleEaseEnum: EaseEnum? = null
    var showRotateEaseEnum: EaseEnum? = null
    var hideMoveEaseEnum: EaseEnum? = null
    var hideScaleEaseEnum: EaseEnum? = null
    var hideRotateEaseEnum: EaseEnum? = null
    var rotateDegree = 0
    var isUse3DTransformAnimation = false
    var isAutoBoom = false
    var isAutoBoomImmediately = false
    private var boomStateEnum: BoomStateEnum = BoomStateEnum.DidReboom

    // Background
    private var background: BackgroundView? = null

    // Boom Buttons
    private var boomButtons: ArrayList<BoomButton> = ArrayList()
    private var boomButtonBuilders: ArrayList<BoomButtonBuilder<*>> = ArrayList()
    private var simpleCircleButtonRadius = 0f
    private var textInsideCircleButtonRadius = 0f
    private var textOutsideCircleButtonWidth = 0f
    private var textOutsideCircleButtonHeight = 0f
    private var hamButtonWidth = 0f
    private var hamButtonHeight = 0f
    var buttonPlaceEnum: ButtonPlaceEnum = ButtonPlaceEnum.Unknown
        set(value) {
            field = value
            clearButtons()
            needToCalculateStartPositions = true
        }
    var customButtonPlacePositions: ArrayList<PointF> = ArrayList()
        set(value) {
            field = value
            clearButtons()
            needToCalculateStartPositions = true
        }
    var buttonPlaceAlignmentEnum: ButtonPlaceAlignmentEnum? = null
    var buttonHorizontalMargin = 0f
    var buttonVerticalMargin = 0f
    var buttonInclinedMargin = 0f
    var buttonTopMargin = 0f
    var buttonBottomMargin = 0f
    var buttonLeftMargin = 0f
    var buttonRightMargin = 0f
    private var startPositions: ArrayList<PointF>? = null
    private var endPositions: ArrayList<PointF>? = null
    var bottomHamButtonTopMargin = 0f
    private var needToCreateButtons = true
    private var needToCalculateStartPositions = true
    private var lastReboomIndex = -1

    // Orientation
    var isOrientationAdaptable = false
        set(value) {
            field = value
            if (value) {
                initOrientationListener()
            }
        }
    private var lastOrientation = OrientationEventListener.ORIENTATION_UNKNOWN
    private var isOrientationChanged = false
    private var orientationEventListener: OrientationEventListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.bmb, this, true)
        initAttrs(context, attrs)
        initShadow()
        initButton()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BoomMenuButton, 0, 0)
            ?: return

        try {
            // Basic
            cacheOptimization = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_cacheOptimization, R.bool.default_bmb_cacheOptimization)
            boomInWholeScreen = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_boomInWholeScreen, R.bool.default_bmb_boomInWholeScreen)
            inList = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_inList, R.bool.default_bmb_inList)
            inFragment = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_inFragment, R.bool.default_bmb_inFragment)
            isBackPressListened = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_backPressListened, R.bool.default_bmb_backPressListened)
            isOrientationAdaptable = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_orientationAdaptable, R.bool.default_bmb_orientationAdaptable)

            // Shadow
            shadowEffect = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_shadowEffect, R.bool.default_bmb_shadow_effect)
            shadowRadius = Util.getDimenSize(typedArray, R.styleable.BoomMenuButton_bmb_shadowRadius, R.dimen.default_bmb_shadow_radius)
            shadowOffsetX = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_shadowOffsetX, R.dimen.default_bmb_shadow_offset_x)
            shadowOffsetY = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_shadowOffsetY, R.dimen.default_bmb_shadow_offset_y)
            shadowColor = Util.getColor(typedArray, R.styleable.BoomMenuButton_bmb_shadowColor, R.color.default_bmb_shadow_color)

            // Button
            buttonRadius = Util.getDimenSize(typedArray, R.styleable.BoomMenuButton_bmb_buttonRadius, R.dimen.default_bmb_button_radius)
            buttonEnum = ButtonEnum.getEnum(Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_buttonEnum, R.integer.default_bmb_button_enum))
            backgroundEffect = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_backgroundEffect, R.bool.default_bmb_background_effect)
            rippleEffect = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_rippleEffect, R.bool.default_bmb_ripple_effect)
            normalColor = Util.getColor(typedArray, R.styleable.BoomMenuButton_bmb_normalColor, R.color.default_bmb_normal_color)
            highlightedColor = Util.getColor(typedArray, R.styleable.BoomMenuButton_bmb_highlightedColor, R.color.default_bmb_highlighted_color)
            if (highlightedColor == Color.TRANSPARENT) highlightedColor = Util.getDarkerColor(normalColor)
            unableColor = Util.getColor(typedArray, R.styleable.BoomMenuButton_bmb_unableColor, R.color.default_bmb_unable_color)
            if (unableColor == Color.TRANSPARENT) unableColor = Util.getLighterColor(normalColor)
            draggable = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_draggable, R.bool.default_bmb_draggable)
            edgeInsetsInParentView = Rect(0, 0, 0, 0)
            edgeInsetsInParentView.left = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_edgeInsetsLeft, R.dimen.default_bmb_edgeInsetsLeft)
            edgeInsetsInParentView.top = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_edgeInsetsTop, R.dimen.default_bmb_edgeInsetsTop)
            edgeInsetsInParentView.right = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_edgeInsetsRight, R.dimen.default_bmb_edgeInsetsRight)
            edgeInsetsInParentView.bottom = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_edgeInsetsBottom, R.dimen.default_bmb_edgeInsetsBottom)

            // Piece
            dotRadius = Util.getDimenSize(typedArray, R.styleable.BoomMenuButton_bmb_dotRadius, R.dimen.default_bmb_dotRadius).toFloat()
            hamWidth = Util.getDimenSize(typedArray, R.styleable.BoomMenuButton_bmb_hamWidth, R.dimen.default_bmb_hamWidth).toFloat()
            hamHeight = Util.getDimenSize(typedArray, R.styleable.BoomMenuButton_bmb_hamHeight, R.dimen.default_bmb_hamHeight).toFloat()
            pieceCornerRadius = Util.getDimenSize(typedArray, R.styleable.BoomMenuButton_bmb_pieceCornerRadius, R.dimen.default_bmb_pieceCornerRadius).toFloat()
            pieceHorizontalMargin = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_pieceHorizontalMargin, R.dimen.default_bmb_pieceHorizontalMargin).toFloat()
            pieceVerticalMargin = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_pieceVerticalMargin, R.dimen.default_bmb_pieceVerticalMargin).toFloat()
            pieceInclinedMargin = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_pieceInclinedMargin, R.dimen.default_bmb_pieceInclinedMargin).toFloat()
            shareLineLength = Util.getDimenSize(typedArray, R.styleable.BoomMenuButton_bmb_sharedLineLength, R.dimen.default_bmb_sharedLineLength).toFloat()
            shareLine1Color = Util.getColor(typedArray, R.styleable.BoomMenuButton_bmb_shareLine1Color, R.color.default_bmb_shareLine1Color)
            shareLine2Color = Util.getColor(typedArray, R.styleable.BoomMenuButton_bmb_shareLine2Color, R.color.default_bmb_shareLine2Color)
            shareLineWidth = Util.getDimenSize(typedArray, R.styleable.BoomMenuButton_bmb_shareLineWidth, R.dimen.default_bmb_shareLineWidth).toFloat()
            piecePlaceEnum = PiecePlaceEnum.getEnum(Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_piecePlaceEnum, R.integer.default_bmb_pieceEnum))

            // Animation
            dimColor = Util.getColor(typedArray, R.styleable.BoomMenuButton_bmb_dimColor, R.color.default_bmb_dimColor)
            showDuration = Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_showDuration, R.integer.default_bmb_showDuration).toLong()
            showDelay = Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_showDelay, R.integer.default_bmb_showDelay).toLong()
            hideDuration = Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_hideDuration, R.integer.default_bmb_hideDuration).toLong()
            hideDelay = Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_hideDelay, R.integer.default_bmb_hideDelay).toLong()
            isCancelable = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_cancelable, R.bool.default_bmb_cancelable)
            isAutoHide = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_autoHide, R.bool.default_bmb_autoHide)
            orderEnum = OrderEnum.getEnum(Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_orderEnum, R.integer.default_bmb_orderEnum))
            frames = Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_frames, R.integer.default_bmb_frames)
            boomEnum = BoomEnum.getEnum(Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_boomEnum, R.integer.default_bmb_boomEnum))
            showMoveEaseEnum = EaseEnum.getEnum(Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_showMoveEaseEnum, R.integer.default_bmb_showMoveEaseEnum))
            showScaleEaseEnum = EaseEnum.getEnum(Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_showScaleEaseEnum, R.integer.default_bmb_showScaleEaseEnum))
            showRotateEaseEnum = EaseEnum.getEnum(Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_showRotateEaseEnum, R.integer.default_bmb_showRotateEaseEnum))
            hideMoveEaseEnum = EaseEnum.getEnum(Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_hideMoveEaseEnum, R.integer.default_bmb_hideMoveEaseEnum))
            hideScaleEaseEnum = EaseEnum.getEnum(Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_hideScaleEaseEnum, R.integer.default_bmb_hideScaleEaseEnum))
            hideRotateEaseEnum = EaseEnum.getEnum(Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_hideRotateEaseEnum, R.integer.default_bmb_hideRotateEaseEnum))
            rotateDegree = Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_rotateDegree, R.integer.default_bmb_rotateDegree)
            isUse3DTransformAnimation = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_use3DTransformAnimation, R.bool.default_bmb_use3DTransformAnimation)
            isAutoBoom = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_autoBoom, R.bool.default_bmb_autoBoom)
            isAutoBoomImmediately = Util.getBoolean(typedArray, R.styleable.BoomMenuButton_bmb_autoBoomImmediately, R.bool.default_bmb_autoBoomImmediately)

            // Boom buttons
            buttonPlaceEnum = ButtonPlaceEnum.getEnum(Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_buttonPlaceEnum, R.integer.default_bmb_buttonPlaceEnum))
            buttonPlaceAlignmentEnum = ButtonPlaceAlignmentEnum.getEnum(Util.getInt(typedArray, R.styleable.BoomMenuButton_bmb_buttonPlaceAlignmentEnum, R.integer.default_bmb_buttonPlaceAlignmentEnum))
            buttonHorizontalMargin = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_buttonHorizontalMargin, R.dimen.default_bmb_buttonHorizontalMargin).toFloat()
            buttonVerticalMargin = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_buttonVerticalMargin, R.dimen.default_bmb_buttonVerticalMargin).toFloat()
            buttonInclinedMargin = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_buttonInclinedMargin, R.dimen.default_bmb_buttonInclinedMargin).toFloat()
            buttonTopMargin = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_buttonTopMargin, R.dimen.default_bmb_buttonTopMargin).toFloat()
            buttonBottomMargin = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_buttonBottomMargin, R.dimen.default_bmb_buttonBottomMargin).toFloat()
            buttonLeftMargin = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_buttonLeftMargin, R.dimen.default_bmb_buttonLeftMargin).toFloat()
            buttonRightMargin = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_buttonRightMargin, R.dimen.default_bmb_buttonRightMargin).toFloat()
            bottomHamButtonTopMargin = Util.getDimenOffset(typedArray, R.styleable.BoomMenuButton_bmb_bottomHamButtonTopMargin, R.dimen.default_bmb_bottomHamButtonTopMargin).toFloat()
        } finally {
            typedArray.recycle()
        }
    }

    private fun initShadow() {
        if (shadow == null) shadow = findViewById(R.id.shadow) as BMBShadow
        val hasShadow = shadowEffect && backgroundEffect && !inList
        shadow?.setShadowEffect(hasShadow)
        if (hasShadow) {
            shadow?.setShadowOffsetX(shadowOffsetX)
            shadow?.setShadowOffsetY(shadowOffsetY)
            shadow?.setShadowColor(shadowColor)
            shadow?.setShadowRadius(shadowRadius)
            shadow?.setShadowCornerRadius(shadowRadius + buttonRadius)
        } else {
            shadow?.clearShadow()
        }
    }

    private fun initButton() {
        if (button == null) button = findViewById(R.id.button) as FrameLayout
        button?.setOnClickListener { boom() }
        initDraggableTouchListener()
        setButtonSize()
        setButtonBackground()
    }

    private fun setButtonSize() {
        val params = button?.layoutParams as? LayoutParams ?: return
        params.width = buttonRadius * 2
        params.height = buttonRadius * 2
        button?.layoutParams = params
    }

    private fun setButtonBackground() {
        val btn = button ?: return
        if (backgroundEffect && !inList) {
            if (rippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val rippleDrawable = RippleDrawable(
                    ColorStateList.valueOf(highlightedColor),
                    Util.getOvalDrawable(btn, normalColor),
                    null
                )
                Util.setDrawable(btn, rippleDrawable)
            } else {
                val stateListDrawable = Util.getOvalStateListBitmapDrawable(
                    btn,
                    buttonRadius,
                    normalColor,
                    highlightedColor,
                    unableColor
                )
                Util.setDrawable(btn, stateListDrawable)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Util.setDrawable(btn, Util.getSystemDrawable(context, android.R.attr.selectableItemBackgroundBorderless))
            } else {
                Util.setDrawable(btn, Util.getSystemDrawable(context, android.R.attr.selectableItemBackground))
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_BACK == keyCode
            && isBackPressListened
            && (boomStateEnum == BoomStateEnum.WillBoom || boomStateEnum == BoomStateEnum.DidBoom)
        ) {
            reboom()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    // Region Place Pieces

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isOrientationChanged) {
            reLayoutAfterOrientationChanged()
        }
        if (needToLayout) {
            if (inList) delayToDoLayoutJobs()
            else doLayoutJobs()
        }
        needToLayout = false
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        checkAutoBoom()
    }

    private fun doLayoutJobs() {
        if (uninitializedBoomButtons()) return
        clearPieces()
        createPieces()
        placeShareLinesView()
        placePieces()
        placePiecesAtPositions()
        calculateStartPositions(false)
        setShareLinesViewData()
    }

    private fun clearPieces() {
        pieces?.forEach { piece -> button?.removeView(piece) }
        pieces?.clear()
    }

    private fun createPieces() {
        calculatePiecePositions()
        val pieceNumber = pieceNumber()
        pieces = ArrayList(pieceNumber)
        for (i in 0 until pieceNumber) {
            pieces?.add(PiecePlaceManager.createPiece(this, boomButtonBuilders[i]))
        }
    }

    private fun placePieces() {
        val indexes = if (piecePlaceEnum == PiecePlaceEnum.Share) {
            AnimationManager.getOrderIndex(OrderEnum.DEFAULT, pieces?.size ?: 0)
        } else {
            AnimationManager.getOrderIndex(orderEnum, pieces?.size ?: 0)
        }
        // Reverse to keep the former pieces are above than the latter(z-axis)
        for (i in indexes.size - 1 downTo 0) {
            button?.addView(pieces?.get(indexes[i]))
        }
    }

    private fun placePiecesAtPositions() {
        val pieceNumber = pieceNumber()
        for (i in 0 until pieceNumber) {
            pieces?.get(i)?.place(piecePositions?.get(i))
        }
    }

    private fun calculatePiecePositions() {
        when (buttonEnum) {
            ButtonEnum.SimpleCircle,
            ButtonEnum.TextInsideCircle,
            ButtonEnum.TextOutsideCircle -> {
                piecePositions = if (piecePlaceEnum == PiecePlaceEnum.Share) {
                    PiecePlaceManager.getShareDotPositions(
                        this,
                        Point(button?.width ?: 0, button?.height ?: 0),
                        boomButtonBuilders.size
                    )
                } else {
                    PiecePlaceManager.getDotPositions(
                        this,
                        Point(button?.width ?: 0, button?.height ?: 0)
                    )
                }
            }
            ButtonEnum.Ham -> {
                piecePositions = PiecePlaceManager.getHamPositions(
                    this,
                    Point(button?.width ?: 0, button?.height ?: 0)
                )
            }
            ButtonEnum.Unknown -> {
                throw RuntimeException("The button-enum is unknown!")
            }
        }
    }

    // Region Touch

    private fun initDraggableTouchListener() {
        val btn = button ?: return
        if (!draggable) {
            btn.setOnTouchListener(null)
        } else {
            btn.setOnTouchListener { v, event ->
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        if (draggable) {
                            startPositionX = x - event.rawX
                            startPositionY = y - event.rawY
                            lastMotionX = event.rawX
                            lastMotionY = event.rawY
                        }
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (abs(lastMotionX - event.rawX) > 10 || abs(lastMotionY - event.rawY) > 10) {
                            ableToStartDragging = true
                        }
                        if (draggable && ableToStartDragging) {
                            isDragging = true
                            shadow?.let {
                                x = event.rawX + startPositionX
                                y = event.rawY + startPositionY
                            }
                        } else {
                            ableToStartDragging = false
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        if (isDragging) {
                            ableToStartDragging = false
                            isDragging = false
                            needToCalculateStartPositions = true
                            preventDragOutside()
                            btn.isPressed = false
                            return@setOnTouchListener true
                        }
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        if (isDragging) {
                            ableToStartDragging = false
                            isDragging = false
                            needToCalculateStartPositions = true
                            preventDragOutside()
                            return@setOnTouchListener true
                        }
                    }
                }
                false
            }
        }
    }

    // Region Animation

    /**
     * Whether BMB is animating.
     *
     * @return Is animating.
     */
    fun isAnimating(): Boolean = animatingViewNumber != 0

    /**
     * Whether the BMB has finished booming.
     *
     * @return whether the BMB has finished booming
     */
    fun isBoomed(): Boolean = boomStateEnum == BoomStateEnum.DidBoom

    /**
     * Whether the BMB has finished ReBooming.
     *
     * @return whether the BMB has finished ReBooming
     */
    fun isReBoomed(): Boolean = boomStateEnum == BoomStateEnum.DidReboom

    /**
     * Boom the BMB!
     */
    fun boom() {
        innerBoom(false)
    }

    /**
     * Boom the BMB with duration 0!
     */
    fun boomImmediately() {
        innerBoom(true)
    }

    private fun innerBoom(immediately: Boolean) {
        if (isAnimating() || boomStateEnum != BoomStateEnum.DidReboom) return
        ExceptionManager.judge(this, boomButtonBuilders)
        boomStateEnum = BoomStateEnum.WillBoom
        onBoomListener?.onBoomWillShow()
        calculateStartPositions(false)
        createButtons()
        dimBackground(immediately)
        startBoomAnimations(immediately)
        startBoomAnimationForFadeViews(immediately)
        if (isBackPressListened) {
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
        }
    }

    /**
     * Re-boom the BMB!
     */
    fun reboom() {
        innerReboom(false)
    }

    /**
     * Re-boom the BMB with duration 0!
     */
    fun reboomImmediately() {
        innerReboom(true)
    }

    private fun innerReboom(immediately: Boolean) {
        if (isAnimating() || boomStateEnum != BoomStateEnum.DidBoom) return
        boomStateEnum = BoomStateEnum.WillReboom
        onBoomListener?.onBoomWillHide()
        lightBackground(immediately)
        startReboomAnimations(immediately)
        startReboomAnimationForFadeViews(immediately)
        if (isBackPressListened) {
            isFocusable = false
            isFocusableInTouchMode = false
        }
    }

    private fun dimBackground(immediately: Boolean) {
        createBackground()
        Util.setVisibility(VISIBLE, background)
        val duration = if (immediately) 1L else showDuration + showDelay * ((pieces?.size ?: 1) - 1)
        background?.dim(duration, object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                boomStateEnum = BoomStateEnum.DidBoom
                onBoomListener?.onBoomDidShow()
            }
        })
        if (piecePlaceEnum == PiecePlaceEnum.Share) {
            AnimationManager.animate(
                shareLinesView, "showProcess", 0, duration,
                Ease.getInstance(EaseEnum.Linear), 0f, 1f
            )
        }
    }

    private fun lightBackground(immediately: Boolean) {
        createBackground()
        val duration = if (immediately) 1L else hideDuration + hideDelay * ((pieces?.size ?: 1) - 1)
        background?.light(duration, null)
        if (piecePlaceEnum == PiecePlaceEnum.Share) {
            AnimationManager.animate(
                shareLinesView, "hideProcess", 0, duration,
                Ease.getInstance(EaseEnum.Linear), 0f, 1f
            )
        }
    }

    private fun finishReboomAnimations() {
        if (isAnimating()) {
            return
        }
        boomStateEnum = BoomStateEnum.DidReboom
        onBoomListener?.onBoomDidHide()
        background?.visibility = GONE
        clearViews(false)
    }

    private fun startBoomAnimations(immediately: Boolean) {
        background?.removeAllViews()
        calculateEndPositions()
        val indexes = if (piecePlaceEnum == PiecePlaceEnum.Share) {
            AnimationManager.getOrderIndex(OrderEnum.DEFAULT, pieces?.size ?: 0)
        } else {
            AnimationManager.getOrderIndex(orderEnum, pieces?.size ?: 0)
        }
        // Fix the strange bug when use3DTransformAnimation is true
        if (lastReboomIndex != -1 && isUse3DTransformAnimation) {
            boomButtons[lastReboomIndex] = boomButtonBuilders[lastReboomIndex]
                .innerListener(this).index(lastReboomIndex).build(context)
        }
        // Reverse to keep the former boom-buttons are above than the latter(z-axis)
        for (i in indexes.size - 1 downTo 0) {
            val index = indexes[i]
            val boomButton = boomButtons[index]
            val startPosition = PointF(
                (startPositions?.get(index)?.x ?: 0f) - boomButton.centerPoint.x,
                (startPositions?.get(index)?.y ?: 0f) - boomButton.centerPoint.y
            )
            putBoomButtonInBackground(boomButton, startPosition)
            startEachBoomAnimation(
                pieces?.get(index), boomButton, startPosition,
                endPositions?.get(index), i, immediately
            )
        }
    }

    private fun startReboomAnimations(immediately: Boolean) {
        val indexes = if (piecePlaceEnum == PiecePlaceEnum.Share) {
            AnimationManager.getOrderIndex(OrderEnum.REVERSE, pieces?.size ?: 0)
        } else {
            AnimationManager.getOrderIndex(orderEnum, pieces?.size ?: 0)
        }
        lastReboomIndex = indexes[indexes.size - 1]
        for (index in indexes) {
            boomButtons[index].bringToFront()
        }
        for (i in indexes.indices) {
            val index = indexes[i]
            val boomButton = boomButtons[index]
            val startPosition = PointF(
                (startPositions?.get(index)?.x ?: 0f) - boomButton.centerPoint.x,
                (startPositions?.get(index)?.y ?: 0f) - boomButton.centerPoint.y
            )
            startEachReboomAnimation(
                pieces?.get(index), boomButton,
                endPositions?.get(index), startPosition, i, immediately
            )
        }
    }

    private fun startEachBoomAnimation(
        piece: BoomPiece?,
        boomButton: BoomButton,
        startPosition: PointF,
        endPosition: PointF?,
        delayFactor: Int,
        immediately: Boolean
    ) {
        if (isBatterySaveModeTurnOn()) {
            post {
                innerStartEachBoomAnimation(piece, boomButton, startPosition, endPosition, delayFactor, immediately)
            }
        } else {
            innerStartEachBoomAnimation(piece, boomButton, startPosition, endPosition, delayFactor, immediately)
        }
    }

    private fun innerStartEachBoomAnimation(
        piece: BoomPiece?,
        boomButton: BoomButton,
        startPosition: PointF,
        endPosition: PointF?,
        delayFactor: Int,
        immediately: Boolean
    ) {
        animatingViewNumber++
        val xs = FloatArray(frames + 1)
        val ys = FloatArray(frames + 1)
        val scaleX = (piece?.width ?: 1) * 1.0f / boomButton.contentWidth()
        val scaleY = (piece?.height ?: 1) * 1.0f / boomButton.contentHeight()
        val delay = if (immediately) 1L else showDelay * delayFactor
        val duration = if (immediately) 1L else showDuration
        boomButton.setSelfScaleAnchorPoints()
        boomButton.scaleX = scaleX
        boomButton.scaleY = scaleY
        boomButton.hideAllGoneViews()
        AnimationManager.calculateShowXY(
            boomEnum,
            PointF(
                (background?.layoutParams?.width ?: 0).toFloat(),
                (background?.layoutParams?.height ?: 0).toFloat()
            ),
            Ease.getInstance(showMoveEaseEnum), frames, startPosition, endPosition, xs, ys
        )
        if (boomButton.isNeededColorAnimation) {
            if (boomButton.prepareColorTransformAnimation()) {
                AnimationManager.animate(
                    boomButton, "rippleButtonColor", delay, duration,
                    ShowRgbEvaluator.instance, boomButton.pieceColor(), boomButton.buttonColor()
                )
            } else {
                AnimationManager.animate(
                    boomButton, "nonRippleButtonColor", delay, duration,
                    ShowRgbEvaluator.instance, boomButton.pieceColor(), boomButton.buttonColor()
                )
            }
        }
        AnimationManager.animate(boomButton, "x", delay, duration, LinearInterpolator(), xs)
        AnimationManager.animate(boomButton, "y", delay, duration, LinearInterpolator(), ys)
        AnimationManager.rotate(boomButton, delay, duration, Ease.getInstance(showRotateEaseEnum), 0, rotateDegree)
        AnimationManager.animate("alpha", delay, duration, floatArrayOf(0f, 1f), Ease.getInstance(EaseEnum.Linear), boomButton.goneViews())
        AnimationManager.animate(boomButton, "scaleX", delay, duration, Ease.getInstance(showScaleEaseEnum), scaleX, 1f)
        AnimationManager.animate(
            boomButton, "scaleY", delay, duration, Ease.getInstance(showScaleEaseEnum),
            object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    Util.setVisibility(INVISIBLE, piece)
                    Util.setVisibility(VISIBLE, boomButton)
                    boomButton.willShow()
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    boomButton.didShow()
                    animatingViewNumber--
                }
            }, scaleY, 1f
        )

        if (isUse3DTransformAnimation) {
            val rotate3DAnimation = AnimationManager.getRotate3DAnimation(xs, ys, delay, duration, boomButton)
            rotate3DAnimation.set(boomButton, startPosition.x, startPosition.y)
            boomButton.cameraDistance = 0f
            boomButton.startAnimation(rotate3DAnimation)
        }
    }

    private fun startEachReboomAnimation(
        piece: BoomPiece?,
        boomButton: BoomButton,
        startPosition: PointF?,
        endPosition: PointF,
        delayFactor: Int,
        immediately: Boolean
    ) {
        animatingViewNumber++
        val xs = FloatArray(frames + 1)
        val ys = FloatArray(frames + 1)
        val scaleX = (piece?.width ?: 1) * 1.0f / boomButton.contentWidth()
        val scaleY = (piece?.height ?: 1) * 1.0f / boomButton.contentHeight()
        val delay = if (immediately) 1L else hideDelay * delayFactor
        val duration = if (immediately) 1L else hideDuration
        AnimationManager.calculateHideXY(
            boomEnum,
            PointF(
                (background?.layoutParams?.width ?: 0).toFloat(),
                (background?.layoutParams?.height ?: 0).toFloat()
            ),
            Ease.getInstance(hideMoveEaseEnum), frames, startPosition, endPosition, xs, ys
        )
        if (boomButton.isNeededColorAnimation) {
            if (boomButton.prepareColorTransformAnimation()) {
                AnimationManager.animate(
                    boomButton, "rippleButtonColor", delay, duration,
                    HideRgbEvaluator.instance, boomButton.buttonColor(), boomButton.pieceColor()
                )
            } else {
                AnimationManager.animate(
                    boomButton, "nonRippleButtonColor", delay, duration,
                    HideRgbEvaluator.instance, boomButton.buttonColor(), boomButton.pieceColor()
                )
            }
        }
        AnimationManager.animate(boomButton, "x", delay, duration, LinearInterpolator(), xs)
        AnimationManager.animate(boomButton, "y", delay, duration, LinearInterpolator(), ys)
        AnimationManager.rotate(boomButton, delay, duration, Ease.getInstance(hideRotateEaseEnum), 0, -rotateDegree)
        AnimationManager.animate("alpha", delay, duration, floatArrayOf(1f, 0f), Ease.getInstance(EaseEnum.Linear), boomButton.goneViews())
        AnimationManager.animate(boomButton, "scaleX", delay, duration, Ease.getInstance(hideScaleEaseEnum), 1f, scaleX)
        AnimationManager.animate(
            boomButton, "scaleY", delay, duration, Ease.getInstance(hideScaleEaseEnum),
            object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    boomButton.willHide()
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    Util.setVisibility(VISIBLE, piece)
                    Util.setVisibility(INVISIBLE, boomButton)
                    boomButton.didHide()
                    animatingViewNumber--
                    finishReboomAnimations()
                }
            }, 1f, scaleY
        )
        if (isUse3DTransformAnimation) {
            val rotate3DAnimation = AnimationManager.getRotate3DAnimation(xs, ys, delay, duration, boomButton)
            rotate3DAnimation.set(boomButton, endPosition.x, endPosition.y)
            boomButton.cameraDistance = 0f
            boomButton.startAnimation(rotate3DAnimation)
        }
    }

    // Region Support Methods

    private fun createBackground() {
        if (background == null) {
            background = BackgroundView(context, this)
        }
    }

    val parentView: ViewGroup
        get() {
            return if (boomInWholeScreen) {
                val activity = Util.scanForActivity(context)
                if (activity == null) {
                    parent as ViewGroup
                } else {
                    activity.window.decorView as ViewGroup
                }
            } else {
                parent as ViewGroup
            }
        }

    private fun clearBackground() {
        Util.setVisibility(GONE, background)
        if (!cacheOptimization || inList || inFragment) {
            background?.removeAllViews()
            (background?.parent as? ViewGroup)?.removeView(background)
            background = null
        }
    }

    private fun createButtons() {
        if (!needToCreateButtons) return
        needToCreateButtons = false
        boomButtons = ArrayList(pieces?.size ?: 0)
        for (i in boomButtonBuilders.indices) {
            boomButtons.add(boomButtonBuilders[i].innerListener(this).index(i).build(context))
        }
        when (buttonEnum) {
            ButtonEnum.SimpleCircle -> {
                simpleCircleButtonRadius = (boomButtonBuilders[0] as SimpleCircleButton.Builder).getButtonRadius().toFloat()
            }
            ButtonEnum.TextInsideCircle -> {
                textInsideCircleButtonRadius = (boomButtonBuilders[0] as TextInsideCircleButton.Builder).getButtonRadius().toFloat()
            }
            ButtonEnum.TextOutsideCircle -> {
                textOutsideCircleButtonWidth = (boomButtonBuilders[0] as TextOutsideCircleButton.Builder).getButtonContentWidth().toFloat()
                textOutsideCircleButtonHeight = (boomButtonBuilders[0] as TextOutsideCircleButton.Builder).getButtonContentHeight().toFloat()
            }
            ButtonEnum.Ham -> {
                hamButtonWidth = (boomButtonBuilders[0] as HamButton.Builder).getButtonWidth().toFloat()
                hamButtonHeight = (boomButtonBuilders[0] as HamButton.Builder).getButtonHeight().toFloat()
            }
            ButtonEnum.Unknown -> { }
        }
    }

    internal fun onBackgroundClicked() {
        if (isAnimating()) return
        onBoomListener?.onBackgroundClick()
        if (isCancelable) reboom()
    }

    private fun calculateStartPositions(force: Boolean) {
        if (!(force || needToCalculateStartPositions || inList || inFragment)) return
        if (!force) needToCalculateStartPositions = false
        startPositions = ArrayList(pieceNumber())
        val rootView = parentView
        val rootViewLocation = IntArray(2)
        rootView.getLocationOnScreen(rootViewLocation)
        pieces?.forEachIndexed { i, piece ->
            val pieceCenterInRootView = PointF()
            val buttonLocation = IntArray(2)
            button?.getLocationOnScreen(buttonLocation)
            pieceCenterInRootView.x = buttonLocation[0] + (piecePositions?.get(i)?.left ?: 0f) -
                    rootViewLocation[0] + (piece.layoutParams.width / 2)
            pieceCenterInRootView.y = buttonLocation[1] + (piecePositions?.get(i)?.top ?: 0f) -
                    rootViewLocation[1] + (piece.layoutParams.height / 2)
            startPositions?.add(pieceCenterInRootView)
        }
    }

    private fun calculateEndPositions() {
        val parentSize = Point(
            background?.layoutParams?.width ?: 0,
            background?.layoutParams?.height ?: 0
        )
        endPositions = when (buttonEnum) {
            ButtonEnum.SimpleCircle -> {
                ButtonPlaceManager.getPositions(parentSize, simpleCircleButtonRadius, boomButtonBuilders.size, this)
            }
            ButtonEnum.TextInsideCircle -> {
                ButtonPlaceManager.getPositions(parentSize, textInsideCircleButtonRadius, boomButtonBuilders.size, this)
            }
            ButtonEnum.TextOutsideCircle -> {
                ButtonPlaceManager.getPositions(
                    parentSize,
                    textOutsideCircleButtonWidth, textOutsideCircleButtonHeight,
                    boomButtonBuilders.size, this
                )
            }
            ButtonEnum.Ham -> {
                ButtonPlaceManager.getPositions(
                    parentSize,
                    hamButtonWidth, hamButtonHeight,
                    boomButtonBuilders.size, this
                )
            }
            ButtonEnum.Unknown -> null
        }
        boomButtons.forEachIndexed { i, boomButton ->
            endPositions?.get(i)?.offset(-boomButton.centerPoint.x, -boomButton.centerPoint.y)
        }
    }

    private fun putBoomButtonInBackground(boomButton: BoomButton, position: PointF): BoomButton {
        createBackground()
        boomButton.place(
            position.x.toInt(),
            position.y.toInt(),
            boomButton.trueWidth(),
            boomButton.trueHeight()
        )
        boomButton.visibility = INVISIBLE
        background?.addView(boomButton)
        return boomButton
    }

    private fun clearViews(force: Boolean) {
        if (force || !cacheOptimization || inList || inFragment) {
            clearButtons()
            clearBackground()
        }
    }

    private fun clearButtons() {
        needToCreateButtons = true
        boomButtons.forEach { boomButton -> background?.removeView(boomButton) }
        boomButtons.clear()
    }

    private fun buttonMaxHeight(): Float {
        return when (buttonEnum) {
            ButtonEnum.SimpleCircle -> simpleCircleButtonRadius * 2
            ButtonEnum.TextInsideCircle -> textInsideCircleButtonRadius * 2
            ButtonEnum.TextOutsideCircle -> textOutsideCircleButtonHeight
            ButtonEnum.Ham -> hamButtonHeight
            ButtonEnum.Unknown -> 0f
        }
    }

    private fun preventDragOutside() {
        var needToAdjustXY = false
        var newX = x
        var newY = y
        val parentView = parent as? ViewGroup ?: return

        if (newX < edgeInsetsInParentView.left) {
            newX = edgeInsetsInParentView.left.toFloat()
            needToAdjustXY = true
        }

        if (newY < edgeInsetsInParentView.top) {
            newY = edgeInsetsInParentView.top.toFloat()
            needToAdjustXY = true
        }

        if (newX > parentView.width - edgeInsetsInParentView.right - width) {
            newX = (parentView.width - edgeInsetsInParentView.right - width).toFloat()
            needToAdjustXY = true
        }

        if (newY > parentView.height - edgeInsetsInParentView.bottom - height) {
            newY = (parentView.height - edgeInsetsInParentView.bottom - height).toFloat()
            needToAdjustXY = true
        }

        if (needToAdjustXY) {
            AnimationManager.animate(this, "x", 0, 300, Ease.getInstance(EaseEnum.EaseOutBack), x, newX)
            AnimationManager.animate(this, "y", 0, 300, Ease.getInstance(EaseEnum.EaseOutBack), y, newY)
        }
    }

    private fun toLayout() {
        if (needToLayout) return
        needToLayout = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (!isInLayout) requestLayout()
        } else {
            requestLayout()
        }
    }

    private fun delayToDoLayoutJobs() {
        if (layoutJobsRunnable == null) {
            layoutJobsRunnable = Runnable { doLayoutJobs() }
        }
        post(layoutJobsRunnable)
    }

    private fun pieceNumber(): Int {
        return when {
            piecePlaceEnum == PiecePlaceEnum.Unknown -> 0
            piecePlaceEnum == PiecePlaceEnum.Share -> boomButtonBuilders.size
            piecePlaceEnum == PiecePlaceEnum.Custom -> customPiecePlacePositions.size
            else -> piecePlaceEnum.pieceNumber()
        }
    }

    override fun onButtonClick(index: Int, boomButton: BoomButton) {
        if (isAnimating()) return
        onBoomListener?.onBoomButtonClick(index)
        if (isAutoHide) reboom()
    }

    private fun placeShareLinesView() {
        if (piecePlaceEnum == PiecePlaceEnum.Share) {
            shareLinesView?.let { button?.removeView(it) }
            shareLinesView = ShareLinesView(context)
            shareLinesView?.setLine1Color(shareLine1Color)
            shareLinesView?.setLine2Color(shareLine2Color)
            shareLinesView?.setLineWidth(shareLineWidth)
            button?.addView(shareLinesView)
            shareLinesView?.place(0, 0, button?.width ?: 0, button?.height ?: 0)
        } else {
            shareLinesView?.let { button?.removeView(it) }
        }
    }

    private fun setShareLinesViewData() {
        if (piecePlaceEnum == PiecePlaceEnum.Share) {
            shareLinesView?.setData(piecePositions, this)
        }
    }

    private fun uninitializedBoomButtons(): Boolean {
        return buttonEnum == ButtonEnum.Unknown
                || piecePlaceEnum == PiecePlaceEnum.Unknown
                || buttonPlaceEnum == ButtonPlaceEnum.Unknown
    }

    private fun isBatterySaveModeTurnOn(): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && powerManager.isPowerSaveMode
    }

    private fun checkAutoBoom() {
        if (isAutoBoomImmediately) boomImmediately()
        else if (isAutoBoom) boom()
        isAutoBoomImmediately = false
        isAutoBoom = false
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isOrientationAdaptable) initOrientationListener()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        orientationEventListener?.disable()
    }

    private fun initOrientationListener() {
        if (orientationEventListener == null) {
            orientationEventListener = object : OrientationEventListener(context) {
                override fun onOrientationChanged(orientation: Int) {
                    if (orientation != lastOrientation && lastOrientation != OrientationEventListener.ORIENTATION_UNKNOWN) {
                        isOrientationChanged = true
                    }
                    lastOrientation = orientation
                }
            }
        }
        if (orientationEventListener?.canDetectOrientation() == true) {
            orientationEventListener?.enable()
        }
    }

    private fun reLayoutAfterOrientationChanged() {
        post {
            background?.reLayout(this)
            calculateStartPositions(true)
            calculateEndPositions()
            when (boomStateEnum) {
                BoomStateEnum.DidReboom -> { }
                BoomStateEnum.DidBoom -> {
                    placeButtons()
                }
                BoomStateEnum.WillBoom,
                BoomStateEnum.WillReboom -> {
                    stopAllAnimations(boomStateEnum == BoomStateEnum.WillBoom)
                    placeButtons()
                }
            }
        }
    }

    private fun placeButtons() {
        boomButtons.forEachIndexed { i, boomButton ->
            val pointF = endPositions?.get(i) ?: return@forEachIndexed
            boomButton.x = pointF.x
            boomButton.y = pointF.y
        }
    }

    private fun stopAllAnimations(isBoomAnimation: Boolean) {
        background?.clearAnimation()
        boomButtons.forEach { it.clearAnimation() }
    }

    // Region Builders

    /**
     * Add a builder to bmb, notice that @needToLayout will be called.
     *
     * @param builder builder
     */
    fun addBuilder(builder: BoomButtonBuilder<*>) {
        boomButtonBuilders.add(builder)
        toLayout()
    }

    /**
     * Set a builder at index, notice that @needToLayout will be called.
     *
     * @param index index
     * @param builder builder
     */
    fun setBuilder(index: Int, builder: BoomButtonBuilder<*>) {
        boomButtonBuilders[index] = builder
        toLayout()
    }

    /**
     * Set builders array, notice that @needToLayout will be called.
     *
     * @param builders builders
     */
    fun setBuilders(builders: ArrayList<BoomButtonBuilder<*>>) {
        boomButtonBuilders = builders
        toLayout()
    }

    /**
     * Get a builder at index.
     *
     * @param index index
     * @return the builder at the index
     */
    fun getBuilder(index: Int): BoomButtonBuilder<*>? {
        return if (index < 0 || index >= boomButtonBuilders.size) null
        else boomButtonBuilders[index]
    }

    /**
     * Remove a builder, notice that @needToLayout will be called.
     *
     * @param builder builder
     */
    fun removeBuilder(builder: BoomButtonBuilder<*>) {
        boomButtonBuilders.remove(builder)
        toLayout()
    }

    /**
     * Remove a builder at index, notice that @needToLayout will be called.
     *
     * @param index index
     */
    fun removeBuilder(index: Int) {
        boomButtonBuilders.removeAt(index)
        toLayout()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        button?.isEnabled = enabled
        setButtonBackground()
    }

    /**
     * Set enable attribute of the boom-button at index.
     *
     * @param index index of the boom-button
     * @param enable whether the boom-button should be enable
     */
    fun setEnable(index: Int, enable: Boolean) {
        if (index < 0) return
        if (index < boomButtonBuilders.size) {
            boomButtonBuilders[index].setUnable(!enable)
        }
        if (index < boomButtons.size) {
            boomButtons[index].isEnabled = enable
        }
    }

    /**
     * Remove all builders, notice that @needToLayout will NOT be called.
     */
    fun clearBuilders() {
        boomButtonBuilders.clear()
    }

    /**
     * Get the array of builders.
     *
     * @return array of builders
     */
    fun getBuilders(): ArrayList<BoomButtonBuilder<*>> = boomButtonBuilders

    /**
     * Get a boom button at index.
     * Notice that the boom button may be null,
     * because boom buttons are cleared in some situation(in list, in fragment, etc.)
     *
     * @param index index
     * @return boom button
     */
    fun getBoomButton(index: Int): BoomButton? {
        return if (index in 0 until boomButtons.size) boomButtons[index] else null
    }

    /**
     * Get boom buttons.
     * Notice that the boom button may be null,
     * because boom buttons are cleared in some situation(in list, in fragment, etc.)
     *
     * @return boom buttons
     */
    fun getBoomButtons(): ArrayList<BoomButton> = boomButtons

    // Region Fade Views

    private fun startBoomAnimationForFadeViews(immediately: Boolean) {
        val duration = if (immediately) 1L else showDuration + showDelay * ((pieces?.size ?: 1) - 1)
        AnimationManager.animate(
            "alpha", 0, duration, floatArrayOf(1f, 0f),
            TimeInterpolator { input -> min(input * 2, 1f) },
            getFadeViews()
        )
    }

    private fun startReboomAnimationForFadeViews(immediately: Boolean) {
        val duration = if (immediately) 1L else hideDuration + hideDelay * ((pieces?.size ?: 1) - 1)
        AnimationManager.animate(
            "alpha", 0, duration, floatArrayOf(0f, 1f),
            TimeInterpolator { input ->
                if (input <= 0.5f) 0f
                else min((input - 0.5f) * 2, 1f)
            },
            getFadeViews()
        )
    }

    private fun getFadeViews(): ArrayList<View> {
        val fadeViews = ArrayList<View>()
        for (i in 0 until childCount) {
            val subView = getChildAt(i)
            if (subView != shadow && subView != button && subView != shareLinesView) {
                fadeViews.add(subView)
            }
        }
        return fadeViews
    }

    // Region Getter and Setter

    fun isCacheOptimization(): Boolean = cacheOptimization

    /**
     * Whether use cache optimization to avoid multi-creating boom-buttons.
     *
     * @param cacheOptimization cache optimization
     */
    fun setCacheOptimization(cacheOptimization: Boolean) {
        this.cacheOptimization = cacheOptimization
    }

    fun isBoomInWholeScreen(): Boolean = boomInWholeScreen

    /**
     * Whether the BMB should boom in the whole screen.
     * If this value is false, then BMB will boom its boom-buttons to its parent-view.
     *
     * @param boomInWholeScreen boom in the whole screen
     */
    fun setBoomInWholeScreen(boomInWholeScreen: Boolean) {
        this.boomInWholeScreen = boomInWholeScreen
    }

    fun isInList(): Boolean = inList

    /**
     * When BMB is used in list-view, it must be setInList(true).
     *
     * @param inList use BMB in list-view
     */
    fun setInList(inList: Boolean) {
        this.inList = inList
    }

    fun isInFragment(): Boolean = inFragment

    /**
     * When BMB is used in fragment, it must be setInFragment(true).
     *
     * @param inFragment use BMB in fragment
     */
    fun setInFragment(inFragment: Boolean) {
        this.inFragment = inFragment
    }

    fun isBackPressListened(): Boolean = isBackPressListened

    /**
     * Whether BMB will reboom when the back-key is pressed.
     *
     * @param backPressListened whether BMB will reboom when the back-key is pressed
     */
    fun setBackPressListened(backPressListened: Boolean) {
        isBackPressListened = backPressListened
    }

    fun isShadowEffect(): Boolean = shadowEffect

    /**
     * Whether BMB should have a shadow-effect.
     * Notice that when you set @backgroundEffect to false, this value will set to false too.
     *
     * @param shadowEffect shadow-effect
     */
    fun setShadowEffect(shadowEffect: Boolean) {
        if (this.shadowEffect == shadowEffect) return
        this.shadowEffect = shadowEffect
        initShadow()
    }

    fun getShadowOffsetX(): Int = shadowOffsetX

    /**
     * Set the BMB's shadow offset in the x-axis.
     *
     * @param shadowOffsetX x-axis shadow offset
     */
    fun setShadowOffsetX(shadowOffsetX: Int) {
        if (this.shadowOffsetX == shadowOffsetX) return
        this.shadowOffsetX = shadowOffsetX
        initShadow()
    }

    fun getShadowOffsetY(): Int = shadowOffsetY

    /**
     * Set the BMB's shadow offset in the y-axis.
     *
     * @param shadowOffsetY y-axis shadow offset
     */
    fun setShadowOffsetY(shadowOffsetY: Int) {
        if (this.shadowOffsetY == shadowOffsetY) return
        this.shadowOffsetY = shadowOffsetY
        initShadow()
    }

    fun getShadowRadius(): Int = shadowRadius

    /**
     * Set the shadow-radius of BMB, please notice that the "radius" here means the extra
     * radius of BMB.
     *
     * @param shadowRadius extra shadow radius
     */
    fun setShadowRadius(shadowRadius: Int) {
        if (this.shadowRadius == shadowRadius) return
        this.shadowRadius = shadowRadius
        initShadow()
    }

    fun getShadowColor(): Int = shadowColor

    /**
     * Set the color of shadow.
     *
     * @param shadowColor color of shadow
     */
    fun setShadowColor(shadowColor: Int) {
        if (this.shadowColor == shadowColor) return
        this.shadowColor = shadowColor
        initShadow()
    }

    /**
     * Set the radius of BMB, if you use this method to set the size of BMB,
     * then you should set the width and height of BMB in .xml file to "wrap-content".
     *
     * @param buttonRadius radius of BMB
     */
    fun setButtonRadius(buttonRadius: Int) {
        if (this.buttonRadius == buttonRadius) return
        this.buttonRadius = buttonRadius
        initButton()
        toLayout()
    }

    /**
     * Set the button-enum for bmb, notice that methods [toLayout], [clearPieces],
     * [clearBuilders], and [clearButtons] will be called.
     *
     * @param buttonEnum button-enum
     */
    fun setButtonEnum(buttonEnum: ButtonEnum) {
        if (this.buttonEnum == buttonEnum) return
        this.buttonEnum = buttonEnum
        clearPieces()
        clearBuilders()
        clearButtons()
        toLayout()
    }

    fun isBackgroundEffect(): Boolean = backgroundEffect

    /**
     * Whether the BMB should have a background effect.
     *
     * @param backgroundEffect background effect
     */
    fun setBackgroundEffect(backgroundEffect: Boolean) {
        if (this.backgroundEffect == backgroundEffect) return
        this.backgroundEffect = backgroundEffect
        setButtonBackground()
        toLayout()
    }

    fun isRippleEffect(): Boolean = rippleEffect

    /**
     * Whether the BMB should have a ripple-effect.
     *
     * @param rippleEffect ripple effect
     */
    fun setRippleEffect(rippleEffect: Boolean) {
        if (this.rippleEffect == rippleEffect) return
        this.rippleEffect = rippleEffect
        setButtonBackground()
        toLayout()
    }

    /**
     * Set the color of BMB at normal-state.
     *
     * @param normalColor the color of BMB at normal-state
     */
    fun setNormalColor(normalColor: Int) {
        if (this.normalColor == normalColor) return
        this.normalColor = normalColor
        setButtonBackground()
        toLayout()
    }

    /**
     * Set the color of BMB at highlighted-state.
     *
     * @param highlightedColor the color of BMB at highlighted-state
     */
    fun setHighlightedColor(highlightedColor: Int) {
        if (this.highlightedColor == highlightedColor) return
        this.highlightedColor = highlightedColor
        setButtonBackground()
        toLayout()
    }

    /**
     * Set the color of BMB at unable-state.
     *
     * @param unableColor the color of BMB at unable-state
     */
    fun setUnableColor(unableColor: Int) {
        if (this.unableColor == unableColor) return
        this.unableColor = unableColor
        setButtonBackground()
        toLayout()
    }

    fun isDraggable(): Boolean = draggable

    /**
     * Make BMB draggable or not.
     *
     * @param draggable draggable or not.
     */
    fun setDraggable(draggable: Boolean) {
        if (this.draggable == draggable) return
        this.draggable = draggable
        initDraggableTouchListener()
    }

    /**
     * Set the radius of dots in BMB.
     *
     * @param dotRadius radius of dot
     */
    fun setDotRadius(dotRadius: Float) {
        if (this.dotRadius == dotRadius) return
        this.dotRadius = dotRadius
        toLayout()
    }

    /**
     * Set the width of hams in BMB.
     *
     * @param hamWidth width of ham
     */
    fun setHamWidth(hamWidth: Float) {
        if (this.hamWidth == hamWidth) return
        this.hamWidth = hamWidth
        toLayout()
    }

    /**
     * Set the height of hams in BMB.
     *
     * @param hamHeight height of ham
     */
    fun setHamHeight(hamHeight: Int) {
        if (this.hamHeight == hamHeight.toFloat()) return
        this.hamHeight = hamHeight.toFloat()
        toLayout()
    }

    /**
     * Set the corner radius of pieces.
     *
     * @param pieceCornerRadius corner radius of pieces
     */
    fun setPieceCornerRadius(pieceCornerRadius: Float) {
        if (this.pieceCornerRadius == pieceCornerRadius) return
        this.pieceCornerRadius = pieceCornerRadius
        toLayout()
    }

    /**
     * Set the horizontal margin between pieces(dots, blocks or hams) in BMB.
     *
     * @param pieceHorizontalMargin horizontal margin between pieces
     */
    fun setPieceHorizontalMargin(pieceHorizontalMargin: Float) {
        if (this.pieceHorizontalMargin == pieceHorizontalMargin) return
        this.pieceHorizontalMargin = pieceHorizontalMargin
        toLayout()
    }

    /**
     * Set the vertical margin between pieces(dots, blocks or hams) in BMB.
     *
     * @param pieceVerticalMargin vertical margin between pieces
     */
    fun setPieceVerticalMargin(pieceVerticalMargin: Float) {
        if (this.pieceVerticalMargin == pieceVerticalMargin) return
        this.pieceVerticalMargin = pieceVerticalMargin
        toLayout()
    }

    /**
     * Set the inclined margin between pieces(dots, blocks or hams) in BMB.
     *
     * @param pieceInclinedMargin inclined margin between pieces
     */
    fun setPieceInclinedMargin(pieceInclinedMargin: Float) {
        if (this.pieceInclinedMargin == pieceInclinedMargin) return
        this.pieceInclinedMargin = pieceInclinedMargin
        toLayout()
    }

    /**
     * Set the length of share-lines in BMB, only works when piece-place-enum is Share.
     *
     * @param shareLineLength length of share-lines, in pixel
     */
    fun setShareLineLength(shareLineLength: Float) {
        if (this.shareLineLength == shareLineLength) return
        this.shareLineLength = shareLineLength
        toLayout()
    }

    /**
     * Set the color of share-line 1(the above), only works when piece-place-enum is Share.
     *
     * @param shareLine1Color color of share-line 1
     */
    fun setShareLine1Color(shareLine1Color: Int) {
        if (this.shareLine1Color == shareLine1Color) return
        this.shareLine1Color = shareLine1Color
        shareLinesView?.let {
            it.setLine1Color(shareLine1Color)
            it.invalidate()
        }
    }

    /**
     * Set the color of share-line 2(the below), only works when piece-place-enum is Share.
     *
     * @param shareLine2Color color of share-line 2
     */
    fun setShareLine2Color(shareLine2Color: Int) {
        if (this.shareLine2Color == shareLine2Color) return
        this.shareLine2Color = shareLine2Color
        shareLinesView?.let {
            it.setLine2Color(shareLine2Color)
            it.invalidate()
        }
    }

    /**
     * Set the width of share-lines in BMB, only works when piece-place-enum is Share.
     *
     * @param shareLineWidth width of share-lines, in pixel
     */
    fun setShareLineWidth(shareLineWidth: Float) {
        if (this.shareLineWidth == shareLineWidth) return
        this.shareLineWidth = shareLineWidth
        shareLinesView?.let {
            it.setLineWidth(shareLineWidth)
            it.invalidate()
        }
    }

    /**
     * Set the piece-place-enum, notice that @requestLayout() will be called.
     *
     * @param piecePlaceEnum piece-place-enum
     */
    fun setPiecePlaceEnum(piecePlaceEnum: PiecePlaceEnum) {
        this.piecePlaceEnum = piecePlaceEnum
        clearPieces()
        toLayout()
    }

    /**
     * The customized positions of pieces. Only works when the piece-place-enum is **custom**.
     *
     * @param customPiecePlacePositions customized positions
     */
    fun setCustomPiecePlacePositions(customPiecePlacePositions: ArrayList<PointF>) {
        if (this.customPiecePlacePositions == customPiecePlacePositions) return
        this.customPiecePlacePositions = customPiecePlacePositions
        clearPieces()
        toLayout()
    }

    fun setDelay(delay: Long) {
        showDelay = delay
        hideDelay = delay
    }

    fun setDuration(duration: Long) {
        showDuration = duration
        hideDuration = duration
    }

    fun setShowEaseEnum(showEaseEnum: EaseEnum) {
        showMoveEaseEnum = showEaseEnum
        showScaleEaseEnum = showEaseEnum
        showRotateEaseEnum = showEaseEnum
    }

    fun setHideEaseEnum(hideEaseEnum: EaseEnum) {
        hideMoveEaseEnum = hideEaseEnum
        hideScaleEaseEnum = hideEaseEnum
        hideRotateEaseEnum = hideEaseEnum
    }
}
