/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.Animation

import android.animation.TimeInterpolator
import android.graphics.PointF
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin

class Ease private constructor(private val easeEnum: EaseEnum) : TimeInterpolator {

    private var start = PointF(0f, 0f)
    private var end = PointF(1f, 1f)
    private val a = PointF()
    private val b = PointF()
    private val c = PointF()
    private var ableToDefineWithControlPoints = true

    init {
        when (easeEnum) {
            EaseEnum.Linear -> init(0.000, 0.000, 1.000, 1.000)
            EaseEnum.EaseInSine -> init(0.470, 0.000, 0.745, 0.715)
            EaseEnum.EaseOutSine -> init(0.390, 0.575, 0.565, 1.000)
            EaseEnum.EaseInOutSine -> init(0.445, 0.050, 0.550, 0.950)
            EaseEnum.EaseInQuad -> init(0.550, 0.085, 0.680, 0.530)
            EaseEnum.EaseOutQuad -> init(0.250, 0.460, 0.450, 0.940)
            EaseEnum.EaseInOutQuad -> init(0.455, 0.030, 0.515, 0.955)
            EaseEnum.EaseInCubic -> init(0.550, 0.055, 0.675, 0.190)
            EaseEnum.EaseOutCubic -> init(0.215, 0.610, 0.355, 1.000)
            EaseEnum.EaseInOutCubic -> init(0.645, 0.045, 0.335, 1.000)
            EaseEnum.EaseInQuart -> init(0.895, 0.030, 0.685, 0.220)
            EaseEnum.EaseOutQuart -> init(0.165, 0.840, 0.440, 1.000)
            EaseEnum.EaseInOutQuart -> init(0.770, 0.000, 0.175, 1.000)
            EaseEnum.EaseInQuint -> init(0.755, 0.050, 0.855, 0.060)
            EaseEnum.EaseOutQuint -> init(0.230, 1.000, 0.320, 1.000)
            EaseEnum.EaseInOutQuint -> init(0.860, 0.000, 0.070, 1.000)
            EaseEnum.EaseInCirc -> init(0.600, 0.040, 0.980, 0.335)
            EaseEnum.EaseOutCirc -> init(0.075, 0.820, 0.165, 1.000)
            EaseEnum.EaseInOutCirc -> init(0.785, 0.135, 0.150, 0.860)
            EaseEnum.EaseInExpo -> init(0.950, 0.050, 0.795, 0.035)
            EaseEnum.EaseOutExpo -> init(0.190, 1.000, 0.220, 1.000)
            EaseEnum.EaseInOutExpo -> init(1.000, 0.000, 0.000, 1.000)
            EaseEnum.EaseInBack -> init(0.600, -0.20, 0.735, 0.045)
            EaseEnum.EaseOutBack -> init(0.174, 0.885, 0.320, 1.275)
            EaseEnum.EaseInOutBack -> init(0.680, -0.55, 0.265, 1.550)
            EaseEnum.EaseInBounce,
            EaseEnum.EaseOutBounce,
            EaseEnum.EaseInOutBounce,
            EaseEnum.EaseInElastic,
            EaseEnum.EaseOutElastic,
            EaseEnum.EaseInOutElastic -> {
                ableToDefineWithControlPoints = false
            }
            EaseEnum.Unknown -> throw RuntimeException("Ease-enum not found!")
        }
    }

    private fun init(startX: Float, startY: Float, endX: Float, endY: Float) {
        ableToDefineWithControlPoints = true
        start = PointF(startX, startY)
        end = PointF(endX, endY)
    }

    private fun init(startX: Double, startY: Double, endX: Double, endY: Double) {
        init(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat())
    }

    override fun getInterpolation(offset: Float): Float {
        return if (ableToDefineWithControlPoints) {
            getBezierCoordinateY(offset)
        } else {
            when (easeEnum) {
                EaseEnum.EaseInBounce -> getEaseInBounceOffset(offset)
                EaseEnum.EaseInElastic -> getEaseInElasticOffset(offset)
                EaseEnum.EaseOutBounce -> getEaseOutBounceOffset(offset)
                EaseEnum.EaseOutElastic -> getEaseOutElasticOffset(offset)
                EaseEnum.EaseInOutBounce -> getEaseInOutBounceOffset(offset)
                EaseEnum.EaseInOutElastic -> getEaseInOutElasticOffset(offset)
                else -> throw RuntimeException("Wrong ease-enum initialize method.")
            }
        }
    }

