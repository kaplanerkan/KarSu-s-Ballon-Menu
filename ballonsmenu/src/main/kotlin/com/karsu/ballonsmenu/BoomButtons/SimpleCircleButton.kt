/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.BoomButtons

import android.content.Context
import android.graphics.PointF
import android.view.LayoutInflater
import android.view.View
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.R

@Suppress("unused")
class SimpleCircleButton private constructor(builder: Builder, context: Context) : BoomButton(context) {

    init {
        this.context = context
        this.buttonEnum = ButtonEnum.SimpleCircle
        init(builder)
    }

    private fun init(builder: Builder) {
        LayoutInflater.from(context).inflate(R.layout.bmb_simple_circle_button, this, true)
        initAttrs(builder)
        if (isRound) initShadow(buttonRadius + shadowRadius)
        else initShadow(shadowCornerRadius)
        initCircleButton()
        initImage()
        centerPoint = PointF(
            (buttonRadius + shadowRadius + shadowOffsetX).toFloat(),
            (buttonRadius + shadowRadius + shadowOffsetY).toFloat()
        )
    }

    override fun initAttrs(builder: BoomButtonBuilder<*>) {
        super.initAttrs(builder)
    }

    override fun type(): ButtonEnum = ButtonEnum.SimpleCircle

    override fun goneViews(): ArrayList<View> {
        val goneViews = ArrayList<View>()
        image?.let { goneViews.add(it) }
        return goneViews
    }

    override fun rotateViews(): ArrayList<View> {
        val rotateViews = ArrayList<View>()
        if (rotateImage) image?.let { rotateViews.add(it) }
        return rotateViews
    }

    override fun trueWidth(): Int = buttonRadius * 2 + shadowRadius * 2 + shadowOffsetX * 2

    override fun trueHeight(): Int = buttonRadius * 2 + shadowRadius * 2 + shadowOffsetY * 2

    override fun contentWidth(): Int = buttonRadius * 2

    override fun contentHeight(): Int = buttonRadius * 2

    override fun toHighlighted() {
        if (lastStateIsNormal && ableToHighlight) {
            toHighlightedImage()
            lastStateIsNormal = false
        }
    }

    override fun toNormal() {
        if (!lastStateIsNormal) {
            toNormalImage()
            lastStateIsNormal = true
        }
    }

    override fun setRotateAnchorPoints() {
        imageRect?.let { rect ->
            image?.pivotX = (buttonRadius - rect.left).toFloat()
            image?.pivotY = (buttonRadius - rect.top).toFloat()
        }
    }

    override fun setSelfScaleAnchorPoints() {
        // No implementation needed
    }

    class Builder : BoomButtonBuilder<Builder>() {

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
         * Gets button radius, used in BMB package.
         *
         * @return the button radius
         */
        fun getButtonRadius(): Int = buttonRadius

        /**
         * Build simple circle button, only used in BMB package.
         *
         * @param context the context
         * @return the simple circle button
         */
        override fun build(context: Context): SimpleCircleButton {
            val button = SimpleCircleButton(this, context)
            weakReferenceButton(button)
            return button
        }
    }
}
