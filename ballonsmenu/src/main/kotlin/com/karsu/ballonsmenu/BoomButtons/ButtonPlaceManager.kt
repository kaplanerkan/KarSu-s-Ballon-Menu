/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.BoomButtons

import android.graphics.Point
import android.graphics.PointF
import com.karsu.ballonsmenu.BoomMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import kotlin.math.pow
import kotlin.math.sqrt

object ButtonPlaceManager {

    @JvmStatic
    fun getPositions(
        parentSize: Point,
        w: Float,
        h: Float,
        buttonNumber: Int,
        bmb: BoomMenuButton
    ): ArrayList<PointF> {
        val ps = ArrayList<PointF>(buttonNumber)

        val halfButtonNumber = buttonNumber / 2

        val hm = bmb.buttonHorizontalMargin
        val hm_0_5 = hm / 2
        val hm_1_5 = hm * 1.5f
        val hm_2_0 = hm * 2
        val vm = bmb.buttonVerticalMargin
        val vm_0_5 = vm / 2
        val vm_1_5 = vm * 1.5f
        val vm_2_0 = vm * 2
        val w_0_5 = w / 2
        val w_1_5 = w * 1.5f
        val w_2_0 = w * 2
        val h_0_5 = h / 2
        val h_1_5 = h * 1.5f
        val h_2_0 = h * 2

        when (bmb.buttonPlaceEnum) {
            ButtonPlaceEnum.Horizontal -> {
                if (buttonNumber % 2 == 0) {
                    for (i in halfButtonNumber - 1 downTo 0)
                        ps.add(point(-w_0_5 - hm_0_5 - i * (w + hm), 0f))
                    for (i in 0 until halfButtonNumber)
                        ps.add(point(+w_0_5 + hm_0_5 + i * (w + hm), 0f))
                } else {
                    for (i in halfButtonNumber - 1 downTo 0)
                        ps.add(point(-w - hm - i * (w + hm), 0f))
                    ps.add(point(0f, 0f))
                    for (i in 0 until halfButtonNumber)
                        ps.add(point(+w + hm + i * (w + hm), 0f))
                }
            }
            ButtonPlaceEnum.Vertical, ButtonPlaceEnum.HAM_1, ButtonPlaceEnum.HAM_2,
            ButtonPlaceEnum.HAM_3, ButtonPlaceEnum.HAM_4, ButtonPlaceEnum.HAM_5,
            ButtonPlaceEnum.HAM_6 -> {
                if (buttonNumber % 2 == 0) {
                    for (i in halfButtonNumber - 1 downTo 0)
                        ps.add(point(0f, -h_0_5 - vm_0_5 - i * (h + vm)))
                    for (i in 0 until halfButtonNumber)
                        ps.add(point(0f, +h_0_5 + vm_0_5 + i * (h + vm)))
                } else {
                    for (i in halfButtonNumber - 1 downTo 0)
                        ps.add(point(0f, -h - vm - i * (h + vm)))
                    ps.add(point(0f, 0f))
                    for (i in 0 until halfButtonNumber)
                        ps.add(point(0f, +h + vm + i * (h + vm)))
                }
            }
            ButtonPlaceEnum.SC_1 -> ps.add(point(0f, 0f))
            ButtonPlaceEnum.SC_2_1 -> {
                ps.add(point(-hm_0_5 - w_0_5, 0f))
                ps.add(point(+hm_0_5 + w_0_5, 0f))
            }
            ButtonPlaceEnum.SC_2_2 -> {
                ps.add(point(0f, -vm_0_5 - h_0_5))
                ps.add(point(0f, +vm_0_5 + h_0_5))
            }
            ButtonPlaceEnum.SC_3_1 -> {
                ps.add(point(-hm - w, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm + w, 0f))
            }
            ButtonPlaceEnum.SC_3_2 -> {
                ps.add(point(0f, -vm - h))
                ps.add(point(0f, 0f))
                ps.add(point(0f, +vm + h))
            }
            ButtonPlaceEnum.SC_3_3 -> {
                ps.add(point(-hm_0_5 - w_0_5, -vm_0_5 - h_0_5))
                ps.add(point(+hm_0_5 + w_0_5, -vm_0_5 - h_0_5))
                ps.add(point(0f, vm_0_5 + h_0_5))
            }
            ButtonPlaceEnum.SC_3_4 -> {
                ps.add(point(0f, -vm_0_5 - h_0_5))
                ps.add(point(-hm_0_5 - w_0_5, +vm_0_5 + h_0_5))
                ps.add(point(+hm_0_5 + w_0_5, +vm_0_5 + h_0_5))
            }
            ButtonPlaceEnum.SC_4_1 -> {
                ps.add(point(-hm_0_5 - w_0_5, -vm_0_5 - h_0_5))
                ps.add(point(+hm_0_5 + w_0_5, -vm_0_5 - h_0_5))
                ps.add(point(-hm_0_5 - w_0_5, +vm_0_5 + h_0_5))
                ps.add(point(+hm_0_5 + w_0_5, +vm_0_5 + h_0_5))
            }
            ButtonPlaceEnum.SC_4_2 -> {
                ps.add(point(0f, -vm_0_5 - h_0_5))
                ps.add(point(-hm - w, 0f))
                ps.add(point(+hm + w, 0f))
                ps.add(point(0f, +vm_0_5 + h_0_5))
            }
            ButtonPlaceEnum.SC_5_1 -> {
                ps.add(point(-hm - w, -vm_0_5 - h_0_5))
                ps.add(point(0f, -vm_0_5 - h_0_5))
                ps.add(point(+hm + w, -vm_0_5 - h_0_5))
                ps.add(point(-hm_0_5 - w_0_5, +vm_0_5 + h_0_5))
                ps.add(point(+hm_0_5 + w_0_5, +vm_0_5 + h_0_5))
            }
            ButtonPlaceEnum.SC_5_2 -> {
                ps.add(point(-hm_0_5 - w_0_5, -vm_0_5 - h_0_5))
                ps.add(point(+hm_0_5 + w_0_5, -vm_0_5 - h_0_5))
                ps.add(point(-hm - w, +vm_0_5 + h_0_5))
                ps.add(point(0f, +vm_0_5 + h_0_5))
                ps.add(point(+hm + w, +vm_0_5 + h_0_5))
            }
            ButtonPlaceEnum.SC_5_3 -> {
                ps.add(point(0f, -vm - h))
                ps.add(point(-hm - w, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm + w, 0f))
                ps.add(point(0f, +vm + h))
            }
            ButtonPlaceEnum.SC_5_4 -> {
                ps.add(point(-hm - w, -vm_0_5 - h_0_5))
                ps.add(point(+hm + w, -vm_0_5 - h_0_5))
                ps.add(point(0f, 0f))
                ps.add(point(-hm - w, +vm_0_5 + h_0_5))
                ps.add(point(+hm + w, +vm_0_5 + h_0_5))
            }
            ButtonPlaceEnum.SC_6_1 -> {
                ps.add(point(-hm - w, -vm_0_5 - h_0_5))
                ps.add(point(0f, -vm_0_5 - h_0_5))
                ps.add(point(+hm + w, -vm_0_5 - h_0_5))
                ps.add(point(-hm - w, +vm_0_5 + h_0_5))
                ps.add(point(0f, +vm_0_5 + h_0_5))
                ps.add(point(+hm + w, +vm_0_5 + h_0_5))
            }
            ButtonPlaceEnum.SC_6_2 -> {
                ps.add(point(-hm_0_5 - w_0_5, -vm - h))
                ps.add(point(+hm_0_5 + w_0_5, -vm - h))
                ps.add(point(-hm_0_5 - w_0_5, 0f))
                ps.add(point(+hm_0_5 + w_0_5, 0f))
                ps.add(point(-hm_0_5 - w_0_5, +vm + h))
                ps.add(point(+hm_0_5 + w_0_5, +vm + h))
            }
            ButtonPlaceEnum.SC_6_3 -> {
                ps.add(point(-hm_0_5 - w_0_5, -vm - h))
                ps.add(point(+hm_0_5 + w_0_5, -vm - h))
                ps.add(point(-hm - w, 0f))
                ps.add(point(+hm + w, 0f))
                ps.add(point(-hm_0_5 - w_0_5, +vm + h))
                ps.add(point(+hm_0_5 + w_0_5, +vm + h))
            }
            ButtonPlaceEnum.SC_6_4 -> {
                ps.add(point(0f, -vm - h))
                ps.add(point(-hm - w, -vm_0_5 - h_0_5))
                ps.add(point(+hm + w, -vm_0_5 - h_0_5))
                ps.add(point(-hm - w, +vm_0_5 + h_0_5))
                ps.add(point(+hm + w, +vm_0_5 + h_0_5))
                ps.add(point(0f, +vm + h))
            }
            ButtonPlaceEnum.SC_6_5 -> {
                ps.add(point(-hm - w, -vm - h))
                ps.add(point(0f, -vm - h))
                ps.add(point(+hm + w, -vm - h))
                ps.add(point(-hm_0_5 - w_0_5, 0f))
                ps.add(point(+hm_0_5 + w_0_5, 0f))
                ps.add(point(0f, vm + h))
            }
            ButtonPlaceEnum.SC_6_6 -> {
                ps.add(point(0f, -vm - h))
                ps.add(point(-hm_0_5 - w_0_5, 0f))
                ps.add(point(+hm_0_5 + w_0_5, 0f))
                ps.add(point(-hm - w, vm + h))
                ps.add(point(0f, vm + h))
                ps.add(point(+hm + w, vm + h))
            }
            ButtonPlaceEnum.SC_7_1 -> {
                ps.add(point(-hm - w, -vm - h))
                ps.add(point(0f, -vm - h))
                ps.add(point(+hm + w, -vm - h))
                ps.add(point(-hm - w, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm + w, 0f))
                ps.add(point(0f, vm + h))
            }
            ButtonPlaceEnum.SC_7_2 -> {
                ps.add(point(-hm - w, -vm - h))
                ps.add(point(0f, -vm - h))
                ps.add(point(+hm + w, -vm - h))
                ps.add(point(-hm - w, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm + w, 0f))
                ps.add(point(0f, vm + h))
            }
            ButtonPlaceEnum.SC_7_3 -> {
                ps.add(point(-hm_0_5 - w_0_5, -vm - h))
                ps.add(point(+hm_0_5 + w_0_5, -vm - h))
                ps.add(point(-hm - w, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm + w, 0f))
                ps.add(point(-hm_0_5 - w_0_5, +vm + h))
                ps.add(point(+hm_0_5 + w_0_5, +vm + h))
            }
            ButtonPlaceEnum.SC_7_4 -> {
                ps.add(point(0f, -vm - h))
                ps.add(point(-hm - w, -vm_0_5 - h_0_5))
                ps.add(point(+hm + w, -vm_0_5 - h_0_5))
                ps.add(point(0f, 0f))
                ps.add(point(-hm - w, +vm_0_5 + h_0_5))
                ps.add(point(+hm + w, +vm_0_5 + h_0_5))
                ps.add(point(0f, +vm + h))
            }
            ButtonPlaceEnum.SC_7_5 -> {
                ps.add(point(-hm_1_5 - w_1_5, -vm_0_5 - h_0_5))
                ps.add(point(-hm_0_5 - w_0_5, -vm_0_5 - h_0_5))
                ps.add(point(+hm_0_5 + w_0_5, -vm_0_5 - h_0_5))
                ps.add(point(+hm_1_5 + w_1_5, -vm_0_5 - h_0_5))
                ps.add(point(-hm - w, vm_0_5 + h_0_5))
                ps.add(point(0f, vm_0_5 + h_0_5))
                ps.add(point(+hm + w, vm_0_5 + h_0_5))
            }
            ButtonPlaceEnum.SC_7_6 -> {
                ps.add(point(-hm - w, -vm_0_5 - h_0_5))
                ps.add(point(0f, -vm_0_5 - h_0_5))
                ps.add(point(+hm + w, -vm_0_5 - h_0_5))
                ps.add(point(-hm_1_5 - w_1_5, +vm_0_5 + h_0_5))
                ps.add(point(-hm_0_5 - w_0_5, +vm_0_5 + h_0_5))
                ps.add(point(+hm_0_5 + w_0_5, +vm_0_5 + h_0_5))
                ps.add(point(+hm_1_5 + w_1_5, +vm_0_5 + h_0_5))
            }
            ButtonPlaceEnum.SC_8_1 -> {
                ps.add(point(-hm - w, -vm - h))
                ps.add(point(0f, -vm - h))
                ps.add(point(+hm + w, -vm - h))
                ps.add(point(-hm_0_5 - w_0_5, 0f))
                ps.add(point(+hm_0_5 + w_0_5, 0f))
                ps.add(point(-hm - w, +vm + h))
                ps.add(point(0f, +vm + h))
                ps.add(point(+hm + w, +vm + h))
            }
            ButtonPlaceEnum.SC_8_2 -> {
                ps.add(point(-hm - w, -vm - h))
                ps.add(point(+hm + w, -vm - h))
                ps.add(point(0f, -vm_0_5 - h_0_5))
                ps.add(point(-hm - w, 0f))
                ps.add(point(+hm + w, 0f))
                ps.add(point(0f, +vm_0_5 + h_0_5))
                ps.add(point(-hm - w, +vm + h))
                ps.add(point(+hm + w, +vm + h))
            }
            ButtonPlaceEnum.SC_8_3 -> {
                ps.add(point(-hm - w, -vm - h))
                ps.add(point(0f, -vm - h))
                ps.add(point(+hm + w, -vm - h))
                ps.add(point(-hm - w, 0f))
                ps.add(point(+hm + w, 0f))
                ps.add(point(-hm - w, +vm + h))
                ps.add(point(0f, +vm + h))
                ps.add(point(+hm + w, +vm + h))
            }
            ButtonPlaceEnum.SC_8_4 -> {
                ps.add(point(0f, -vm_2_0 - h_2_0))
                ps.add(point(-hm_0_5 - w_0_5, -vm - h))
                ps.add(point(+hm_0_5 + w_0_5, -vm - h))
                ps.add(point(-hm - w, 0f))
                ps.add(point(+hm + w, 0f))
                ps.add(point(-hm_0_5 - w_0_5, +vm + h))
                ps.add(point(+hm_0_5 + w_0_5, +vm + h))
                ps.add(point(0f, +vm_2_0 + h_2_0))
            }
            ButtonPlaceEnum.SC_8_5 -> {
                ps.add(point(0f, -vm - h))
                ps.add(point(-hm - w, -vm_0_5 - h_0_5))
                ps.add(point(+hm + w, -vm_0_5 - h_0_5))
                ps.add(point(-hm_2_0 - w_2_0, 0f))
                ps.add(point(+hm_2_0 + w_2_0, 0f))
                ps.add(point(-hm - w, +vm_0_5 + h_0_5))
                ps.add(point(+hm + w, +vm_0_5 + h_0_5))
                ps.add(point(0f, +vm + h))
            }
            ButtonPlaceEnum.SC_8_6 -> {
                ps.add(point(-hm_1_5 - w_1_5, -vm_0_5 - h_0_5))
                ps.add(point(-hm_0_5 - w_0_5, -vm_0_5 - h_0_5))
                ps.add(point(+hm_0_5 + w_0_5, -vm_0_5 - h_0_5))
                ps.add(point(+hm_1_5 + w_1_5, -vm_0_5 - h_0_5))
                ps.add(point(-hm_1_5 - w_1_5, +vm_0_5 + h_0_5))
                ps.add(point(-hm_0_5 - w_0_5, +vm_0_5 + h_0_5))
                ps.add(point(+hm_0_5 + w_0_5, +vm_0_5 + h_0_5))
                ps.add(point(+hm_1_5 + w_1_5, +vm_0_5 + h_0_5))
            }
            ButtonPlaceEnum.SC_8_7 -> {
                ps.add(point(-hm_0_5 - w_0_5, -vm_1_5 - h_1_5))
                ps.add(point(+hm_0_5 + w_0_5, -vm_1_5 - h_1_5))
                ps.add(point(-hm_0_5 - w_0_5, -vm_0_5 - h_0_5))
                ps.add(point(+hm_0_5 + w_0_5, -vm_0_5 - h_0_5))
                ps.add(point(-hm_0_5 - w_0_5, +vm_0_5 + h_0_5))
                ps.add(point(+hm_0_5 + w_0_5, +vm_0_5 + h_0_5))
                ps.add(point(-hm_0_5 - w_0_5, +vm_1_5 + h_1_5))
                ps.add(point(+hm_0_5 + w_0_5, +vm_1_5 + h_1_5))
            }
            ButtonPlaceEnum.SC_9_1 -> {
                ps.add(point(-hm - w, -vm - h))
                ps.add(point(0f, -vm - h))
                ps.add(point(+hm + w, -vm - h))
                ps.add(point(-hm - w, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm + w, 0f))
                ps.add(point(-hm - w, +vm + h))
                ps.add(point(0f, +vm + h))
                ps.add(point(+hm + w, +vm + h))
            }
            ButtonPlaceEnum.SC_9_2 -> {
                ps.add(point(0f, -vm_2_0 - h_2_0))
                ps.add(point(-hm_0_5 - w_0_5, -vm - h))
                ps.add(point(+hm_0_5 + w_0_5, -vm - h))
                ps.add(point(-hm - w, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm + w, 0f))
                ps.add(point(-hm_0_5 - w_0_5, +vm + h))
                ps.add(point(+hm_0_5 + w_0_5, +vm + h))
                ps.add(point(0f, +vm_2_0 + h_2_0))
            }
            ButtonPlaceEnum.SC_9_3 -> {
                ps.add(point(0f, -vm - h))
                ps.add(point(-hm - w, -vm_0_5 - h_0_5))
                ps.add(point(+hm + w, -vm_0_5 - h_0_5))
                ps.add(point(-hm_2_0 - w_2_0, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm_2_0 + w_2_0, 0f))
                ps.add(point(-hm - w, +vm_0_5 + h_0_5))
                ps.add(point(+hm + w, +vm_0_5 + h_0_5))
                ps.add(point(0f, +vm + h))
            }
            ButtonPlaceEnum.Custom -> {
                for (p in bmb.customButtonPlacePositions) ps.add(point(p.x, p.y))
            }
            else -> throw RuntimeException("Button place enum not found!")
        }

        when (bmb.buttonPlaceEnum) {
            ButtonPlaceEnum.SC_3_3 -> adjust(ps, 0f, ((hm_0_5 + w_0_5).toDouble().pow(2.0) / (vm + h)).toFloat())
            ButtonPlaceEnum.SC_3_4 -> adjust(ps, 0f, (-(hm_0_5 + w_0_5).toDouble().pow(2.0) / (vm + h)).toFloat())
            ButtonPlaceEnum.SC_4_2, ButtonPlaceEnum.SC_5_1, ButtonPlaceEnum.SC_5_2,
            ButtonPlaceEnum.SC_5_3, ButtonPlaceEnum.SC_5_4, ButtonPlaceEnum.SC_6_1,
            ButtonPlaceEnum.SC_6_2, ButtonPlaceEnum.SC_6_3, ButtonPlaceEnum.SC_6_4,
            ButtonPlaceEnum.SC_6_5, ButtonPlaceEnum.SC_6_6, ButtonPlaceEnum.SC_7_1,
            ButtonPlaceEnum.SC_7_2, ButtonPlaceEnum.SC_7_3, ButtonPlaceEnum.SC_7_4,
            ButtonPlaceEnum.SC_7_5, ButtonPlaceEnum.SC_7_6, ButtonPlaceEnum.SC_8_1,
            ButtonPlaceEnum.SC_8_2, ButtonPlaceEnum.SC_8_3, ButtonPlaceEnum.SC_8_4,
            ButtonPlaceEnum.SC_8_5, ButtonPlaceEnum.SC_8_6, ButtonPlaceEnum.SC_8_7,
            ButtonPlaceEnum.SC_9_1, ButtonPlaceEnum.SC_9_2, ButtonPlaceEnum.SC_9_3 ->
                adjust(ps, 0f, h_0_5 - w_0_5)
            else -> {
                if (buttonNumber >= 2
                    && bmb.buttonEnum == ButtonEnum.Ham
                    && bmb.bottomHamButtonTopMargin > 0
                    && bmb.buttonPlaceEnum != ButtonPlaceEnum.Horizontal
                ) {
                    ps[ps.size - 1].offset(0f, bmb.bottomHamButtonTopMargin - vm)
                }
            }
        }

        adjust(ps, parentSize.x / 2f, parentSize.y / 2f)
        adjust(ps, parentSize, w_0_5, h_0_5, bmb)

        return ps
    }

