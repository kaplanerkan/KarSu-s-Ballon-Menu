package com.nightonke.boommenu.BoomButtons

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.Gravity
import com.nightonke.boommenu.Piece.BoomPiece
import com.nightonke.boommenu.Util
import java.lang.ref.WeakReference

/**
 * Created by Weiping Huang at 02:20 on 16/11/18
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 */

@Suppress("UNCHECKED_CAST")
abstract class BoomButtonBuilder<T : BoomButtonBuilder<T>> {

    private var piece: BoomPiece? = null
    private var boomButtonWeakReference: WeakReference<BoomButton>? = null

    // Basic
    @JvmField var index: Int = -1
    @JvmField var listener: InnerOnBoomButtonClickListener? = null
    @JvmField var onBMClickListener: OnBMClickListener? = null
    @JvmField var rotateImage: Boolean = true
    @JvmField var rotateText: Boolean = true
    @JvmField var containsSubText: Boolean = true

    // piece
    @JvmField var pieceColor: Int? = null
    @JvmField var pieceColorRes: Int = 0

    // Shadow
    @JvmField var shadowEffect: Boolean = true
    @JvmField var shadowOffsetX: Int = Util.dp2px(0f)
    @JvmField var shadowOffsetY: Int = Util.dp2px(3f)
    @JvmField var shadowRadius: Int = Util.dp2px(8f)
    @JvmField var shadowColor: Int = Color.parseColor("#88757575")
    @JvmField var shadowCornerRadius: Int = Util.dp2px(5f)

    // Images
    @JvmField var normalImageRes: Int = 0
    @JvmField var highlightedImageRes: Int = 0
    @JvmField var unableImageRes: Int = 0
    @JvmField var normalImageDrawable: Drawable? = null
    @JvmField var highlightedImageDrawable: Drawable? = null
    @JvmField var unableImageDrawable: Drawable? = null
    @JvmField var imageRect: Rect = Rect(Util.dp2px(10f), Util.dp2px(10f), Util.dp2px(70f), Util.dp2px(70f))
    @JvmField var imagePadding: Rect = Rect(0, 0, 0, 0)

    // Text
    @JvmField var normalTextRes: Int = 0
    @JvmField var highlightedTextRes: Int = 0
    @JvmField var unableTextRes: Int = 0
    @JvmField var normalText: String? = null
    @JvmField var highlightedText: String? = null
    @JvmField var unableText: String? = null
    @JvmField var normalTextColor: Int = Color.WHITE
    @JvmField var normalTextColorRes: Int = 0
    @JvmField var highlightedTextColor: Int = Color.WHITE
    @JvmField var highlightedTextColorRes: Int = 0
    @JvmField var unableTextColor: Int = Color.WHITE
    @JvmField var unableTextColorRes: Int = 0
    @JvmField var textRect: Rect = Rect(Util.dp2px(15f), Util.dp2px(52f), Util.dp2px(65f), Util.dp2px(72f))
    @JvmField var textPadding: Rect = Rect(0, 0, 0, 0)
    @JvmField var typeface: Typeface? = null
    @JvmField var maxLines: Int = 1
    @JvmField var textGravity: Int = Gravity.CENTER
    @JvmField var ellipsize: TextUtils.TruncateAt = TextUtils.TruncateAt.MARQUEE
    @JvmField var textSize: Int = 10

    // Sub text
    @JvmField var subNormalTextRes: Int = 0
    @JvmField var subHighlightedTextRes: Int = 0
    @JvmField var subUnableTextRes: Int = 0
    @JvmField var subNormalText: String? = null
    @JvmField var subHighlightedText: String? = null
    @JvmField var subUnableText: String? = null
    @JvmField var subNormalTextColor: Int = Color.WHITE
    @JvmField var subNormalTextColorRes: Int = 0
    @JvmField var subHighlightedTextColor: Int = Color.WHITE
    @JvmField var subHighlightedTextColorRes: Int = 0
    @JvmField var subUnableTextColor: Int = Color.WHITE
    @JvmField var subUnableTextColorRes: Int = 0
    @JvmField var subTextRect: Rect = Rect(Util.dp2px(70f), Util.dp2px(35f), Util.dp2px(280f), Util.dp2px(55f))
    @JvmField var subTextPadding: Rect = Rect(0, 0, 0, 0)
    @JvmField var subTypeface: Typeface? = null
    @JvmField var subMaxLines: Int = 1
    @JvmField var subTextGravity: Int = Gravity.START or Gravity.CENTER_VERTICAL
    @JvmField var subEllipsize: TextUtils.TruncateAt = TextUtils.TruncateAt.MARQUEE
    @JvmField var subTextSize: Int = 10

