/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.karsu_buttons

import android.content.Context
import android.graphics.PointF
import android.view.LayoutInflater
import android.view.View
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.databinding.KarsuSimpleCircleButtonBinding

@Suppress("unused")
class SimpleCircleButton private constructor(builder: Builder, context: Context) : KarSuButton(context) {

    init {
        this.context = context
        this.buttonEnum = ButtonEnum.SimpleCircle
        init(builder)
    }

    private fun init(builder: Builder) {
        val binding = KarsuSimpleCircleButtonBinding.inflate(LayoutInflater.from(context), this, true)
        initAttrs(builder)
        if (isRound) initShadow(buttonRadius + shadowRadius, binding.shadow)
        else initShadow(shadowCornerRadius, binding.shadow)
        initCircleButton(binding.button)
        initImage()
        centerPoint = PointF(
            (buttonRadius + shadowRadius + shadowOffsetX).toFloat(),
            (buttonRadius + shadowRadius + shadowOffsetY).toFloat()
        )
    }

    override fun initAttrs(builder: KarSuButtonBuilder<*>) {
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

    class Builder : KarSuButtonBuilder<Builder>() {

        fun buttonRadius(buttonRadius: Int): Builder {
            this.buttonRadius = buttonRadius
            return this
        }

        fun buttonCornerRadius(buttonCornerRadius: Int): Builder {
            this.buttonCornerRadius = buttonCornerRadius
            return this
        }

        fun isRound(isRound: Boolean): Builder {
            this.isRound = isRound
            return this
        }

        fun getButtonRadius(): Int = buttonRadius

        override fun build(context: Context): SimpleCircleButton {
            val button = SimpleCircleButton(this, context)
            weakReferenceButton(button)
            return button
        }
    }
}
