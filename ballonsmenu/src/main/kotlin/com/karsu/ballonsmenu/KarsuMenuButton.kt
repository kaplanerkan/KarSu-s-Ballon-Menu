/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.RippleDrawable
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

import com.karsu.ballonsmenu.animation.AnimationManager
import com.karsu.ballonsmenu.animation.KarSuEnum
import com.karsu.ballonsmenu.animation.Ease
import com.karsu.ballonsmenu.animation.EaseEnum
import com.karsu.ballonsmenu.animation.HideRgbEvaluator
import com.karsu.ballonsmenu.animation.OrderEnum
import com.karsu.ballonsmenu.animation.ShareLinesView
import com.karsu.ballonsmenu.animation.ShowRgbEvaluator
import com.karsu.ballonsmenu.karsu_buttons.KarSuButton
import com.karsu.ballonsmenu.karsu_buttons.KarSuButtonBuilder
import com.karsu.ballonsmenu.karsu_buttons.ButtonPlaceAlignmentEnum
import com.karsu.ballonsmenu.karsu_buttons.ButtonPlaceEnum
import com.karsu.ballonsmenu.karsu_buttons.ButtonPlaceManager
import com.karsu.ballonsmenu.karsu_buttons.HamButton
import com.karsu.ballonsmenu.karsu_buttons.InnerOnKarSuButtonClickListener
import com.karsu.ballonsmenu.karsu_buttons.SimpleCircleButton
import com.karsu.ballonsmenu.karsu_buttons.TextInsideCircleButton
import com.karsu.ballonsmenu.karsu_buttons.TextOutsideCircleButton
import com.karsu.ballonsmenu.piece.KarSuPiece
import com.karsu.ballonsmenu.piece.PiecePlaceEnum
import com.karsu.ballonsmenu.piece.PiecePlaceManager

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@Suppress("unused")
class KarSuMenuButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), InnerOnKarSuButtonClickListener {

    companion object {
        @JvmStatic
        val TAG = "KarSuMenuButton"
    }

    // Basic
    private var needToLayout = true
    private var cacheOptimization = false
    private var karsuInWholeScreen = false
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
    private var shadow: KarsuShadow? = null

    // Button
    var buttonRadius = 0
        private set
    var buttonEnum: ButtonEnum = ButtonEnum.Unknown
        set(value) {
            if (field == value) return
            field = value
            clearPieces()
            clearBuilders()
            clearButtons()
            toLayout()
        }
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

    // piece
    private var pieces: ArrayList<KarSuPiece>? = null
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
        set(value) {
            field = value
            clearPieces()
            toLayout()
        }
    var customPiecePlacePositions: ArrayList<PointF> = ArrayList()
        private set