    // Text for text-outside-circle-button
    @JvmField var textTopMargin: Int = Util.dp2px(5f)
    @JvmField var textWidth: Int = Util.dp2px(80f)
    @JvmField var textHeight: Int = Util.dp2px(20f)

    // Button
    @JvmField var rippleEffect: Boolean = true
    @JvmField var normalColor: Int = Util.getColor()
    @JvmField var normalColorRes: Int = 0
    @JvmField var highlightedColor: Int = Util.getColor()
    @JvmField var highlightedColorRes: Int = 0
    @JvmField var unableColor: Int = Util.getColor()
    @JvmField var unableColorRes: Int = 0
    @JvmField var unable: Boolean = false
    @JvmField var buttonRadius: Int = Util.dp2px(40f)
    @JvmField var buttonWidth: Int = Util.dp2px(300f)
    @JvmField var buttonHeight: Int = Util.dp2px(60f)
    @JvmField var buttonCornerRadius: Int = Util.dp2px(5f)
    @JvmField var isRound: Boolean = true  // only for simple circle/text inside/text outside circle button

    abstract fun build(context: Context): BoomButton

    /**
     * Get the piece color, only used in BMB package.
     *
     * @param context context
     * @return color of piece
     */
    fun pieceColor(context: Context): Int {
        return when {
            pieceColor == null && pieceColorRes == 0 -> {
                if (unable) Util.getColor(context, unableColorRes, unableColor)
                else Util.getColor(context, normalColorRes, normalColor)
            }
            pieceColor == null -> Util.getColor(context, pieceColorRes)
            else -> Util.getColor(context, pieceColorRes, pieceColor!!)
        }
    }

    fun setUnable(unable: Boolean) {
        this.unable = unable
    }

    fun button(): BoomButton? = boomButtonWeakReference?.get()

    internal fun weakReferenceButton(button: BoomButton): BoomButton {
        boomButtonWeakReference = WeakReference(button)
        return button
    }

    /**
     * Set the piece of the builder, only used in BMB package.
     *
     * @param piece the piece
     */
    fun piece(piece: BoomPiece?) {
        this.piece = piece
    }

    /**
     * Set the index of the boom-button, only used in BMB package.
     *
     * @param index the index
     * @return the builder
     */
    fun index(index: Int): BoomButtonBuilder<*> {
        this.index = index
        return this
    }

    /**
     * Set the listener of the boom-button, only used in BMB package.
     *
     * @param listener the listener
     * @return the builder
     */
    fun innerListener(listener: InnerOnBoomButtonClickListener?): BoomButtonBuilder<*> {
        this.listener = listener
        return this
    }

    /**
     * Set listener for when the boom-button is clicked.
     *
     * @param onBMClickListener OnBMClickListener
     * @return the builder
     */
    fun listener(onBMClickListener: OnBMClickListener?): T {
        this.onBMClickListener = onBMClickListener
        return this as T
    }

    /**
     * Whether the image-view should rotate.
     *
     * @param rotateImage rotate or not
     * @return the builder
     */
    fun rotateImage(rotateImage: Boolean): T {
        this.rotateImage = rotateImage
        return this as T
    }

    /**
     * Whether the boom-button should have a shadow effect.
     *
     * @param shadowEffect have shadow effect or not
     * @return the builder
     */
    fun shadowEffect(shadowEffect: Boolean): T {
        this.shadowEffect = shadowEffect
        return this as T
    }

    /**
     * Set the horizontal shadow-offset of the boom-button.
     *
     * @param shadowOffsetX the shadow offset x
     * @return the builder
     */
    fun shadowOffsetX(shadowOffsetX: Int): T {
        this.shadowOffsetX = shadowOffsetX
        return this as T
    }

    /**
     * Set the vertical shadow-offset of the boom-button.
     *
     * @param shadowOffsetY the shadow offset y
     * @return the builder
     */
    fun shadowOffsetY(shadowOffsetY: Int): T {
        this.shadowOffsetY = shadowOffsetY
        return this as T
    }

