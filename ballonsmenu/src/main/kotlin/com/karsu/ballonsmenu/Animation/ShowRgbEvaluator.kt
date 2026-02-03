/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.Animation

import android.animation.TypeEvaluator

class ShowRgbEvaluator private constructor() : TypeEvaluator<Int> {

    override fun evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
        val startA = (startValue shr 24) and 0xff
        val startR = (startValue shr 16) and 0xff
        val startG = (startValue shr 8) and 0xff
        val startB = startValue and 0xff

        val endA = (endValue shr 24) and 0xff
        val endR = (endValue shr 16) and 0xff
        val endG = (endValue shr 8) and 0xff
        val endB = endValue and 0xff

        val trueFraction = speedMap(fraction)

        return ((startA + (trueFraction * (endA - startA)).toInt()) shl 24) or
                ((startR + (trueFraction * (endR - startR)).toInt()) shl 16) or
                ((startG + (trueFraction * (endG - startG)).toInt()) shl 8) or
                (startB + (trueFraction * (endB - startB)).toInt())
    }

    private fun speedMap(fraction: Float): Float {
        var trueSpeed = fraction * 2
        if (trueSpeed > 1) trueSpeed = 1f
        if (trueSpeed < 0) trueSpeed = 0f
        return trueSpeed
    }

    companion object {
        @JvmStatic
        val instance: ShowRgbEvaluator = ShowRgbEvaluator()
    }
}