    @JvmStatic
    fun getPositions(
        parentSize: Point,
        r: Float,
        buttonNumber: Int,
        bmb: BoomMenuButton
    ): ArrayList<PointF> {
        val ps = ArrayList<PointF>(buttonNumber)

        val halfButtonNumber = buttonNumber / 2

        val hm = bmb.buttonHorizontalMargin
        val hm_0_5 = hm / 2
        val hm_1_5 = hm * 1.5f

        val vm = bmb.buttonVerticalMargin
        val vm_0_5 = vm / 2
        val vm_1_5 = vm * 1.5f

        val im = bmb.buttonInclinedMargin

        val r_2_0 = 2 * r
        val r_3_0 = 3 * r

        var a: Float
        var b = hm_0_5 + r
        var c = (b / (sqrt(3.0) / 2)).toFloat()
        a = c / 2
        var e = c - a

        when (bmb.buttonPlaceEnum) {
            ButtonPlaceEnum.SC_4_2, ButtonPlaceEnum.SC_5_4,
            ButtonPlaceEnum.SC_8_5, ButtonPlaceEnum.SC_9_3 -> {
                a = ((r_2_0 + im) / sqrt(2.0)).toFloat()
            }
            ButtonPlaceEnum.SC_8_2 -> {
                b = vm_0_5 + r
                c = (b / (sqrt(3.0) / 2)).toFloat()
                a = c / 2
                e = c - a
            }
            else -> { /* keep defaults */ }
        }

        val a_2_0 = 2 * a
        val b_2_0 = 2 * b
        val b_3_0 = 3 * b
        val c_2_0 = 2 * c

        when (bmb.buttonPlaceEnum) {
            ButtonPlaceEnum.Horizontal -> {
                if (buttonNumber % 2 == 0) {
                    for (i in halfButtonNumber - 1 downTo 0)
                        ps.add(point(-r - hm_0_5 - i * (r_2_0 + hm), 0f))
                    for (i in 0 until halfButtonNumber)
                        ps.add(point(+r + hm_0_5 + i * (r_2_0 + hm), 0f))
                } else {
                    for (i in halfButtonNumber - 1 downTo 0)
                        ps.add(point(-r_2_0 - hm - i * (r_2_0 + hm), 0f))
                    ps.add(point(0f, 0f))
                    for (i in 0 until halfButtonNumber)
                        ps.add(point(+r_2_0 + hm + i * (r_2_0 + hm), 0f))
                }
            }
            ButtonPlaceEnum.Vertical -> {
                if (buttonNumber % 2 == 0) {
                    for (i in halfButtonNumber - 1 downTo 0)
                        ps.add(point(0f, -r - vm_0_5 - i * (r_2_0 + vm)))
                    for (i in 0 until halfButtonNumber)
                        ps.add(point(0f, +r + vm_0_5 + i * (r_2_0 + vm)))
                } else {
                    for (i in halfButtonNumber - 1 downTo 0)
                        ps.add(point(0f, -r_2_0 - vm - i * (r_2_0 + vm)))
                    ps.add(point(0f, 0f))
                    for (i in 0 until halfButtonNumber)
                        ps.add(point(0f, +r_2_0 + vm + i * (r_2_0 + vm)))
                }
            }
            ButtonPlaceEnum.SC_1 -> ps.add(point(0f, 0f))
            ButtonPlaceEnum.SC_2_1 -> {
                ps.add(point(-hm_0_5 - r, 0f))
                ps.add(point(+hm_0_5 + r, 0f))
            }
            ButtonPlaceEnum.SC_2_2 -> {
                ps.add(point(0f, -vm_0_5 - r))
                ps.add(point(0f, +vm_0_5 + r))
            }
            ButtonPlaceEnum.SC_3_1 -> {
                ps.add(point(-hm - r_2_0, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm + r_2_0, 0f))
            }
            ButtonPlaceEnum.SC_3_2 -> {
                ps.add(point(0f, -vm - r_2_0))
                ps.add(point(0f, 0f))
                ps.add(point(0f, +vm + r_2_0))
            }
            ButtonPlaceEnum.SC_3_3 -> {
                ps.add(point(-b, -a))
                ps.add(point(+b, -a))
                ps.add(point(0f, c))
            }
            ButtonPlaceEnum.SC_3_4 -> {
                ps.add(point(0f, -c))
                ps.add(point(-b, a))
                ps.add(point(+b, a))
            }
            ButtonPlaceEnum.SC_4_1 -> {
                ps.add(point(-hm_0_5 - r, -vm_0_5 - r))
                ps.add(point(+hm_0_5 + r, -vm_0_5 - r))
                ps.add(point(-hm_0_5 - r, +vm_0_5 + r))
                ps.add(point(+hm_0_5 + r, +vm_0_5 + r))
            }
            ButtonPlaceEnum.SC_4_2 -> {
                ps.add(point(0f, -a))
                ps.add(point(-a, 0f))
                ps.add(point(+a, 0f))
                ps.add(point(0f, +a))
            }
            ButtonPlaceEnum.SC_5_1 -> {
                ps.add(point(-b_2_0, -c))
                ps.add(point(0f, -c))
                ps.add(point(+b_2_0, -c))
                ps.add(point(-hm_0_5 - r, a))
                ps.add(point(+hm_0_5 + r, a))
            }
            ButtonPlaceEnum.SC_5_2 -> {
                ps.add(point(-hm_0_5 - r, -a))
                ps.add(point(+hm_0_5 + r, -a))
                ps.add(point(-b_2_0, c))
                ps.add(point(0f, c))
                ps.add(point(+b_2_0, c))
            }
            ButtonPlaceEnum.SC_5_3 -> {
                ps.add(point(0f, -vm - r_2_0))
                ps.add(point(-hm - r_2_0, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm + r_2_0, 0f))
                ps.add(point(0f, +vm + r_2_0))
            }
            ButtonPlaceEnum.SC_5_4 -> {
                ps.add(point(-a, -a))
                ps.add(point(+a, -a))
                ps.add(point(0f, 0f))
                ps.add(point(-a, +a))
                ps.add(point(+a, +a))
            }
            ButtonPlaceEnum.SC_6_1 -> {
                ps.add(point(-hm - r_2_0, -vm_0_5 - r))
                ps.add(point(0f, -vm_0_5 - r))
                ps.add(point(+hm + r_2_0, -vm_0_5 - r))
                ps.add(point(-hm - r_2_0, +vm_0_5 + r))
                ps.add(point(0f, +vm_0_5 + r))
                ps.add(point(+hm + r_2_0, +vm_0_5 + r))
            }
            ButtonPlaceEnum.SC_6_2 -> {
                ps.add(point(-hm_0_5 - r, -vm - r_2_0))
                ps.add(point(+hm_0_5 + r, -vm - r_2_0))
                ps.add(point(-hm_0_5 - r, 0f))
                ps.add(point(+hm_0_5 + r, 0f))
                ps.add(point(-hm_0_5 - r, +vm + r_2_0))
                ps.add(point(+hm_0_5 + r, +vm + r_2_0))
            }
            ButtonPlaceEnum.SC_6_3 -> {
                ps.add(point(-b, -a - c))
                ps.add(point(+b, -a - c))
                ps.add(point(-b_2_0, 0f))
                ps.add(point(+b_2_0, 0f))
                ps.add(point(-b, +a + c))
                ps.add(point(+b, +a + c))
            }
            ButtonPlaceEnum.SC_6_4 -> {
                ps.add(point(0f, -b_2_0))
                ps.add(point(-a - c, -b))
                ps.add(point(+a + c, -b))
                ps.add(point(-a - c, +b))
                ps.add(point(+a + c, +b))
                ps.add(point(0f, +b_2_0))
            }
            ButtonPlaceEnum.SC_6_5 -> {
                ps.add(point(-b_2_0, -a - c + e))
                ps.add(point(0f, -a - c + e))
                ps.add(point(+b_2_0, -a - c + e))
                ps.add(point(-hm_0_5 - r, +e))
                ps.add(point(+hm_0_5 + r, +e))
                ps.add(point(0f, +a + c + e))
            }
            ButtonPlaceEnum.SC_6_6 -> {
                ps.add(point(0f, -a - c - e))
                ps.add(point(-hm_0_5 - r, -e))
                ps.add(point(+hm_0_5 + r, -e))
                ps.add(point(-b_2_0, +a + c - e))
                ps.add(point(0f, +a + c - e))
                ps.add(point(+b_2_0, +a + c - e))
            }
            ButtonPlaceEnum.SC_7_1 -> {
                ps.add(point(-hm - r_2_0, -vm - r_2_0))
                ps.add(point(0f, -vm - r_2_0))
                ps.add(point(+hm + r_2_0, -vm - r_2_0))
                ps.add(point(-hm - r_2_0, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm + r_2_0, 0f))
                ps.add(point(0f, vm + r_2_0))
            }
            ButtonPlaceEnum.SC_7_2 -> {
                ps.add(point(0f, -vm - r_2_0))
                ps.add(point(-hm - r_2_0, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm + r_2_0, 0f))
                ps.add(point(-hm - r_2_0, vm + r_2_0))
                ps.add(point(0f, vm + r_2_0))
                ps.add(point(+hm + r_2_0, vm + r_2_0))
            }
            ButtonPlaceEnum.SC_7_3 -> {
                ps.add(point(-b, -a - c))
                ps.add(point(+b, -a - c))
                ps.add(point(-b_2_0, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+b_2_0, 0f))
                ps.add(point(-b, +a + c))
                ps.add(point(+b, +a + c))
            }
            ButtonPlaceEnum.SC_7_4 -> {
                ps.add(point(0f, -b_2_0))
                ps.add(point(-a - c, -b))
                ps.add(point(+a + c, -b))
                ps.add(point(0f, 0f))
                ps.add(point(-a - c, +b))
                ps.add(point(+a + c, +b))
                ps.add(point(0f, +b_2_0))
            }
            ButtonPlaceEnum.SC_7_5 -> {
                ps.add(point(-b_3_0, -a))
                ps.add(point(-b, -a))
                ps.add(point(+b, -a))
                ps.add(point(+b_3_0, -a))
                ps.add(point(-b_2_0, c))
                ps.add(point(0f, c))
                ps.add(point(+b_2_0, c))
            }
            ButtonPlaceEnum.SC_7_6 -> {
                ps.add(point(-b_2_0, -c))
                ps.add(point(0f, -c))
                ps.add(point(+b_2_0, -c))
                ps.add(point(-b_3_0, a))
                ps.add(point(-b, a))
                ps.add(point(+b, a))
                ps.add(point(+b_3_0, a))
            }
            ButtonPlaceEnum.SC_8_1 -> {
                ps.add(point(-b_2_0, -a - c))
                ps.add(point(0f, -a - c))
                ps.add(point(+b_2_0, -a - c))
                ps.add(point(-hm_0_5 - r, 0f))
                ps.add(point(+hm_0_5 + r, 0f))
                ps.add(point(-b_2_0, +a + c))
                ps.add(point(0f, +a + c))
                ps.add(point(+b_2_0, +a + c))
            }
            ButtonPlaceEnum.SC_8_2 -> {
                ps.add(point(-a - c, -b_2_0))
                ps.add(point(+a + c, -b_2_0))
                ps.add(point(0f, -vm_0_5 - r))
                ps.add(point(-a - c, 0f))
                ps.add(point(+a + c, 0f))
                ps.add(point(0f, +vm_0_5 + r))
                ps.add(point(-a - c, +b_2_0))
                ps.add(point(+a + c, +b_2_0))
            }
            ButtonPlaceEnum.SC_8_3 -> {
                ps.add(point(-hm - r_2_0, -vm - r_2_0))
                ps.add(point(0f, -vm - r_2_0))
                ps.add(point(+hm + r_2_0, -vm - r_2_0))
                ps.add(point(-hm - r_2_0, 0f))
                ps.add(point(+hm + r_2_0, 0f))
                ps.add(point(-hm - r_2_0, +vm + r_2_0))
                ps.add(point(0f, +vm + r_2_0))
                ps.add(point(+hm + r_2_0, +vm + r_2_0))
            }
            ButtonPlaceEnum.SC_8_4 -> {
                ps.add(point(0f, -a_2_0 - c_2_0))
                ps.add(point(-hm_0_5 - r, -a - c))
                ps.add(point(+hm_0_5 + r, -a - c))
                ps.add(point(-b_2_0, 0f))
                ps.add(point(+b_2_0, 0f))
                ps.add(point(-hm_0_5 - r, +a + c))
                ps.add(point(+hm_0_5 + r, +a + c))
                ps.add(point(0f, +a_2_0 + c_2_0))
            }
            ButtonPlaceEnum.SC_8_5 -> {
                ps.add(point(0f, -a_2_0))
                ps.add(point(-a, -a))
                ps.add(point(+a, -a))
                ps.add(point(-a_2_0, 0f))
                ps.add(point(+a_2_0, 0f))
                ps.add(point(-a, +a))
                ps.add(point(+a, +a))
                ps.add(point(0f, +a_2_0))
            }
            ButtonPlaceEnum.SC_8_6 -> {
                ps.add(point(-hm_1_5 - r_3_0, -vm_0_5 - r))
                ps.add(point(-hm_0_5 - r, -vm_0_5 - r))
                ps.add(point(+hm_0_5 + r, -vm_0_5 - r))
                ps.add(point(+hm_1_5 + r_3_0, -vm_0_5 - r))
                ps.add(point(-hm_1_5 - r_3_0, +vm_0_5 + r))
                ps.add(point(-hm_0_5 - r, +vm_0_5 + r))
                ps.add(point(+hm_0_5 + r, +vm_0_5 + r))
                ps.add(point(+hm_1_5 + r_3_0, +vm_0_5 + r))
            }
            ButtonPlaceEnum.SC_8_7 -> {
                ps.add(point(-hm_0_5 - r, -vm_1_5 - r_3_0))
                ps.add(point(+hm_0_5 + r, -vm_1_5 - r_3_0))
                ps.add(point(-hm_0_5 - r, -vm_0_5 - r))
                ps.add(point(+hm_0_5 + r, -vm_0_5 - r))
                ps.add(point(-hm_0_5 - r, +vm_0_5 + r))
                ps.add(point(+hm_0_5 + r, +vm_0_5 + r))
                ps.add(point(-hm_0_5 - r, +vm_1_5 + r_3_0))
                ps.add(point(+hm_0_5 + r, +vm_1_5 + r_3_0))
            }
            ButtonPlaceEnum.SC_9_1 -> {
                ps.add(point(-hm - r_2_0, -vm - r_2_0))
                ps.add(point(0f, -vm - r_2_0))
                ps.add(point(+hm + r_2_0, -vm - r_2_0))
                ps.add(point(-hm - r_2_0, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+hm + r_2_0, 0f))
                ps.add(point(-hm - r_2_0, +vm + r_2_0))
                ps.add(point(0f, +vm + r_2_0))
                ps.add(point(+hm + r_2_0, +vm + r_2_0))
            }
            ButtonPlaceEnum.SC_9_2 -> {
                ps.add(point(0f, -a_2_0 - c_2_0))
                ps.add(point(-hm_0_5 - r, -a - c))
                ps.add(point(+hm_0_5 + r, -a - c))
                ps.add(point(-b_2_0, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+b_2_0, 0f))
                ps.add(point(-hm_0_5 - r, +a + c))
                ps.add(point(+hm_0_5 + r, +a + c))
                ps.add(point(0f, +a_2_0 + c_2_0))
            }
            ButtonPlaceEnum.SC_9_3 -> {
                ps.add(point(0f, -a_2_0))
                ps.add(point(-a, -a))
                ps.add(point(+a, -a))
                ps.add(point(-a_2_0, 0f))
                ps.add(point(0f, 0f))
                ps.add(point(+a_2_0, 0f))
                ps.add(point(-a, +a))
                ps.add(point(+a, +a))
                ps.add(point(0f, +a_2_0))
            }
            ButtonPlaceEnum.Custom -> {
                for (p in bmb.customButtonPlacePositions) ps.add(point(p.x, p.y))
            }
            else -> throw RuntimeException("Button place enum not found!")
        }

        adjust(ps, parentSize.x / 2f, parentSize.y / 2f)
        adjust(ps, parentSize, r, r, bmb)

        return ps
    }