    // animation
    private var animatingViewNumber = 0
    var onKarSuListener: OnKarSuListener? = null
    var dimColor = 0
        set(value) {
            if (field == value) return
            field = value
            if (karsuStateEnum == KarSuStateEnum.DidKarSu && background != null) {
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
    var karsuEnum: KarSuEnum? = null
    var showMoveEaseEnum: EaseEnum? = null
    var showScaleEaseEnum: EaseEnum? = null
    var showRotateEaseEnum: EaseEnum? = null
    var hideMoveEaseEnum: EaseEnum? = null
    var hideScaleEaseEnum: EaseEnum? = null
    var hideRotateEaseEnum: EaseEnum? = null
    var rotateDegree = 0
    var isUse3DTransformAnimation = false
    var isAutoKarSu = false
    var isAutoKarSuImmediately = false
    private var karsuStateEnum: KarSuStateEnum = KarSuStateEnum.DidRekarsu

    // Background
    private var background: BackgroundView? = null

    // KarSu Buttons
    private var karsuButtons: ArrayList<KarSuButton> = ArrayList()
    private var karsuButtonBuilders: ArrayList<KarSuButtonBuilder<*>> = ArrayList()
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
    private var lastRekarsuIndex = -1

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
        val bmbBinding = com.karsu.ballonsmenu.databinding.BmbBinding.inflate(LayoutInflater.from(context), this, true)
        shadow = bmbBinding.shadow
        button = bmbBinding.button
        initAttrs(context, attrs)
        initShadow()
        initButton()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.KarSuMenuButton, 0, 0)

        try {
            // Basic
            cacheOptimization = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_cacheOptimization, R.bool.default_bmb_cacheOptimization)
            karsuInWholeScreen = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_karsuInWholeScreen, R.bool.default_bmb_karsuInWholeScreen)
            inList = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_inList, R.bool.default_bmb_inList)
            inFragment = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_inFragment, R.bool.default_bmb_inFragment)
            isBackPressListened = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_backPressListened, R.bool.default_bmb_backPressListened)
            isOrientationAdaptable = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_orientationAdaptable, R.bool.default_bmb_orientationAdaptable)

            // Shadow
            shadowEffect = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_shadowEffect, R.bool.default_bmb_shadow_effect)
            shadowRadius = Util.getDimenSize(typedArray, R.styleable.KarSuMenuButton_bmb_shadowRadius, R.dimen.default_bmb_shadow_radius)
            shadowOffsetX = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_shadowOffsetX, R.dimen.default_bmb_shadow_offset_x)
            shadowOffsetY = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_shadowOffsetY, R.dimen.default_bmb_shadow_offset_y)
            shadowColor = Util.getColor(typedArray, R.styleable.KarSuMenuButton_bmb_shadowColor, R.color.default_bmb_shadow_color)

            // Button
            buttonRadius = Util.getDimenSize(typedArray, R.styleable.KarSuMenuButton_bmb_buttonRadius, R.dimen.default_bmb_button_radius)
            buttonEnum = ButtonEnum.getEnum(Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_buttonEnum, R.integer.default_bmb_button_enum))
            backgroundEffect = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_backgroundEffect, R.bool.default_bmb_background_effect)
            rippleEffect = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_rippleEffect, R.bool.default_bmb_ripple_effect)
            normalColor = Util.getColor(typedArray, R.styleable.KarSuMenuButton_bmb_normalColor, R.color.default_bmb_normal_color)
            highlightedColor = Util.getColor(typedArray, R.styleable.KarSuMenuButton_bmb_highlightedColor, R.color.default_bmb_highlighted_color)
            if (highlightedColor == Color.TRANSPARENT) highlightedColor = Util.getDarkerColor(normalColor)
            unableColor = Util.getColor(typedArray, R.styleable.KarSuMenuButton_bmb_unableColor, R.color.default_bmb_unable_color)
            if (unableColor == Color.TRANSPARENT) unableColor = Util.getLighterColor(normalColor)
            draggable = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_draggable, R.bool.default_bmb_draggable)
            edgeInsetsInParentView = Rect(0, 0, 0, 0)
            edgeInsetsInParentView.left = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_edgeInsetsLeft, R.dimen.default_bmb_edgeInsetsLeft)
            edgeInsetsInParentView.top = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_edgeInsetsTop, R.dimen.default_bmb_edgeInsetsTop)
            edgeInsetsInParentView.right = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_edgeInsetsRight, R.dimen.default_bmb_edgeInsetsRight)
            edgeInsetsInParentView.bottom = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_edgeInsetsBottom, R.dimen.default_bmb_edgeInsetsBottom)

            // piece
            dotRadius = Util.getDimenSize(typedArray, R.styleable.KarSuMenuButton_bmb_dotRadius, R.dimen.default_bmb_dotRadius).toFloat()
            hamWidth = Util.getDimenSize(typedArray, R.styleable.KarSuMenuButton_bmb_hamWidth, R.dimen.default_bmb_hamWidth).toFloat()
            hamHeight = Util.getDimenSize(typedArray, R.styleable.KarSuMenuButton_bmb_hamHeight, R.dimen.default_bmb_hamHeight).toFloat()
            pieceCornerRadius = Util.getDimenSize(typedArray, R.styleable.KarSuMenuButton_bmb_pieceCornerRadius, R.dimen.default_bmb_pieceCornerRadius).toFloat()
            pieceHorizontalMargin = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_pieceHorizontalMargin, R.dimen.default_bmb_pieceHorizontalMargin).toFloat()
            pieceVerticalMargin = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_pieceVerticalMargin, R.dimen.default_bmb_pieceVerticalMargin).toFloat()
            pieceInclinedMargin = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_pieceInclinedMargin, R.dimen.default_bmb_pieceInclinedMargin).toFloat()
            shareLineLength = Util.getDimenSize(typedArray, R.styleable.KarSuMenuButton_bmb_sharedLineLength, R.dimen.default_bmb_sharedLineLength).toFloat()
            shareLine1Color = Util.getColor(typedArray, R.styleable.KarSuMenuButton_bmb_shareLine1Color, R.color.default_bmb_shareLine1Color)
            shareLine2Color = Util.getColor(typedArray, R.styleable.KarSuMenuButton_bmb_shareLine2Color, R.color.default_bmb_shareLine2Color)
            shareLineWidth = Util.getDimenSize(typedArray, R.styleable.KarSuMenuButton_bmb_shareLineWidth, R.dimen.default_bmb_shareLineWidth).toFloat()
            piecePlaceEnum = PiecePlaceEnum.getEnum(Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_piecePlaceEnum, R.integer.default_bmb_pieceEnum))

            // animation
            dimColor = Util.getColor(typedArray, R.styleable.KarSuMenuButton_bmb_dimColor, R.color.default_bmb_dimColor)
            showDuration = Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_showDuration, R.integer.default_bmb_showDuration).toLong()
            showDelay = Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_showDelay, R.integer.default_bmb_showDelay).toLong()
            hideDuration = Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_hideDuration, R.integer.default_bmb_hideDuration).toLong()
            hideDelay = Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_hideDelay, R.integer.default_bmb_hideDelay).toLong()
            isCancelable = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_cancelable, R.bool.default_bmb_cancelable)
            isAutoHide = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_autoHide, R.bool.default_bmb_autoHide)
            orderEnum = OrderEnum.getEnum(Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_orderEnum, R.integer.default_bmb_orderEnum))
            frames = Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_frames, R.integer.default_bmb_frames)
            karsuEnum = KarSuEnum.getEnum(Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_karsuEnum, R.integer.default_bmb_karsuEnum))
            showMoveEaseEnum = EaseEnum.getEnum(Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_showMoveEaseEnum, R.integer.default_bmb_showMoveEaseEnum))
            showScaleEaseEnum = EaseEnum.getEnum(Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_showScaleEaseEnum, R.integer.default_bmb_showScaleEaseEnum))
            showRotateEaseEnum = EaseEnum.getEnum(Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_showRotateEaseEnum, R.integer.default_bmb_showRotateEaseEnum))
            hideMoveEaseEnum = EaseEnum.getEnum(Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_hideMoveEaseEnum, R.integer.default_bmb_hideMoveEaseEnum))
            hideScaleEaseEnum = EaseEnum.getEnum(Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_hideScaleEaseEnum, R.integer.default_bmb_hideScaleEaseEnum))
            hideRotateEaseEnum = EaseEnum.getEnum(Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_hideRotateEaseEnum, R.integer.default_bmb_hideRotateEaseEnum))
            rotateDegree = Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_rotateDegree, R.integer.default_bmb_rotateDegree)
            isUse3DTransformAnimation = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_use3DTransformAnimation, R.bool.default_bmb_use3DTransformAnimation)
            isAutoKarSu = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_autoKarSu, R.bool.default_bmb_autoKarSu)
            isAutoKarSuImmediately = Util.getBoolean(typedArray, R.styleable.KarSuMenuButton_bmb_autoKarSuImmediately, R.bool.default_bmb_autoKarSuImmediately)

            // KarSu buttons
            buttonPlaceEnum = ButtonPlaceEnum.getEnum(Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_buttonPlaceEnum, R.integer.default_bmb_buttonPlaceEnum))
            buttonPlaceAlignmentEnum = ButtonPlaceAlignmentEnum.getEnum(Util.getInt(typedArray, R.styleable.KarSuMenuButton_bmb_buttonPlaceAlignmentEnum, R.integer.default_bmb_buttonPlaceAlignmentEnum))
            buttonHorizontalMargin = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_buttonHorizontalMargin, R.dimen.default_bmb_buttonHorizontalMargin).toFloat()
            buttonVerticalMargin = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_buttonVerticalMargin, R.dimen.default_bmb_buttonVerticalMargin).toFloat()
            buttonInclinedMargin = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_buttonInclinedMargin, R.dimen.default_bmb_buttonInclinedMargin).toFloat()
            buttonTopMargin = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_buttonTopMargin, R.dimen.default_bmb_buttonTopMargin).toFloat()
            buttonBottomMargin = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_buttonBottomMargin, R.dimen.default_bmb_buttonBottomMargin).toFloat()
            buttonLeftMargin = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_buttonLeftMargin, R.dimen.default_bmb_buttonLeftMargin).toFloat()
            buttonRightMargin = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_buttonRightMargin, R.dimen.default_bmb_buttonRightMargin).toFloat()
            bottomHamButtonTopMargin = Util.getDimenOffset(typedArray, R.styleable.KarSuMenuButton_bmb_bottomHamButtonTopMargin, R.dimen.default_bmb_bottomHamButtonTopMargin).toFloat()
        } finally {
            typedArray.recycle()
        }
    }

    private fun initShadow() {
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
        button?.setOnClickListener { karsu() }
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
            && (karsuStateEnum == KarSuStateEnum.WillKarSu || karsuStateEnum == KarSuStateEnum.DidKarSu)
        ) {
            rekarsu()
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
        checkAutoKarSu()
    }

    private fun doLayoutJobs() {
        if (uninitializedKarSuButtons()) return
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
            pieces?.add(PiecePlaceManager.createPiece(this, karsuButtonBuilders[i]))
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
                        karsuButtonBuilders.size
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

    // Region animation

    /**
     * Whether BMB is animating.
     *
     * @return Is animating.
     */
    fun isAnimating(): Boolean = animatingViewNumber != 0

    /**
     * Whether the BMB has finished karsuing.
     *
     * @return whether the BMB has finished karsuing
     */
    fun isKarSued(): Boolean = karsuStateEnum == KarSuStateEnum.DidKarSu

    /**
     * Whether the BMB has finished ReKarSuing.
     *
     * @return whether the BMB has finished ReKarSuing
     */
    fun isReKarSued(): Boolean = karsuStateEnum == KarSuStateEnum.DidRekarsu

    /**
     * KarSu the BMB!
     */
    fun karsu() {
        innerKarSu(false)
    }

    /**
     * KarSu the BMB with duration 0!
     */
    fun karsuImmediately() {
        innerKarSu(true)
    }

    private fun innerKarSu(immediately: Boolean) {
        if (isAnimating() || karsuStateEnum != KarSuStateEnum.DidRekarsu) return
        ExceptionManager.judge(this, karsuButtonBuilders)
        karsuStateEnum = KarSuStateEnum.WillKarSu
        onKarSuListener?.onKarSuWillShow()
        calculateStartPositions(false)
        android.util.Log.d("KarSuMenu", "innerKarSu: startPositions=$startPositions")
        createButtons()
        dimBackground(immediately)
        android.util.Log.d("KarSuMenu", "innerKarSu: after dimBackground, bg=${background}, bgVisible=${background?.visibility}, bgParent=${background?.parent?.javaClass?.simpleName}, parentViewClass=${parentView.javaClass.simpleName}, parentViewW=${parentView.width}, parentViewH=${parentView.height}")
        startKarSuAnimations(immediately)
        startKarSuAnimationForFadeViews(immediately)
        if (isBackPressListened) {
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
        }
    }

    /**
     * Re-karsu the BMB!
     */
    fun rekarsu() {
        innerRekarsu(false)
    }

    /**
     * Re-karsu the BMB with duration 0!
     */
    fun rekarsuImmediately() {
        innerRekarsu(true)
    }

    private fun innerRekarsu(immediately: Boolean) {
        if (isAnimating() || karsuStateEnum != KarSuStateEnum.DidKarSu) return
        karsuStateEnum = KarSuStateEnum.WillRekarsu
        onKarSuListener?.onKarSuWillHide()
        lightBackground(immediately)
        startRekarsuAnimations(immediately)
        startRekarsuAnimationForFadeViews(immediately)
        if (isBackPressListened) {
            isFocusable = false
            isFocusableInTouchMode = false
        }
    }

    private fun dimBackground(immediately: Boolean) {
        createBackground()
        Util.setVisibility(VISIBLE, background)
        background?.reLayout(this)
        val duration = if (immediately) 1L else showDuration + showDelay * ((pieces?.size ?: 1) - 1)
        background?.dim(duration, object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                karsuStateEnum = KarSuStateEnum.DidKarSu
                onKarSuListener?.onKarSuDidShow()
            }
        })
        if (piecePlaceEnum == PiecePlaceEnum.Share) {
            AnimationManager.animate(
                shareLinesView, "showProcess", 0, duration,
                Ease.getInstance(EaseEnum.Linear), null, 0f, 1f
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
                Ease.getInstance(EaseEnum.Linear), null, 0f, 1f
            )
        }
    }

    private fun finishRekarsuAnimations() {
        if (isAnimating()) {
            return
        }
        karsuStateEnum = KarSuStateEnum.DidRekarsu
        onKarSuListener?.onKarSuDidHide()
        background?.visibility = GONE
        clearViews(false)
    }

    private fun startKarSuAnimations(immediately: Boolean) {
        android.util.Log.d("KarSuMenu", "startKarSuAnimations: background=$background, bgParent=${background?.parent}, bgVisible=${background?.visibility}, bgW=${background?.width}, bgH=${background?.height}")
        background?.removeAllViews()
        calculateEndPositions()
        val indexes = if (piecePlaceEnum == PiecePlaceEnum.Share) {
            AnimationManager.getOrderIndex(OrderEnum.DEFAULT, pieces?.size ?: 0)
        } else {
            AnimationManager.getOrderIndex(orderEnum, pieces?.size ?: 0)
        }
        // Fix the strange bug when use3DTransformAnimation is true
        if (lastRekarsuIndex != -1 && isUse3DTransformAnimation) {
            karsuButtons[lastRekarsuIndex] = karsuButtonBuilders[lastRekarsuIndex]
                .innerListener(this).index(lastRekarsuIndex).build(context)
        }
        // Reverse to keep the former karsu-buttons are above than the latter(z-axis)
        for (i in indexes.size - 1 downTo 0) {
            val index = indexes[i]
            val karsuButton = karsuButtons[index]
            val startPosition = PointF(
                (startPositions?.get(index)?.x ?: 0f) - (karsuButton.centerPoint?.x ?: 0f),
                (startPositions?.get(index)?.y ?: 0f) - (karsuButton.centerPoint?.y ?: 0f)
            )
            android.util.Log.d("KarSuMenu", "startKarSuAnim[$index]: startPos=$startPosition, endPos=${endPositions?.get(index)}, centerPoint=${karsuButton.centerPoint}, btnVisible=${karsuButton.visibility}, btnW=${karsuButton.width}, btnH=${karsuButton.height}")
            putKarSuButtonInBackground(karsuButton, startPosition)
            startEachKarSuAnimation(
                pieces?.get(index), karsuButton, startPosition,
                endPositions?.get(index), i, immediately
            )
        }
        android.util.Log.d("KarSuMenu", "startKarSuAnimations: bgChildCount=${background?.childCount}")
    }

    private fun startRekarsuAnimations(immediately: Boolean) {
        val indexes = if (piecePlaceEnum == PiecePlaceEnum.Share) {
            AnimationManager.getOrderIndex(OrderEnum.REVERSE, pieces?.size ?: 0)
        } else {
            AnimationManager.getOrderIndex(orderEnum, pieces?.size ?: 0)
        }
        lastRekarsuIndex = indexes[indexes.size - 1]
        for (index in indexes) {
            karsuButtons[index].bringToFront()
        }
        for (i in indexes.indices) {
            val index = indexes[i]
            val karsuButton = karsuButtons[index]
            val startPosition = PointF(
                (startPositions?.get(index)?.x ?: 0f) - (karsuButton.centerPoint?.x ?: 0f),
                (startPositions?.get(index)?.y ?: 0f) - (karsuButton.centerPoint?.y ?: 0f)
            )
            startEachRekarsuAnimation(
                pieces?.get(index), karsuButton,
                endPositions?.get(index), startPosition, i, immediately
            )
        }
    }

    private fun startEachKarSuAnimation(
        piece: KarSuPiece?,
        karsuButton: KarSuButton,
        startPosition: PointF,
        endPosition: PointF?,
        delayFactor: Int,
        immediately: Boolean
    ) {
        if (isBatterySaveModeTurnOn()) {
            post {
                innerStartEachKarSuAnimation(piece, karsuButton, startPosition, endPosition, delayFactor, immediately)
            }
        } else {
            innerStartEachKarSuAnimation(piece, karsuButton, startPosition, endPosition, delayFactor, immediately)
        }
    }

    private fun innerStartEachKarSuAnimation(
        piece: KarSuPiece?,
        karsuButton: KarSuButton,
        startPosition: PointF,
        endPosition: PointF?,
        delayFactor: Int,
        immediately: Boolean
    ) {
        animatingViewNumber++
        val xs = FloatArray(frames + 1)
        val ys = FloatArray(frames + 1)
        val scaleX = (piece?.width ?: 1) * 1.0f / karsuButton.contentWidth()
        val scaleY = (piece?.height ?: 1) * 1.0f / karsuButton.contentHeight()
        val delay = if (immediately) 1L else showDelay * delayFactor
        val duration = if (immediately) 1L else showDuration
        android.util.Log.d("KarSuMenu", "innerAnim: pieceW=${piece?.width}, pieceH=${piece?.height}, contentW=${karsuButton.contentWidth()}, contentH=${karsuButton.contentHeight()}, trueW=${karsuButton.trueWidth()}, trueH=${karsuButton.trueHeight()}, scaleX=$scaleX, scaleY=$scaleY, delay=$delay, duration=$duration, frames=$frames, karsuEnum=$karsuEnum")
        karsuButton.setSelfScaleAnchorPoints()
        karsuButton.scaleX = scaleX
        karsuButton.scaleY = scaleY
        karsuButton.hideAllGoneViews()
        AnimationManager.calculateShowXY(
            karsuEnum,
            PointF(
                (background?.layoutParams?.width ?: 0).toFloat(),
                (background?.layoutParams?.height ?: 0).toFloat()
            ),
            Ease.getInstance(showMoveEaseEnum), frames, startPosition, endPosition, xs, ys
        )
        android.util.Log.d("KarSuMenu", "innerAnim: xs[0]=${xs[0]}, xs[last]=${xs[frames]}, ys[0]=${ys[0]}, ys[last]=${ys[frames]}, btnLeft=${karsuButton.left}, btnTop=${karsuButton.top}")
        if (karsuButton.isNeededColorAnimation()) {
            if (karsuButton.prepareColorTransformAnimation()) {
                AnimationManager.animate(
                    karsuButton, "rippleButtonColor", delay, duration,
                    ShowRgbEvaluator.instance, null, karsuButton.pieceColor(), karsuButton.buttonColor()
                )
            } else {
                AnimationManager.animate(
                    karsuButton, "nonRippleButtonColor", delay, duration,
                    ShowRgbEvaluator.instance, null, karsuButton.pieceColor(), karsuButton.buttonColor()
                )
            }
        }
        AnimationManager.animate(karsuButton, "x", delay, duration, LinearInterpolator(), null, *xs)
        AnimationManager.animate(karsuButton, "y", delay, duration, LinearInterpolator(), null, *ys)
        AnimationManager.rotate(karsuButton, delay, duration, Ease.getInstance(showRotateEaseEnum), 0, rotateDegree)
        AnimationManager.animate("alpha", delay, duration, floatArrayOf(0f, 1f), Ease.getInstance(EaseEnum.Linear), karsuButton.goneViews())
        AnimationManager.animate(karsuButton, "scaleX", delay, duration, Ease.getInstance(showScaleEaseEnum), null, scaleX, 1f)
        AnimationManager.animate(
            karsuButton, "scaleY", delay, duration, Ease.getInstance(showScaleEaseEnum),
            object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    Util.setVisibility(INVISIBLE, piece)
                    Util.setVisibility(VISIBLE, karsuButton)
                    karsuButton.willShow()
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    karsuButton.didShow()
                    animatingViewNumber--
                }
            }, scaleY, 1f
        )

        if (isUse3DTransformAnimation) {
            val rotate3DAnimation = AnimationManager.getRotate3DAnimation(xs, ys, delay, duration, karsuButton)
            rotate3DAnimation.set(karsuButton, startPosition.x, startPosition.y)
            karsuButton.cameraDistance = 0f
            karsuButton.startAnimation(rotate3DAnimation)
        }
    }

    private fun startEachRekarsuAnimation(
        piece: KarSuPiece?,
        karsuButton: KarSuButton,
        startPosition: PointF?,
        endPosition: PointF,
        delayFactor: Int,
        immediately: Boolean
    ) {
        animatingViewNumber++
        val xs = FloatArray(frames + 1)
        val ys = FloatArray(frames + 1)
        val scaleX = (piece?.width ?: 1) * 1.0f / karsuButton.contentWidth()
        val scaleY = (piece?.height ?: 1) * 1.0f / karsuButton.contentHeight()
        val delay = if (immediately) 1L else hideDelay * delayFactor
        val duration = if (immediately) 1L else hideDuration
        AnimationManager.calculateHideXY(
            karsuEnum,
            PointF(
                (background?.layoutParams?.width ?: 0).toFloat(),
                (background?.layoutParams?.height ?: 0).toFloat()
            ),
            Ease.getInstance(hideMoveEaseEnum), frames, startPosition, endPosition, xs, ys
        )
        if (karsuButton.isNeededColorAnimation()) {
            if (karsuButton.prepareColorTransformAnimation()) {
                AnimationManager.animate(
                    karsuButton, "rippleButtonColor", delay, duration,
                    HideRgbEvaluator.instance, null, karsuButton.buttonColor(), karsuButton.pieceColor()
                )
            } else {
                AnimationManager.animate(
                    karsuButton, "nonRippleButtonColor", delay, duration,
                    HideRgbEvaluator.instance, null, karsuButton.buttonColor(), karsuButton.pieceColor()
                )
            }
        }
        AnimationManager.animate(karsuButton, "x", delay, duration, LinearInterpolator(), null, *xs)
        AnimationManager.animate(karsuButton, "y", delay, duration, LinearInterpolator(), null, *ys)
        AnimationManager.rotate(karsuButton, delay, duration, Ease.getInstance(hideRotateEaseEnum), 0, -rotateDegree)
        AnimationManager.animate("alpha", delay, duration, floatArrayOf(1f, 0f), Ease.getInstance(EaseEnum.Linear), karsuButton.goneViews())
        AnimationManager.animate(karsuButton, "scaleX", delay, duration, Ease.getInstance(hideScaleEaseEnum), null, 1f, scaleX)
        AnimationManager.animate(
            karsuButton, "scaleY", delay, duration, Ease.getInstance(hideScaleEaseEnum),
            object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    karsuButton.willHide()
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    Util.setVisibility(VISIBLE, piece)
                    Util.setVisibility(INVISIBLE, karsuButton)
                    karsuButton.didHide()
                    animatingViewNumber--
                    finishRekarsuAnimations()
                }
            }, 1f, scaleY
        )
        if (isUse3DTransformAnimation) {
            val rotate3DAnimation = AnimationManager.getRotate3DAnimation(xs, ys, delay, duration, karsuButton)
            rotate3DAnimation.set(karsuButton, endPosition.x, endPosition.y)
            karsuButton.cameraDistance = 0f
            karsuButton.startAnimation(rotate3DAnimation)
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
            return if (karsuInWholeScreen) {
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
            background?.release()
            background = null
        }
    }

    private fun createButtons() {
        if (!needToCreateButtons) return
        needToCreateButtons = false
        karsuButtons = ArrayList(pieces?.size ?: 0)
        for (i in karsuButtonBuilders.indices) {
            karsuButtons.add(karsuButtonBuilders[i].innerListener(this).index(i).build(context))
        }
        when (buttonEnum) {
            ButtonEnum.SimpleCircle -> {
                simpleCircleButtonRadius = (karsuButtonBuilders[0] as SimpleCircleButton.Builder).getButtonRadius().toFloat()
            }
            ButtonEnum.TextInsideCircle -> {
                textInsideCircleButtonRadius = (karsuButtonBuilders[0] as TextInsideCircleButton.Builder).getButtonRadius().toFloat()
            }
            ButtonEnum.TextOutsideCircle -> {
                textOutsideCircleButtonWidth = (karsuButtonBuilders[0] as TextOutsideCircleButton.Builder).getButtonContentWidth().toFloat()
                textOutsideCircleButtonHeight = (karsuButtonBuilders[0] as TextOutsideCircleButton.Builder).getButtonContentHeight().toFloat()
            }
            ButtonEnum.Ham -> {
                hamButtonWidth = (karsuButtonBuilders[0] as HamButton.Builder).getButtonWidth().toFloat()
                hamButtonHeight = (karsuButtonBuilders[0] as HamButton.Builder).getButtonHeight().toFloat()
            }
            ButtonEnum.Unknown -> { }
        }
    }

    internal fun onBackgroundClicked() {
        if (isAnimating()) return
        onKarSuListener?.onBackgroundClick()
        if (isCancelable) rekarsu()
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
        android.util.Log.d("KarSuMenu", "calculateEndPositions: parentSize=$parentSize, buttonEnum=$buttonEnum, alignment=$buttonPlaceAlignmentEnum, buttonPlaceEnum=$buttonPlaceEnum")
        endPositions = when (buttonEnum) {
            ButtonEnum.SimpleCircle -> {
                ButtonPlaceManager.getPositions(parentSize, simpleCircleButtonRadius, karsuButtonBuilders.size, this)
            }
            ButtonEnum.TextInsideCircle -> {
                ButtonPlaceManager.getPositions(parentSize, textInsideCircleButtonRadius, karsuButtonBuilders.size, this)
            }
            ButtonEnum.TextOutsideCircle -> {
                ButtonPlaceManager.getPositions(
                    parentSize,
                    textOutsideCircleButtonWidth, textOutsideCircleButtonHeight,
                    karsuButtonBuilders.size, this
                )
            }
            ButtonEnum.Ham -> {
                ButtonPlaceManager.getPositions(
                    parentSize,
                    hamButtonWidth, hamButtonHeight,
                    karsuButtonBuilders.size, this
                )
            }
            ButtonEnum.Unknown -> null
        }
        android.util.Log.d("KarSuMenu", "calculateEndPositions: raw endPositions=$endPositions")
        karsuButtons.forEachIndexed { i, karsuButton ->
            endPositions?.get(i)?.offset(-(karsuButton.centerPoint?.x ?: 0f), -(karsuButton.centerPoint?.y ?: 0f))
        }
        android.util.Log.d("KarSuMenu", "calculateEndPositions: final endPositions=$endPositions")
    }

    private fun putKarSuButtonInBackground(karsuButton: KarSuButton, position: PointF): KarSuButton {
        createBackground()
        karsuButton.place(
            position.x.toInt(),
            position.y.toInt(),
            karsuButton.trueWidth(),
            karsuButton.trueHeight()
        )
        karsuButton.visibility = INVISIBLE
        background?.addView(karsuButton)
        return karsuButton
    }

    private fun clearViews(force: Boolean) {
        if (force || !cacheOptimization || inList || inFragment) {
            clearButtons()
            clearBackground()
        }
    }

    private fun clearButtons() {
        needToCreateButtons = true
        karsuButtons.forEach { karsuButton -> background?.removeView(karsuButton) }
        karsuButtons.clear()
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
            AnimationManager.animate(this, "x", 0, 300, Ease.getInstance(EaseEnum.EaseOutBack), null, x, newX)
            AnimationManager.animate(this, "y", 0, 300, Ease.getInstance(EaseEnum.EaseOutBack), null, y, newY)
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
            piecePlaceEnum == PiecePlaceEnum.Share -> karsuButtonBuilders.size
            piecePlaceEnum == PiecePlaceEnum.Custom -> customPiecePlacePositions.size
            else -> piecePlaceEnum.pieceNumber()
        }
    }

    override fun onButtonClick(index: Int, karsuButton: KarSuButton) {
        if (isAnimating()) return
        onKarSuListener?.onKarSuButtonClick(index)
        if (isAutoHide) rekarsu()
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

    private fun uninitializedKarSuButtons(): Boolean {
        return buttonEnum == ButtonEnum.Unknown
                || piecePlaceEnum == PiecePlaceEnum.Unknown
                || buttonPlaceEnum == ButtonPlaceEnum.Unknown
    }

    private fun isBatterySaveModeTurnOn(): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && powerManager.isPowerSaveMode
    }

    private fun checkAutoKarSu() {
        if (isAutoKarSuImmediately) karsuImmediately()
        else if (isAutoKarSu) karsu()
        isAutoKarSuImmediately = false
        isAutoKarSu = false
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isOrientationAdaptable) initOrientationListener()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // Cancel pending layout jobs
        layoutJobsRunnable?.let { removeCallbacks(it) }
        // Stop all running animations
        karsuButtons.forEach { it.clearAnimation() }
        background?.clearAnimation()
        animatingViewNumber = 0
        // Disable and release orientation listener
        orientationEventListener?.disable()
        orientationEventListener = null
        // Clear listener to prevent Activity leaks
        onKarSuListener = null
        // Clear background view
        clearBackground()
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
            when (karsuStateEnum) {
                KarSuStateEnum.DidRekarsu -> { }
                KarSuStateEnum.DidKarSu -> {
                    placeButtons()
                }
                KarSuStateEnum.WillKarSu,
                KarSuStateEnum.WillRekarsu -> {
                    stopAllAnimations(karsuStateEnum == KarSuStateEnum.WillKarSu)
                    placeButtons()
                }
            }
        }
    }

    private fun placeButtons() {
        karsuButtons.forEachIndexed { i, karsuButton ->
            val pointF = endPositions?.get(i) ?: return@forEachIndexed
            karsuButton.x = pointF.x
            karsuButton.y = pointF.y
        }
    }

    private fun stopAllAnimations(isKarSuAnimation: Boolean) {
        background?.clearAnimation()
        karsuButtons.forEach { it.clearAnimation() }
    }

    // Region Builders

    /**
     * Add a builder to bmb, notice that @needToLayout will be called.
     *
     * @param builder builder
     */
    fun addBuilder(builder: KarSuButtonBuilder<*>) {
        karsuButtonBuilders.add(builder)
        toLayout()
    }

    /**
     * Set a builder at index, notice that @needToLayout will be called.
     *
     * @param index index
     * @param builder builder
     */
    fun setBuilder(index: Int, builder: KarSuButtonBuilder<*>) {
        karsuButtonBuilders[index] = builder
        toLayout()
    }

    /**
     * Set builders array, notice that @needToLayout will be called.
     *
     * @param builders builders
     */
    fun setBuilders(builders: ArrayList<KarSuButtonBuilder<*>>) {
        karsuButtonBuilders = builders
        toLayout()
    }

    /**
     * Get a builder at index.
     *
     * @param index index
     * @return the builder at the index
     */
    fun getBuilder(index: Int): KarSuButtonBuilder<*>? {
        return if (index < 0 || index >= karsuButtonBuilders.size) null
        else karsuButtonBuilders[index]
    }

    /**
     * Remove a builder, notice that @needToLayout will be called.
     *
     * @param builder builder
     */
    fun removeBuilder(builder: KarSuButtonBuilder<*>) {
        karsuButtonBuilders.remove(builder)
        toLayout()
    }

    /**
     * Remove a builder at index, notice that @needToLayout will be called.
     *
     * @param index index
     */
    fun removeBuilder(index: Int) {
        karsuButtonBuilders.removeAt(index)
        toLayout()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        button?.isEnabled = enabled
        setButtonBackground()
    }

    /**
     * Set enable attribute of the karsu-button at index.
     *
     * @param index index of the karsu-button
     * @param enable whether the karsu-button should be enable
     */
    fun setEnable(index: Int, enable: Boolean) {
        if (index < 0) return
        if (index < karsuButtonBuilders.size) {
            karsuButtonBuilders[index].setUnable(!enable)
        }
        if (index < karsuButtons.size) {
            karsuButtons[index].isEnabled = enable
        }
    }

    /**
     * Remove all builders, notice that @needToLayout will NOT be called.
     */
    fun clearBuilders() {
        karsuButtonBuilders.clear()
    }

    /**
     * Get the array of builders.
     *
     * @return array of builders
     */
    fun getBuilders(): ArrayList<KarSuButtonBuilder<*>> = karsuButtonBuilders

    /**
     * Get a karsu button at index.
     * Notice that the karsu button may be null,
     * because karsu buttons are cleared in some situation(in list, in fragment, etc.)
     *
     * @param index index
     * @return karsu button
     */
    fun getKarSuButton(index: Int): KarSuButton? {
        return if (index in 0 until karsuButtons.size) karsuButtons[index] else null
    }

    /**
     * Get karsu buttons.
     * Notice that the karsu button may be null,
     * because karsu buttons are cleared in some situation(in list, in fragment, etc.)
     *
     * @return karsu buttons
     */
    fun getKarSuButtons(): ArrayList<KarSuButton> = karsuButtons

    // Region Fade Views

    private fun startKarSuAnimationForFadeViews(immediately: Boolean) {
        val duration = if (immediately) 1L else showDuration + showDelay * ((pieces?.size ?: 1) - 1)
        AnimationManager.animate(
            "alpha", 0, duration, floatArrayOf(1f, 0f),
            TimeInterpolator { input -> min(input * 2, 1f) },
            getFadeViews()
        )
    }

    private fun startRekarsuAnimationForFadeViews(immediately: Boolean) {
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
     * Whether use cache optimization to avoid multi-creating karsu-buttons.
     *
     * @param cacheOptimization cache optimization
     */
    fun setCacheOptimization(cacheOptimization: Boolean) {
        this.cacheOptimization = cacheOptimization
    }

    fun isKarSuInWholeScreen(): Boolean = karsuInWholeScreen

    /**
     * Whether the BMB should karsu in the whole screen.
     * If this value is false, then BMB will karsu its karsu-buttons to its parent-view.
     *
     * @param karsuInWholeScreen karsu in the whole screen
     */
    fun setKarSuInWholeScreen(karsuInWholeScreen: Boolean) {
        this.karsuInWholeScreen = karsuInWholeScreen
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
     * Whether BMB will rekarsu when the back-key is pressed.
     *
     * @param backPressListened whether BMB will rekarsu when the back-key is pressed
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

    /**
     * Duration property for show and hide animations.
     */
    var duration: Long
        get() = showDuration
        set(value) {
            showDuration = value
            hideDuration = value
        }
}
