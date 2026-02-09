/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.karsu_buttons

import android.content.Context
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.R
import com.karsu.ballonsmenu.Util

@Suppress("unused")
class HamButton private constructor(builder: Builder, context: Context) : KarSuButton(context) {

    init {
        this.context = context
        this.buttonEnum = ButtonEnum.Ham
        init(builder)
    }

    private fun init(builder: Builder) {
        LayoutInflater.from(context).inflate(R.layout.karsu_ham_button, this, true)
        initAttrs(builder)
        initShadow(builder.shadowCornerRadius)
        initHamButton()
        initText(button)
        initSubText(button)
        initImage()
        centerPoint = PointF(
            buttonWidth / 2.0f + shadowRadius + shadowOffsetX,
            buttonHeight / 2.0f + shadowRadius + shadowOffsetY
        )
    }

    override fun initAttrs(builder: KarSuButtonBuilder<*>) {
        super.initAttrs(builder)
    }

    override fun type(): ButtonEnum = ButtonEnum.Ham

    override fun goneViews(): ArrayList<View> {
        val goneViews = ArrayList<View>()
        image?.let { goneViews.add(it) }
        text?.let { goneViews.add(it) }
        subText?.let { goneViews.add(it) }
        return goneViews
    }

    override fun rotateViews(): ArrayList<View> {
        val rotateViews = ArrayList<View>()
        if (rotateImage) image?.let { rotateViews.add(it) }
        return rotateViews
    }

    override fun trueWidth(): Int = buttonWidth + shadowRadius * 2 + shadowOffsetX * 2

    override fun trueHeight(): Int = buttonHeight + shadowRadius * 2 + shadowOffsetY * 2

    override fun contentWidth(): Int = buttonWidth

    override fun contentHeight(): Int = buttonHeight

    override fun toHighlighted() {
        if (lastStateIsNormal && ableToHighlight) {
            toHighlightedImage()
            toHighlightedText()
            toHighlightedSubText()
            lastStateIsNormal = false
        }
    }

    override fun toNormal() {
        if (!lastStateIsNormal) {
            toNormalImage()
            toNormalText()
            toNormalSubText()
            lastStateIsNormal = true
        }
    }

    override fun setRotateAnchorPoints() {
        // No implementation needed
    }

    override fun setSelfScaleAnchorPoints() {
        // No implementation needed
    }

    class Builder : KarSuButtonWithTextBuilder<Builder>() {

        init {
            imageRect = Rect(0, 0, Util.dp2px(60f), Util.dp2px(60f))
            textRect = Rect(Util.dp2px(70f), Util.dp2px(10f), Util.dp2px(280f), Util.dp2px(40f))
            textGravity = Gravity.START or Gravity.CENTER_VERTICAL
            textSize = 15
        }

        /**
         * Whether the ham-button contains a sub text-view.
         *
         * @param containsSubText contains a sub text-view or not
         * @return the builder
         */
        fun containsSubText(containsSubText: Boolean): Builder {
            this.containsSubText = containsSubText
            return this
        }

        /**
         * Set the sub-text when boom-button is at normal-state.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subNormalText sub-text
         * @return the builder
         */
        fun subNormalText(subNormalText: String?): Builder {
            if (this.subNormalText == null || this.subNormalText != subNormalText) {
                this.subNormalText = subNormalText
                button()?.let { button ->
                    button.subNormalText = subNormalText
                    button.updateSubText()
                }
            }
            return this
        }

        /**
         * Set the sub-text resource when boom-button is at normal-state.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subNormalTextRes sub-text resource
         * @return the builder
         */
        fun subNormalTextRes(subNormalTextRes: Int): Builder {
            if (this.subNormalTextRes != subNormalTextRes) {
                this.subNormalTextRes = subNormalTextRes
                button()?.let { button ->
                    button.subNormalTextRes = subNormalTextRes
                    button.updateSubText()
                }
            }
            return this
        }

        /**
         * Set the sub-text when boom-button is at highlighted-state.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subHighlightedText sub-text
         * @return the builder
         */
        fun subHighlightedText(subHighlightedText: String?): Builder {
            if (this.subHighlightedText == null || this.subHighlightedText != subHighlightedText) {
                this.subHighlightedText = subHighlightedText
                button()?.let { button ->
                    button.subHighlightedText = subHighlightedText
                    button.updateSubText()
                }
            }
            return this
        }