    private fun getBezierCoordinateY(time: Float): Float {
        if (start.x == 0f && start.y == 0f && end.x == 1f && end.y == 1f) return time
        c.y = 3 * start.y
        b.y = 3 * (end.y - start.y) - c.y
        a.y = 1 - c.y - b.y
        return time * (c.y + time * (b.y + time * a.y))
    }

    private fun getEaseInBounceOffset(offset: Float): Float {
        val b = 0f
        val c = 1f
        val d = 1f
        return c - getEaseBounceOffsetHelper2(d - offset, 0f, c, d) + b
    }

    private fun getEaseBounceOffsetHelper1(t: Float, b: Float, c: Float, d: Float): Float {
        return c - getEaseBounceOffsetHelper2(d - t, 0f, c, d) + b
    }

    private fun getEaseBounceOffsetHelper2(t: Float, b: Float, c: Float, d: Float): Float {
        var time = t / d
        return when {
            time < (1 / 2.75f) -> c * (7.5625f * time * time) + b
            time < (2 / 2.75f) -> {
                time -= 1.5f / 2.75f
                c * (7.5625f * time * time + 0.75f) + b
            }
            time < (2.5f / 2.75f) -> {
                time -= 2.25f / 2.75f
                c * (7.5625f * time * time + 0.9375f) + b
            }
            else -> {
                time -= 2.625f / 2.75f
                c * (7.5625f * time * time + 0.984375f) + b
            }
        }
    }

    private fun getEaseOutBounceOffset(t: Float): Float {
        val b = 0f
        val c = 1f
        val d = 1f
        var time = t / d
        return when {
            time < (1 / 2.75f) -> c * (7.5625f * time * time) + b
            time < (2 / 2.75f) -> {
                time -= (1.5f / 2.75f)
                c * (7.5625f * time * time + 0.75f) + b
            }
            time < (2.5f / 2.75f) -> {
                time -= (2.25f / 2.75f)
                c * (7.5625f * time * time + 0.9375f) + b
            }
            else -> {
                time -= 2.625f / 2.75f
                c * (7.5625f * time * time + 0.984375f) + b
            }
        }
    }

    private fun getEaseInOutBounceOffset(offset: Float): Float {
        val b = 0f
        val c = 1f
        val d = 1f
        return if (offset < d / 2) {
            getEaseBounceOffsetHelper1(offset * 2, 0f, c, d) * 0.5f + b
        } else {
            getEaseBounceOffsetHelper2(offset * 2, 0f, c, d) * 0.5f + c * 0.5f + b
        }
    }

    private fun getEaseInElasticOffset(offset: Float): Float {
        val b = 0f
        val c = 1f
        val d = 1f
        if (offset == 0f) return b
        var time = offset / d
        if (time == 1f) return b + c
        val p = d * 0.3f
        val s = p / 4
        time -= 1
        return -(c * 2.0.pow((10 * time).toDouble()).toFloat() *
                sin(((time * d - s) * (2 * PI) / p).toFloat())) + b
    }

    private fun getEaseOutElasticOffset(offset: Float): Float {
        val b = 0f
        val c = 1f
        val d = 1f
        if (offset == 0f) return b
        val time = offset / d
        if (time == 1f) return b + c
        val p = d * 0.3f
        val s = p / 4
        return (c * 2.0.pow((-10 * time).toDouble()).toFloat() *
                sin(((time * d - s) * (2 * PI) / p).toFloat()) + c + b)
    }

    private fun getEaseInOutElasticOffset(offset: Float): Float {
        val b = 0f
        val c = 1f
        val d = 1f
        if (offset == 0f) return b
        var time = offset / (d / 2)
        if (time == 2f) return b + c
        val p = d * 0.45f
        val s = p / 4
        return if (time < 1) {
            time -= 1
            -0.5f * (c * 2.0.pow((10 * time).toDouble()).toFloat() *
                    sin(((time * d - s) * (2 * PI) / p).toFloat())) + b
        } else {
            time -= 1
            c * 2.0.pow((-10 * time).toDouble()).toFloat() *
                    sin(((time * d - s) * (2 * PI) / p).toFloat()) * 0.5f + c + b
        }
    }

    companion object {
        private var eases: ArrayList<Ease?>? = null

        @JvmStatic
        fun getInstance(easeEnum: EaseEnum?): Ease {
            if (easeEnum == null) return getInstance(EaseEnum.Linear)
            if (eases == null) {
                eases = ArrayList<Ease?>(EaseEnum.entries.size).apply {
                    repeat(EaseEnum.entries.size) { add(null) }
                }
            }
            var ease = eases!![easeEnum.value]
            if (ease == null) {
                ease = Ease(easeEnum)
                eases!![easeEnum.value] = ease
            }
            return ease
        }
    }
}