    /**
     * Set the radius of shadow of the boom-button.
     *
     * @param shadowRadius the shadow radius
     * @return the builder
     */
    fun shadowRadius(shadowRadius: Int): T {
        this.shadowRadius = shadowRadius
        return this as T
    }

    /**
     * Set the corner-radius of the shadow.
     *
     * @param shadowCornerRadius corner-radius of the shadow
     * @return the builder
     */
    fun shadowCornerRadius(shadowCornerRadius: Int): T {
        this.shadowCornerRadius = shadowCornerRadius
        return this as T
    }

    /**
     * Set the color of the shadow of boom-button.
     *
     * @param shadowColor the shadow color
     * @return the builder
     */
    fun shadowColor(shadowColor: Int): T {
        this.shadowColor = shadowColor
        return this as T
    }

    /**
     * Set the image drawable when boom-button is at normal-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param normalImageDrawable the normal image drawable
     * @return the builder
     */
    fun normalImageDrawable(normalImageDrawable: Drawable?): T {
        if (this.normalImageDrawable != normalImageDrawable) {
            this.normalImageDrawable = normalImageDrawable
            button()?.let { button ->
                button.normalImageDrawable = normalImageDrawable
                button.updateImage()
            }
        }
        return this as T
    }

    /**
     * Set the image resource when boom-button is at normal-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param normalImageRes the normal image res
     * @return the builder
     */
    fun normalImageRes(normalImageRes: Int): T {
        if (this.normalImageRes != normalImageRes) {
            this.normalImageRes = normalImageRes
            button()?.let { button ->
                button.normalImageRes = normalImageRes
                button.updateImage()
            }
        }
        return this as T
    }

    /**
     * Set the image drawable when boom-button is at highlighted-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param highlightedImageDrawable the highlighted image drawable
     * @return the builder
     */
    fun highlightedImageDrawable(highlightedImageDrawable: Drawable?): T {
        if (this.highlightedImageDrawable != highlightedImageDrawable) {
            this.highlightedImageDrawable = highlightedImageDrawable
            button()?.let { button ->
                button.highlightedImageDrawable = highlightedImageDrawable
                button.updateImage()
            }
        }
        return this as T
    }

    /**
     * Set the image resource when boom-button is at highlighted-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param highlightedImageRes the highlighted image res
     * @return the builder
     */
    fun highlightedImageRes(highlightedImageRes: Int): T {
        if (this.highlightedImageRes != highlightedImageRes) {
            this.highlightedImageRes = highlightedImageRes
            button()?.let { button ->
                button.highlightedImageRes = highlightedImageRes
                button.updateImage()
            }
        }
        return this as T
    }

    /**
     * Set the image drawable when boom-button is at unable-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unableImageDrawable the unable image drawable
     * @return the builder
     */
    fun unableImageDrawable(unableImageDrawable: Drawable?): T {
        if (this.unableImageDrawable != unableImageDrawable) {
            this.unableImageDrawable = unableImageDrawable
            button()?.let { button ->
                button.unableImageDrawable = unableImageDrawable
                button.updateImage()
            }
        }
        return this as T
    }

    /**
     * Set the image resource when boom-button is at unable-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unableImageRes the unable image res
     * @return the builder
     */
    fun unableImageRes(unableImageRes: Int): T {
        if (this.unableImageRes != unableImageRes) {
            this.unableImageRes = unableImageRes
            button()?.let { button ->
                button.unableImageRes = unableImageRes
                button.updateImage()
            }
        }
        return this as T
    }

    /**
     * Set the rect of image.
     * By this method, you can set the position and size of the image-view in boom-button.
     * For example, builder.imageRect(new Rect(0, 50, 100, 100)) will make the
     * image-view's size to be 100 * 50 and margin-top to be 50 pixel.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param imageRect the image rect, in pixel.
     * @return the builder
     */
    fun imageRect(imageRect: Rect): T {
        if (this.imageRect != imageRect) {
            this.imageRect = imageRect
            button()?.let { button ->
                button.imageRect = imageRect
                button.updateImageRect()
            }
        }
        return this as T
    }