        /**
         * Set the sub-text resource when boom-button is at highlighted-state.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subHighlightedTextRes sub-text resource
         * @return the builder
         */
        fun subHighlightedTextRes(subHighlightedTextRes: Int): Builder {
            if (this.subHighlightedTextRes != subHighlightedTextRes) {
                this.subHighlightedTextRes = subHighlightedTextRes
                button()?.let { button ->
                    button.subHighlightedTextRes = subHighlightedTextRes
                    button.updateSubText()
                }
            }
            return this
        }

        /**
         * Set the sub-text when boom-button is at unable-state.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subUnableText sub-text
         * @return the builder
         */
        fun subUnableText(subUnableText: String?): Builder {
            if (this.subUnableText == null || this.subUnableText != subUnableText) {
                this.subUnableText = subUnableText
                button()?.let { button ->
                    button.subUnableText = subUnableText
                    button.updateSubText()
                }
            }
            return this
        }

        /**
         * Set the sub-text resource when boom-button is at unable-state.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subUnableTextRes sub-text resource
         * @return the builder
         */
        fun subUnableTextRes(subUnableTextRes: Int): Builder {
            if (this.subUnableTextRes != subUnableTextRes) {
                this.subUnableTextRes = subUnableTextRes
                button()?.let { button ->
                    button.subUnableTextRes = subUnableTextRes
                    button.updateSubText()
                }
            }
            return this
        }

        /**
         * Set the color of sub-text when boom-button is at normal-state.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subNormalTextColor color of sub-text
         * @return the builder
         */
        fun subNormalTextColor(subNormalTextColor: Int): Builder {
            if (this.subNormalTextColor != subNormalTextColor) {
                this.subNormalTextColor = subNormalTextColor
                button()?.let { button ->
                    button.subNormalTextColor = subNormalTextColor
                    button.updateSubText()
                }
            }
            return this
        }

        /**
         * Set the color of sub-text when boom-button is at normal-state.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subNormalTextColorRes color resource of sub-text
         * @return the builder
         */
        fun subNormalTextColorRes(subNormalTextColorRes: Int): Builder {
            if (this.subNormalTextColorRes != subNormalTextColorRes) {
                this.subNormalTextColorRes = subNormalTextColorRes
                button()?.let { button ->
                    button.subNormalTextColorRes = subNormalTextColorRes
                    button.updateSubText()
                }
            }
            return this
        }

        /**
         * Set the color of sub-text when boom-button is at highlighted-state.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subHighlightedTextColor color of sub-text
         * @return the builder
         */
        fun subHighlightedTextColor(subHighlightedTextColor: Int): Builder {
            if (this.subHighlightedTextColor != subHighlightedTextColor) {
                this.subHighlightedTextColor = subHighlightedTextColor
                button()?.let { button ->
                    button.subHighlightedTextColor = subHighlightedTextColor
                    button.updateSubText()
                }
            }
            return this
        }

        /**
         * Set the color of sub-text when boom-button is at highlighted-state.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subHighlightedTextColorRes color resource of sub-text
         * @return the builder
         */
        fun subHighlightedTextColorRes(subHighlightedTextColorRes: Int): Builder {
            if (this.subHighlightedTextColorRes != subHighlightedTextColorRes) {
                this.subHighlightedTextColorRes = subHighlightedTextColorRes
                button()?.let { button ->
                    button.subHighlightedTextColorRes = subHighlightedTextColorRes
                    button.updateSubText()
                }
            }
            return this
        }

        /**
         * Set the color of sub-text when boom-button is at unable-state.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subUnableTextColor color of sub-text
         * @return the builder
         */
        fun subUnableTextColor(subUnableTextColor: Int): Builder {
            if (this.subUnableTextColor != subUnableTextColor) {
                this.subUnableTextColor = subUnableTextColor
                button()?.let { button ->
                    button.subUnableTextColor = subUnableTextColor
                    button.updateSubText()
                }
            }
            return this
        }

