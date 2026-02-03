/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.Animation

import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.animation.TypeEvaluator
import android.graphics.PointF
import android.view.View
import com.karsu.ballonsmenu.BoomButtons.BoomButton
import com.karsu.ballonsmenu.ButtonEnum
import java.util.Random
import kotlin.math.abs

object AnimationManager {

    @JvmStatic
    @JvmOverloads
    fun animate(
        target: Any?,
        property: String,
        delay: Long,
        duration: Long,
        interpolator: TimeInterpolator?,
        listenerAdapter: AnimatorListenerAdapter? = null,
        vararg values: Float
    ): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(target, property, *values)
        animator.startDelay = delay
        animator.duration = duration
        interpolator?.let { animator.interpolator = it }
        listenerAdapter?.let { animator.addListener(it) }
        animator.start()
        return animator
    }

    @JvmStatic
    fun animate(
        property: String,
        delay: Long,
        duration: Long,
        values: FloatArray,
        interpolator: TimeInterpolator?,
        targets: ArrayList<View>
    ) {
        for (target in targets) {
            animate(target, property, delay, duration, interpolator, null, *values)
        }
    }

    @JvmStatic
    fun rotate(
        boomButton: BoomButton,
        delay: Long,
        duration: Long,
        interpolator: TimeInterpolator?,
        vararg degrees: Int
    ) {
        boomButton.setRotateAnchorPoints()
        val floatDegrees = degrees.map { it.toFloat() }.toFloatArray()
        for (view in boomButton.rotateViews()) {
            animate(view, "rotation", delay, duration, interpolator, null, *floatDegrees)
        }
    }

    @JvmStatic
    @JvmOverloads
    fun animate(
        target: Any?,
        property: String,
        delay: Long,
        duration: Long,
        evaluator: TypeEvaluator<*>,
        listenerAdapter: AnimatorListenerAdapter? = null,
        vararg values: Int
    ): ObjectAnimator {
        val animator = ObjectAnimator.ofInt(target, property, *values)
        animator.startDelay = delay
        animator.duration = duration
        animator.setEvaluator(evaluator)
        listenerAdapter?.let { animator.addListener(it) }
        animator.start()
        return animator
    }

    @JvmStatic
    fun getOrderIndex(orderEnum: OrderEnum?, size: Int): ArrayList<Int> {
        val indexes = ArrayList<Int>()
        when (orderEnum) {
            OrderEnum.DEFAULT -> {
                for (i in 0 until size) indexes.add(i)
            }
            OrderEnum.REVERSE -> {
                for (i in 0 until size) indexes.add(size - i - 1)
            }
            OrderEnum.RANDOM -> {
                val used = BooleanArray(size) { false }
                var count = 0
                val random = Random()
                while (count < size) {
                    val r = random.nextInt(size)
                    if (!used[r]) {
                        used[r] = true
                        indexes.add(r)
                        count++
                    }
                }
            }
            OrderEnum.Unknown, null -> {
                for (i in 0 until size) indexes.add(i)
            }
        }
        return indexes
    }

    @JvmStatic
    fun calculateShowXY(
        boomEnum: BoomEnum?,
        parentSize: PointF,
        ease: Ease,
        frames: Int,
        startPosition: PointF,
        endPosition: PointF?,
        xs: FloatArray,
        ys: FloatArray
    ) {
        val effectiveEndPosition = endPosition ?: PointF(0f, 0f)
        var effectiveBoomEnum = boomEnum ?: BoomEnum.LINE
        if (abs(startPosition.x - effectiveEndPosition.x) < 1) effectiveBoomEnum = BoomEnum.LINE

        var x1 = startPosition.x
        var y1 = startPosition.y
        var x2 = effectiveEndPosition.x
        var y2 = effectiveEndPosition.y
        val p = 1.0f / frames
        var xOffset = x2 - x1
        val yOffset = y2 - y1
        var offset = 0f

        when (effectiveBoomEnum) {
            BoomEnum.LINE -> {
                for (i in 0..frames) {
                    val offsetInFact = ease.getInterpolation(offset)
                    xs[i] = x1 + offsetInFact * xOffset
                    ys[i] = y1 + offsetInFact * yOffset
                    offset += p
                }
            }
            BoomEnum.PARABOLA_1 -> {
                val x3 = (x1 + x2) / 2.0f
                val y3 = minOf(y1, y2) * 3.0f / 4
                val a = (y1 * (x2 - x3) + y2 * (x3 - x1) + y3 * (x1 - x2)) /
                        (x1 * x1 * (x2 - x3) + x2 * x2 * (x3 - x1) + x3 * x3 * (x1 - x2))
                val b = (y1 - y2) / (x1 - x2) - a * (x1 + x2)
                val c = y1 - (x1 * x1) * a - x1 * b
                for (i in 0..frames) {
                    val x = x1 + ease.getInterpolation(offset) * xOffset
                    xs[i] = x
                    ys[i] = a * x * x + b * x + c
                    offset += p
                }
            }
            BoomEnum.PARABOLA_2 -> {
                val x3 = (x1 + x2) / 2.0f
                val y3 = (parentSize.y + maxOf(y1, y2)) / 2.0f
                val a = (y1 * (x2 - x3) + y2 * (x3 - x1) + y3 * (x1 - x2)) /
                        (x1 * x1 * (x2 - x3) + x2 * x2 * (x3 - x1) + x3 * x3 * (x1 - x2))
                val b = (y1 - y2) / (x1 - x2) - a * (x1 + x2)
                val c = y1 - (x1 * x1) * a - x1 * b
                for (i in 0..frames) {
                    val x = x1 + ease.getInterpolation(offset) * xOffset
                    xs[i] = x
                    ys[i] = a * x * x + b * x + c
                    offset += p
                }
            }
            BoomEnum.PARABOLA_3 -> {
                val y3 = (y1 + y2) / 2.0f
                val x3 = minOf(x1, x2) / 2.0f
                val a = (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) /
                        (y1 * y1 * (y2 - y3) + y2 * y2 * (y3 - y1) + y3 * y3 * (y1 - y2))
                val b = (x1 - x2) / (y1 - y2) - a * (y1 + y2)
                val c = x1 - (y1 * y1) * a - y1 * b
                for (i in 0..frames) {
                    val y = y1 + ease.getInterpolation(offset) * yOffset
                    ys[i] = y
                    xs[i] = a * y * y + b * y + c
                    offset += p
                }
            }
            BoomEnum.PARABOLA_4 -> {
                val y3 = (y1 + y2) / 2.0f
                val x3 = (parentSize.x + maxOf(x1, x2)) / 2.0f
                val a = (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) /
                        (y1 * y1 * (y2 - y3) + y2 * y2 * (y3 - y1) + y3 * y3 * (y1 - y2))
                val b = (x1 - x2) / (y1 - y2) - a * (y1 + y2)
                val c = x1 - (y1 * y1) * a - y1 * b
                for (i in 0..frames) {
                    val y = y1 + ease.getInterpolation(offset) * yOffset
                    ys[i] = y
                    xs[i] = a * y * y + b * y + c
                    offset += p
                }
            }
            BoomEnum.HORIZONTAL_THROW_1 -> {
                val x3 = x2 * 2 - x1
                val y3 = y1
                val a = (y1 * (x3 - x2) + y3 * (x2 - x1) + y2 * (x1 - x3)) /
                        (x1 * x1 * (x3 - x2) + x3 * x3 * (x2 - x1) + x2 * x2 * (x1 - x3))
                val b = (y1 - y3) / (x1 - x3) - a * (x1 + x3)
                val c = y1 - (x1 * x1) * a - x1 * b
                for (i in 0..frames) {
                    val x = x1 + ease.getInterpolation(offset) * xOffset
                    xs[i] = x
                    ys[i] = a * x * x + b * x + c
                    offset += p
                }
            }
            BoomEnum.HORIZONTAL_THROW_2 -> {
                x2 = startPosition.x
                y2 = startPosition.y
                x1 = effectiveEndPosition.x
                y1 = effectiveEndPosition.y
                val x3 = x2 * 2 - x1
                val y3 = y1
                val a = (y1 * (x3 - x2) + y3 * (x2 - x1) + y2 * (x1 - x3)) /
                        (x1 * x1 * (x3 - x2) + x3 * x3 * (x2 - x1) + x2 * x2 * (x1 - x3))
                val b = (y1 - y3) / (x1 - x3) - a * (x1 + x3)
                val c = y1 - (x1 * x1) * a - x1 * b
                xOffset = x2 - x1
                for (i in 0..frames) {
                    val x = x2 + ease.getInterpolation(offset) * xOffset
                    xs[i] = x
                    ys[i] = a * x * x + b * x + c
                    offset += p
                }
            }
            BoomEnum.RANDOM -> {
                calculateShowXY(
                    BoomEnum.entries[Random().nextInt(BoomEnum.RANDOM.value)],
                    parentSize, ease, frames, startPosition, effectiveEndPosition, xs, ys
                )
            }
            BoomEnum.Unknown -> {
                throw RuntimeException("Unknown boom-enum!")
            }
        }
    }

    @JvmStatic
    fun calculateHideXY(
        boomEnum: BoomEnum?,
        parentSize: PointF,
        ease: Ease,
        frames: Int,
        startPosition: PointF?,
        endPosition: PointF,
        xs: FloatArray,
        ys: FloatArray
    ) {
        val effectiveStartPosition = startPosition ?: PointF(0f, 0f)
        var effectiveBoomEnum = boomEnum ?: BoomEnum.LINE
        if (abs(effectiveStartPosition.x - endPosition.x) < 1) effectiveBoomEnum = BoomEnum.LINE

        var x1 = effectiveStartPosition.x
        var y1 = effectiveStartPosition.y
        var x2 = endPosition.x
        var y2 = endPosition.y
        val p = 1.0f / frames
        var xOffset = x2 - x1
        var offset = 0f

        when (effectiveBoomEnum) {
            BoomEnum.LINE,
            BoomEnum.PARABOLA_1,
            BoomEnum.PARABOLA_2,
            BoomEnum.PARABOLA_3,
            BoomEnum.PARABOLA_4,
            BoomEnum.RANDOM,
            BoomEnum.Unknown -> {
                calculateShowXY(effectiveBoomEnum, parentSize, ease, frames, effectiveStartPosition, endPosition, xs, ys)
            }
            BoomEnum.HORIZONTAL_THROW_1 -> {
                x2 = effectiveStartPosition.x
                y2 = effectiveStartPosition.y
                x1 = endPosition.x
                y1 = endPosition.y
                val x3 = x2 * 2 - x1
                val y3 = y1
                val a = (y1 * (x3 - x2) + y3 * (x2 - x1) + y2 * (x1 - x3)) /
                        (x1 * x1 * (x3 - x2) + x3 * x3 * (x2 - x1) + x2 * x2 * (x1 - x3))
                val b = (y1 - y3) / (x1 - x3) - a * (x1 + x3)
                val c = y1 - (x1 * x1) * a - x1 * b
                xOffset = x2 - x1
                for (i in 0..frames) {
                    val x = x2 + ease.getInterpolation(offset) * xOffset
                    xs[i] = x
                    ys[i] = a * x * x + b * x + c
                    offset += p
                }
            }
            BoomEnum.HORIZONTAL_THROW_2 -> {
                val x3 = x2 * 2 - x1
                val y3 = y1
                val a = (y1 * (x3 - x2) + y3 * (x2 - x1) + y2 * (x1 - x3)) /
                        (x1 * x1 * (x3 - x2) + x3 * x3 * (x2 - x1) + x2 * x2 * (x1 - x3))
                val b = (y1 - y3) / (x1 - x3) - a * (x1 + x3)
                val c = y1 - (x1 * x1) * a - x1 * b
                for (i in 0..frames) {
                    val x = x1 + ease.getInterpolation(offset) * xOffset
                    xs[i] = x
                    ys[i] = a * x * x + b * x + c
                    offset += p
                }
            }
        }
    }

    @JvmStatic
    fun getRotate3DAnimation(
        xs: FloatArray,
        ys: FloatArray,
        delay: Long,
        duration: Long,
        bb: BoomButton
    ): Rotate3DAnimation {
        val animation = Rotate3DAnimation(
            bb.trueWidth() / 2f,
            bb.trueHeight() / 2f,
            getRotateXs(ys, bb.type()),
            getRotateYs(xs, bb.type())
        )
        animation.startOffset = delay
        animation.duration = duration
        return animation
    }

    private fun getRotateXs(ys: FloatArray, buttonEnum: ButtonEnum): ArrayList<Float> {
        val rotateXs = ArrayList<Float>(ys.size)
        rotateXs.add(0f)
        var previousY = 0f
        val maxDegree = (Math.PI / 4).toFloat()
        val e = if (buttonEnum == ButtonEnum.Ham) 60 else 30
        for (i in ys.indices) {
            if (i != 0) {
                val velocity = ys[i] - previousY
                rotateXs.add((clamp(-velocity / e, -maxDegree, maxDegree) * 180 / Math.PI).toFloat())
            }
            previousY = ys[i]
        }
        addBufferValues(rotateXs)
        return rotateXs
    }

    private fun getRotateYs(xs: FloatArray, buttonEnum: ButtonEnum): ArrayList<Float> {
        val rotateYs = ArrayList<Float>(xs.size)
        rotateYs.add(0f)
        var previousX = 0f
        val maxDegree = (Math.PI / 4).toFloat()
        val e = if (buttonEnum == ButtonEnum.Ham) 60 else 30
        for (i in xs.indices) {
            if (i != 0) {
                val velocity = xs[i] - previousX
                rotateYs.add((clamp(velocity / e, -maxDegree, maxDegree) * 180 / Math.PI).toFloat())
            }
            previousX = xs[i]
        }
        addBufferValues(rotateYs)
        return rotateYs
    }

    private fun clamp(v: Float, min: Float, max: Float): Float {
        return when {
            v < min -> min
            v > max -> max
            else -> v
        }
    }

    private fun addBufferValues(values: ArrayList<Float>) {
        if (values[values.size - 1] == 0f) return
        values.add(values[values.size - 1] * 0.5f)
        values.add(values[values.size - 1] * 0.5f)
        values.add(values[values.size - 1] * 0.5f)
        values.add(0f)
    }
}