    /**
     * Set the padding of image.
     * By this method, you can control the padding in the image-view.
     * For instance, builder.imagePadding(new Rect(10, 10, 10, 10)) will make the
     * image-view content 10-pixel padding to itself.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param imagePadding the image padding
     * @return the builder
     */
    fun imagePadding(imagePadding: Rect): T {
        if (this.imagePadding != imagePadding) {
            this.imagePadding = imagePadding
            button()?.let { button ->
                button.imagePadding = imagePadding
                button.updateImagePadding()
            }
        }
        return this as T
    }

    /**
     * Whether the boom-button should have a ripple effect.
     *
     * @param rippleEffect the ripple effect
     * @return the builder
     */
    fun rippleEffect(rippleEffect: Boolean): T {
        this.rippleEffect = rippleEffect
        return this as T
    }

    /**
     * The color of boom-button when it is at normal-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param normalColor the normal color
     * @return the builder
     */
    fun normalColor(normalColor: Int): T {
        if (this.normalColor != normalColor) {
            this.normalColor = normalColor
            button()?.let { button ->
                button.normalColor = normalColor
                button.updateButtonDrawable()
            }
        }
        return this as T
    }

    /**
     * The resource of color of boom-button when it is at normal-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param normalColorRes resource of normal color
     * @return the builder
     */
    fun normalColorRes(normalColorRes: Int): T {
        if (this.normalColorRes != normalColorRes) {
            this.normalColorRes = normalColorRes
            button()?.let { button ->
                button.normalColorRes = normalColorRes
                button.updateButtonDrawable()
            }
        }
        return this as T
    }

    /**
     * The color of boom-button when it is at highlighted-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param highlightedColor the highlighted color
     * @return the builder
     */
    fun highlightedColor(highlightedColor: Int): T {
        if (this.highlightedColor != highlightedColor) {
            this.highlightedColor = highlightedColor
            button()?.let { button ->
                button.highlightedColor = highlightedColor
                button.updateButtonDrawable()
            }
        }
        return this as T
    }

    /**
     * The resource of color of boom-button when it is at highlighted-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param highlightedColorRes resource of highlighted color
     * @return the builder
     */
    fun highlightedColorRes(highlightedColorRes: Int): T {
        if (this.highlightedColorRes != highlightedColorRes) {
            this.highlightedColorRes = highlightedColorRes
            button()?.let { button ->
                button.highlightedColorRes = highlightedColorRes
                button.updateButtonDrawable()
            }
        }
        return this as T
    }

    /**
     * The color of boom-button when it is at unable-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unableColor the unable color
     * @return the builder
     */
    fun unableColor(unableColor: Int): T {
        if (this.unableColor != unableColor) {
            this.unableColor = unableColor
            button()?.let { button ->
                button.unableColor = unableColor
                button.updateButtonDrawable()
            }
        }
        return this as T
    }

    /**
     * The resource of color of boom-button when it is at unable-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unableColorRes resource of unable color
     * @return the builder
     */
    fun unableColorRes(unableColorRes: Int): T {
        if (this.unableColorRes != unableColorRes) {
            this.unableColorRes = unableColorRes
            button()?.let { button ->
                button.unableColorRes = unableColorRes
                button.updateButtonDrawable()
            }
        }
        return this as T
    }

    /**
     * The color of boom-button when it is just a piece.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param pieceColor color of piece
     * @return the builder
     */
    fun pieceColor(pieceColor: Int): T {
        if (this.pieceColor == null || this.pieceColor != pieceColor) {
            this.pieceColor = pieceColor
            button()?.pieceColor = pieceColor
            piece?.setColor(pieceColor)
        }
        return this as T
    }

    /**
     * The resource of color of boom-button when it is just a piece.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param pieceColorRes resource of color of piece
     * @return the builder
     */
    fun pieceColorRes(pieceColorRes: Int): T {
        if (this.pieceColorRes != pieceColorRes) {
            this.pieceColorRes = pieceColorRes
            button()?.pieceColorRes = pieceColorRes
            piece?.setColorRes(pieceColorRes)
        }
        return this as T
    }

    /**
     * Whether the boom-button is unable, default value is false.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unable the unable
     * @return the builder
     */
    fun unable(unable: Boolean): T {
        if (this.unable != unable) {
            this.unable = unable
            button()?.let { button ->
                button.unable = unable
                button.updateUnable()
                piece?.setColor(button.pieceColor())
            }
        }
        return this as T
    }
}