    private fun adjust(
        ps: ArrayList<PointF>,
        parentSize: Point,
        halfWidth: Float,
        halfHeight: Float,
        bmb: BoomMenuButton
    ) {
        var minY = Float.MAX_VALUE
        var maxY = Float.MIN_VALUE
        var minX = Float.MAX_VALUE
        var maxX = Float.MIN_VALUE

        for (position in ps) {
            maxY = maxOf(maxY, position.y)
            minY = minOf(minY, position.y)
            maxX = maxOf(maxX, position.x)
            minX = minOf(minX, position.x)
        }

        var yOffset = 0f
        var xOffset = 0f
        when (bmb.buttonPlaceAlignmentEnum) {
            ButtonPlaceAlignmentEnum.Center -> { /* no adjustment */ }
            ButtonPlaceAlignmentEnum.Top -> {
                yOffset = halfHeight + bmb.buttonTopMargin - minY
            }
            ButtonPlaceAlignmentEnum.Bottom -> {
                yOffset = parentSize.y - halfHeight - maxY - bmb.buttonBottomMargin
            }
            ButtonPlaceAlignmentEnum.Left -> {
                xOffset = halfWidth + bmb.buttonLeftMargin - minY
            }
            ButtonPlaceAlignmentEnum.Right -> {
                xOffset = parentSize.y - halfHeight - maxY - bmb.buttonRightMargin
            }
            ButtonPlaceAlignmentEnum.TL -> {
                yOffset = halfHeight + bmb.buttonTopMargin - minY
                xOffset = halfWidth + bmb.buttonLeftMargin - minY
            }
            ButtonPlaceAlignmentEnum.TR -> {
                yOffset = halfHeight + bmb.buttonTopMargin - minY
                xOffset = parentSize.y - halfHeight - maxY - bmb.buttonRightMargin
            }
            ButtonPlaceAlignmentEnum.BL -> {
                yOffset = parentSize.y - halfHeight - maxY - bmb.buttonBottomMargin
                xOffset = halfWidth + bmb.buttonLeftMargin - minY
            }
            ButtonPlaceAlignmentEnum.BR -> {
                yOffset = parentSize.y - halfHeight - maxY - bmb.buttonBottomMargin
                xOffset = parentSize.y - halfHeight - maxY - bmb.buttonRightMargin
            }
            ButtonPlaceAlignmentEnum.Unknown -> { /* no adjustment */ }
        }

        adjust(ps, xOffset, yOffset)
    }

    private fun adjust(ps: ArrayList<PointF>, offsetX: Float, offsetY: Float) {
        for (p in ps) p.offset(offsetX, offsetY)
    }

    private fun point(x: Float, y: Float): PointF = PointF(x, y)
}