        /**
         * Set the color of sub-text when boom-button is at unable-state.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subUnableTextColorRes color resource of sub-text
         * @return the builder
         */
        fun subUnableTextColorRes(subUnableTextColorRes: Int): Builder {
            if (this.subUnableTextColorRes != subUnableTextColorRes) {
                this.subUnableTextColorRes = subUnableTextColorRes
                button()?.let { button ->
                    button.subUnableTextColorRes = subUnableTextColorRes
                    button.updateSubText()
                }
            }
            return this
        }

        /**
         * Set the rect of sub-text.
         * By this method, you can set the position and size of the sub-text-view in boom-button.
         * For example, builder.textRect(new Rect(0, 50, 100, 100)) will make the
         * sub-text-view's size to be 100 * 50 and margin-top to be 50 pixel.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subTextRect the sub-text rect, in pixel.
         * @return the builder
         */
        fun subTextRect(subTextRect: Rect): Builder {
            if (this.subTextRect != subTextRect) {
                this.subTextRect = subTextRect
                button()?.let { button ->
                    button.subTextRect = subTextRect
                    button.updateSubTextRect()
                }
            }
            return this
        }

        /**
         * Set the padding of sub-text.
         * By this method, you can control the padding in the sub-text-view.
         * For instance, builder.textPadding(new Rect(10, 10, 10, 10)) will make the
         * sub-text-view content 10-pixel padding to itself.
         *
         * Synchronicity: If the boom-button existed,
         * then synchronize this change to boom-button.
         *
         * @param subTextPadding the sub-text padding
         * @return the builder
         */
        fun subTextPadding(subTextPadding: Rect): Builder {
            if (this.subTextPadding != subTextPadding) {
                this.subTextPadding = subTextPadding
                button()?.let { button ->
                    button.subTextPadding = subTextPadding
                    button.updateSubTextPadding()
                }
            }
            return this
        }

        /**
         * Set the typeface of the sub-text.
         *
         * @param subTypeface typeface
         * @return the builder
         */
        fun subTypeface(subTypeface: Typeface?): Builder {
            this.subTypeface = subTypeface
            return this
        }

        /**
         * Set the maximum of the lines of sub-text-view.
         *
         * @param subMaxLines maximum lines
         * @return the builder
         */
        fun subMaxLines(subMaxLines: Int): Builder {
            this.subMaxLines = subMaxLines
            return this
        }

        /**
         * Set the gravity of sub-text-view.
         *
         * @param subTextGravity gravity, for example, Gravity.CENTER
         * @return the builder
         */
        fun subTextGravity(subTextGravity: Int): Builder {
            this.subTextGravity = subTextGravity
            return this
        }

        /**
         * Set the ellipsize of the sub-text-view.
         *
         * @param subEllipsize ellipsize
         * @return the builder
         */
        fun subEllipsize(subEllipsize: TextUtils.TruncateAt): Builder {
            this.subEllipsize = subEllipsize
            return this
        }

        /**
         * Set the text size of the sub-text-view.
         *
         * @param subTextSize size of sub-text, in sp
         * @return the builder
         */
        fun subTextSize(subTextSize: Int): Builder {
            this.subTextSize = subTextSize
            return this
        }

        /**
         * Set the width of boom-button, in pixel.
         *
         * @param buttonWidth width of button
         * @return the builder
         */
        fun buttonWidth(buttonWidth: Int): Builder {
            this.buttonWidth = buttonWidth
            return this
        }

        /**
         * Set the height of boom-button, in pixel.
         *
         * @param buttonHeight height of button
         * @return the builder
         */
        fun buttonHeight(buttonHeight: Int): Builder {
            this.buttonHeight = buttonHeight
            return this
        }

        /**
         * Set the corner-radius of button.
         *
         * @param buttonCornerRadius corner-radius of button
         * @return the builder
         */
        fun buttonCornerRadius(buttonCornerRadius: Int): Builder {
            this.buttonCornerRadius = buttonCornerRadius
            return this
        }

        /**
         * Get the width of boom-button.
         *
         * @return width of button
         */
        fun getButtonWidth(): Int = buttonWidth

        /**
         * Get the height of boom-button
         *
         * @return height of button
         */
        fun getButtonHeight(): Int = buttonHeight

        /**
         * Build ham button, don't use this method.
         *
         * @param context the context
         * @return the ham button
         */
        override fun build(context: Context): HamButton {
            val button = HamButton(this, context)
            weakReferenceButton(button)
            return button
        }
    }
}
