/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.karsu_buttons

import android.content.Context
import android.graphics.PointF
import android.view.LayoutInflater
import android.view.View
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.R

@Suppress("unused")
class TextOutsideCircleButton private constructor(builder: Builder, context: Context) : KarSuButton(context) {

    init {
        this.context = context
        this.buttonEnum = ButtonEnum.TextOutsideCircle
        init(builder)
    }

    private fun init(builder: Builder) {
        LayoutInflater.from(context).inflate(R.layout.karsu_text_outside_circle_button, this, true)
        initAttrs(builder)
        initTextOutsideCircleButtonLayout()
        if (isRound) initShadow(buttonRadius + shadowRadius)
        else initShadow(shadowCornerRadius)
        initCircleButton()
        initText(layout)
        initImage()
        centerPoint = PointF(trueRadius.toFloat(), trueRadius.toFloat())
    }

    override fun initAttrs(builder: KarSuButtonBuilder<*>) {
        super.initAttrs(builder)
    }

    override fun type(): ButtonEnum = ButtonEnum.TextOutsideCircle

    override fun goneViews(): ArrayList<View> {
        val goneViews = ArrayList<View>()
        image?.let { goneViews.add(it) }
        text?.let { goneViews.add(it) }
        return goneViews
    }

    override fun rotateViews(): ArrayList<View> {
        val rotateViews = ArrayList<View>()
        if (rotateImage) image?.let { rotateViews.add(it) }
        if (rotateText) text?.let { rotateViews.add(it) }
        return rotateViews
    }

    override fun trueWidth(): Int = trueRadius * 2

    override fun trueHeight(): Int = trueRadius * 2

    override fun contentWidth(): Int = buttonRadius * 2

    override fun contentHeight(): Int = buttonRadius * 2

    override fun toHighlighted() {
        if (lastStateIsNormal && ableToHighlight) {
            toHighlightedImage()
            toHighlightedText()
            lastStateIsNormal = false
        }
    }

    override fun toNormal() {
        if (!lastStateIsNormal) {
            toNormalImage()
            toNormalText()
            lastStateIsNormal = true
        }
    }

    override fun setRotateAnchorPoints() {
        imageRect?.let { rect ->
            image?.pivotX = (buttonRadius - rect.left).toFloat()
            image?.pivotY = (buttonRadius - rect.top).toFloat()
        }
        textRect?.let { rect ->
            text?.pivotX = (trueRadius - rect.left).toFloat()
            text?.pivotY = (trueRadius - rect.top).toFloat()
        }
    }

    override fun setSelfScaleAnchorPoints() {
        // No implementation needed
    }

    class Builder : KarSuButtonWithTextBuilder<Builder>() {

        /**
         * Whether the text-view should rotate.
         *
         * @param rotateText rotate or not
         * @return the builder
         */
        fun rotateText(rotateText: Boolean): Builder {
            this.rotateText = rotateText
            return this
        }

        /**
         * Set the top-margin between text-view and the circle button.
         * Don't need to mind the shadow, BMB will mind this in code.
         *
         * @param textTopMargin top-margin between text-view and the circle button, bigger or
         *                      equal to zero.
         * @return the builder
         */
        fun textTopMargin(textTopMargin: Int): Builder {
            this.textTopMargin = if (textTopMargin < 0) 0 else textTopMargin
            return this
        }

        /**
         * The width of text-view.
         *
         * @param textWidth width of text-view
         * @return the builder
         */
        fun textWidth(textWidth: Int): Builder {
            this.textWidth = textWidth
            return this
        }

        /**
         * The height of text-view.
         *
         * @param textHeight height of text-view
         * @return the builder
         */
        fun textHeight(textHeight: Int): Builder {
            this.textHeight = textHeight
            return this
        }

        /**
         * The radius of boom-button, in pixel.
         *
         * @param buttonRadius the button radius
         * @return the builder
         */
        fun buttonRadius(buttonRadius: Int): Builder {
            this.buttonRadius = buttonRadius
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
         * Whether the button is a circle shape.
         *
         * @param isRound is or not
         * @return the builder
         */
        fun isRound(isRound: Boolean): Builder {
            this.isRound = isRound
            return this
        }

        /**
         * Get the height of content.
         * This method is used to calculate the position of boom-button on the screen,
         * don't use it outside.
         *
         * @return the width of content in text-outside-circle-button
         */
        fun getButtonContentWidth(): Int {
            var width = buttonRadius * 2
            width = maxOf(width, textWidth)
            return width
        }

        /**
         * Get the height of content.
         * This method is used to calculate the position of boom-button on the screen,
         * don't use it outside.
         *
         * @return the height of content in text-outside-circle-button
         */
        fun getButtonContentHeight(): Int {
            var height = buttonRadius * 2
            height = maxOf(height, textRect.bottom - shadowOffsetY - shadowRadius)
            return height
        }

        /**
         * Gets button radius.
         *
         * @return the button radius
         */
        fun getButtonRadius(): Int = buttonRadius

        /**
         * Build text-inside circle button, only used in BMB package.
         *
         * @param context the context
         * @return the simple circle button
         */
        override fun build(context: Context): TextOutsideCircleButton {
            val button = TextOutsideCircleButton(this, context)
            weakReferenceButton(button)
            return button
        }
    }
}
