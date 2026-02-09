/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.piece

import android.content.Context
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import com.karsu.ballonsmenu.karsu_buttons.KarSuButtonBuilder
import com.karsu.ballonsmenu.KarSuMenuButton
import kotlin.math.sqrt

object PiecePlaceManager {

    @JvmStatic
    fun getDotPositions(bmb: KarSuMenuButton, parentSize: Point): ArrayList<RectF> {
        val hm = bmb.pieceHorizontalMargin
        val hm_0_5 = hm * 0.5f
        val hm_1_5 = hm * 1.5f
        val vm = bmb.pieceVerticalMargin
        val vm_0_5 = vm * 0.5f
        val vm_1_5 = vm * 1.5f
        val im = bmb.pieceInclinedMargin
        val r = bmb.dotRadius
        val r_2_0 = r * 2
        val r_3_0 = r * 3

        val positions = ArrayList<RectF>()
        val pos = ArrayList<PointF>()

        var a: Float
        var b: Float
        var c: Float
        var e: Float
        b = hm_0_5 + r
        c = (b / (sqrt(3.0) / 2)).toFloat()
        a = c / 2
        e = c - a

        when (bmb.piecePlaceEnum) {
            PiecePlaceEnum.DOT_4_2, PiecePlaceEnum.DOT_5_4,
            PiecePlaceEnum.DOT_8_5, PiecePlaceEnum.DOT_9_3 -> {
                a = ((r_2_0 + im) / sqrt(2.0)).toFloat()
            }
            PiecePlaceEnum.DOT_8_2 -> {
                b = vm_0_5 + r
                c = (b / (sqrt(3.0) / 2)).toFloat()
                a = c / 2
                e = c - a
            }
            else -> { /* use default values */ }
        }

        val a_2_0 = 2 * a
        val b_2_0 = 2 * b
        val b_3_0 = 3 * b
        val c_2_0 = 2 * c

        when (bmb.piecePlaceEnum) {
            PiecePlaceEnum.DOT_1 -> {
                pos.add(point(0f, 0f))
            }
            PiecePlaceEnum.DOT_2_1 -> {
                pos.add(point(-hm_0_5 - r, 0f))
                pos.add(point(+hm_0_5 + r, 0f))
            }
            PiecePlaceEnum.DOT_2_2 -> {
                pos.add(point(0f, -vm_0_5 - r))
                pos.add(point(0f, +vm_0_5 + r))
            }
            PiecePlaceEnum.DOT_3_1 -> {
                pos.add(point(-hm - r_2_0, 0f))
                pos.add(point(0f, 0f))
                pos.add(point(+hm + r_2_0, 0f))
            }
            PiecePlaceEnum.DOT_3_2 -> {
                pos.add(point(0f, -vm - r_2_0))
                pos.add(point(0f, 0f))
                pos.add(point(0f, +vm + r_2_0))
            }
            PiecePlaceEnum.DOT_3_3 -> {
                pos.add(point(-b, -a))
                pos.add(point(+b, -a))
                pos.add(point(0f, c))
            }
            PiecePlaceEnum.DOT_3_4 -> {
                pos.add(point(0f, -c))
                pos.add(point(-b, a))
                pos.add(point(+b, a))
            }
            PiecePlaceEnum.DOT_4_1 -> {
                pos.add(point(-hm_0_5 - r, -vm_0_5 - r))
                pos.add(point(+hm_0_5 + r, -vm_0_5 - r))
                pos.add(point(-hm_0_5 - r, +vm_0_5 + r))
                pos.add(point(+hm_0_5 + r, +vm_0_5 + r))
            }
            PiecePlaceEnum.DOT_4_2 -> {
                pos.add(point(0f, -a))
                pos.add(point(+a, 0f))
                pos.add(point(0f, +a))
                pos.add(point(-a, 0f))
            }
            PiecePlaceEnum.DOT_5_1 -> {
                pos.add(point(-b_2_0, -a))
                pos.add(point(0f, -a))
                pos.add(point(+b_2_0, -a))
                pos.add(point(-hm_0_5 - r, c))
                pos.add(point(+hm_0_5 + r, c))
            }
            PiecePlaceEnum.DOT_5_2 -> {
                pos.add(point(-hm_0_5 - r, -c))
                pos.add(point(+hm_0_5 + r, -c))
                pos.add(point(-b_2_0, a))
                pos.add(point(0f, a))
                pos.add(point(+b_2_0, a))
            }
            PiecePlaceEnum.DOT_5_3 -> {
                pos.add(point(0f, -vm - r_2_0))
                pos.add(point(-hm - r_2_0, 0f))
                pos.add(point(0f, 0f))
                pos.add(point(+hm + r_2_0, 0f))
                pos.add(point(0f, +vm + r_2_0))
            }
            PiecePlaceEnum.DOT_5_4 -> {
                pos.add(point(-a, -a))
                pos.add(point(+a, -a))
                pos.add(point(0f, 0f))
                pos.add(point(-a, +a))
                pos.add(point(+a, +a))
            }
            PiecePlaceEnum.DOT_6_1 -> {
                pos.add(point(-hm - r_2_0, -vm_0_5 - r))
                pos.add(point(0f, -vm_0_5 - r))
                pos.add(point(+hm + r_2_0, -vm_0_5 - r))
                pos.add(point(-hm - r_2_0, +vm_0_5 + r))
                pos.add(point(0f, +vm_0_5 + r))
                pos.add(point(+hm + r_2_0, +vm_0_5 + r))
            }
            PiecePlaceEnum.DOT_6_2 -> {
                pos.add(point(-hm_0_5 - r, -vm - r_2_0))
                pos.add(point(+hm_0_5 + r, -vm - r_2_0))
                pos.add(point(-hm_0_5 - r, 0f))
                pos.add(point(+hm_0_5 + r, 0f))
                pos.add(point(-hm_0_5 - r, +vm + r_2_0))
                pos.add(point(+hm_0_5 + r, +vm + r_2_0))
            }
            PiecePlaceEnum.DOT_6_3 -> {
                pos.add(point(-b, -a - c))
                pos.add(point(+b, -a - c))
                pos.add(point(-b_2_0, 0f))
                pos.add(point(+b_2_0, 0f))
                pos.add(point(-b, +a + c))
                pos.add(point(+b, +a + c))
            }
            PiecePlaceEnum.DOT_6_4 -> {
                pos.add(point(0f, -b_2_0))
                pos.add(point(-a - c, -b))
                pos.add(point(+a + c, -b))
                pos.add(point(-a - c, +b))
                pos.add(point(+a + c, +b))
                pos.add(point(0f, +b_2_0))
            }
            PiecePlaceEnum.DOT_6_5 -> {
                pos.add(point(-b_2_0, -a - c + e))
                pos.add(point(0f, -a - c + e))
                pos.add(point(+b_2_0, -a - c + e))
                pos.add(point(-hm_0_5 - r, e))
                pos.add(point(+hm_0_5 + r, e))
                pos.add(point(0f, +a + c + e))
            }
            PiecePlaceEnum.DOT_6_6 -> {
                pos.add(point(0f, -a - c - e))
                pos.add(point(-hm_0_5 - r, -e))
                pos.add(point(+hm_0_5 + r, -e))
                pos.add(point(-b_2_0, a + c - e))
                pos.add(point(0f, +a + c - e))
                pos.add(point(+b_2_0, a + c - e))
            }
            PiecePlaceEnum.DOT_7_1 -> {
                pos.add(point(-hm - r_2_0, -vm - r_2_0))
                pos.add(point(0f, -vm - r_2_0))
                pos.add(point(+hm + r_2_0, -vm - r_2_0))
                pos.add(point(-hm - r_2_0, 0f))
                pos.add(point(0f, 0f))
                pos.add(point(+hm + r_2_0, 0f))
                pos.add(point(0f, +vm + r_2_0))
            }
            PiecePlaceEnum.DOT_7_2 -> {
                pos.add(point(0f, -vm - r_2_0))
                pos.add(point(-hm - r_2_0, 0f))
                pos.add(point(0f, 0f))
                pos.add(point(+hm + r_2_0, 0f))
                pos.add(point(-hm - r_2_0, +vm + r_2_0))
                pos.add(point(0f, +vm + r_2_0))
                pos.add(point(+hm + r_2_0, +vm + r_2_0))
            }
            PiecePlaceEnum.DOT_7_3 -> {
                pos.add(point(-b, -a - c))
                pos.add(point(+b, -a - c))
                pos.add(point(-b_2_0, 0f))
                pos.add(point(0f, 0f))
                pos.add(point(+b_2_0, 0f))
                pos.add(point(-b, +a + c))
                pos.add(point(+b, +a + c))
            }
            PiecePlaceEnum.DOT_7_4 -> {
                pos.add(point(0f, -b_2_0))
                pos.add(point(-a - c, -b))
                pos.add(point(+a + c, -b))
                pos.add(point(0f, 0f))
                pos.add(point(-a - c, +b))
                pos.add(point(+a + c, +b))
                pos.add(point(0f, +b_2_0))
            }
            PiecePlaceEnum.DOT_7_5 -> {
                pos.add(point(-b_3_0, -a))
                pos.add(point(-b, -a))
                pos.add(point(+b, -a))
                pos.add(point(+b_3_0, -a))
                pos.add(point(-b_2_0, +c))
                pos.add(point(0f, c))
                pos.add(point(+b_2_0, +c))
            }
            PiecePlaceEnum.DOT_7_6 -> {
                pos.add(point(-b_2_0, -c))
                pos.add(point(0f, -c))
                pos.add(point(+b_2_0, -c))
                pos.add(point(-b_3_0, +a))
                pos.add(point(-b, a))
                pos.add(point(+b, a))
                pos.add(point(+b_3_0, +a))
            }
            PiecePlaceEnum.DOT_8_1 -> {
                pos.add(point(-b_2_0, -a - c))
                pos.add(point(0f, -a - c))
                pos.add(point(+b_2_0, -a - c))
                pos.add(point(-hm_0_5 - r, 0f))
                pos.add(point(+hm_0_5 + r, 0f))
                pos.add(point(-b_2_0, +a + c))
                pos.add(point(0f, +a + c))
                pos.add(point(+b_2_0, +a + c))
            }
            PiecePlaceEnum.DOT_8_2 -> {
                pos.add(point(-a - c, -b_2_0))
                pos.add(point(+a + c, -b_2_0))
                pos.add(point(0f, -vm_0_5 - r))
                pos.add(point(-a - c, 0f))
                pos.add(point(+a + c, 0f))
                pos.add(point(0f, +vm_0_5 + r))
                pos.add(point(-a - c, +b_2_0))
                pos.add(point(+a + c, +b_2_0))
            }
            PiecePlaceEnum.DOT_8_3 -> {
                pos.add(point(-hm - r_2_0, -vm - r_2_0))
                pos.add(point(0f, -vm - r_2_0))
                pos.add(point(+hm + r_2_0, -vm - r_2_0))
                pos.add(point(-hm - r_2_0, 0f))
                pos.add(point(+hm + r_2_0, 0f))
                pos.add(point(-hm - r_2_0, +vm + r_2_0))
                pos.add(point(0f, +vm + r_2_0))
                pos.add(point(+hm + r_2_0, +vm + r_2_0))
            }
            PiecePlaceEnum.DOT_8_4 -> {
                pos.add(point(0f, -a_2_0 - c_2_0))
                pos.add(point(-hm_0_5 - r, -a - c))
                pos.add(point(+hm_0_5 + r, -a - c))
                pos.add(point(-b_2_0, 0f))
                pos.add(point(+b_2_0, 0f))
                pos.add(point(-hm_0_5 - r, +a + c))
                pos.add(point(+hm_0_5 + r, +a + c))
                pos.add(point(0f, +a_2_0 + c_2_0))
            }
            PiecePlaceEnum.DOT_8_5 -> {
                pos.add(point(0f, -a_2_0))
                pos.add(point(-a, -a))
                pos.add(point(+a, -a))
                pos.add(point(-a_2_0, 0f))
                pos.add(point(+a_2_0, 0f))
                pos.add(point(-a, +a))
                pos.add(point(+a, +a))
                pos.add(point(0f, +a_2_0))
            }
            PiecePlaceEnum.DOT_8_6 -> {
                pos.add(point(-hm_1_5 - r_3_0, -vm_0_5 - r))
                pos.add(point(-hm_0_5 - r, -vm_0_5 - r))
                pos.add(point(+hm_0_5 + r, -vm_0_5 - r))
                pos.add(point(+hm_1_5 + r_3_0, -vm_0_5 - r))
                pos.add(point(-hm_1_5 - r_3_0, +vm_0_5 + r))
                pos.add(point(-hm_0_5 - r, +vm_0_5 + r))
                pos.add(point(+hm_0_5 + r, +vm_0_5 + r))
                pos.add(point(+hm_1_5 + r_3_0, +vm_0_5 + r))
            }
            PiecePlaceEnum.DOT_8_7 -> {
                pos.add(point(-hm_0_5 - r, -vm_1_5 - r_3_0))
                pos.add(point(+hm_0_5 + r, -vm_1_5 - r_3_0))
                pos.add(point(-hm_0_5 - r, -vm_0_5 - r))
                pos.add(point(+hm_0_5 + r, -vm_0_5 - r))
                pos.add(point(-hm_0_5 - r, +vm_0_5 + r))
                pos.add(point(+hm_0_5 + r, +vm_0_5 + r))
                pos.add(point(-hm_0_5 - r, +vm_1_5 + r_3_0))
                pos.add(point(+hm_0_5 + r, +vm_1_5 + r_3_0))
            }
            PiecePlaceEnum.DOT_9_1 -> {
                pos.add(point(-hm - r_2_0, -vm - r_2_0))
                pos.add(point(0f, -vm - r_2_0))
                pos.add(point(+hm + r_2_0, -vm - r_2_0))
                pos.add(point(-hm - r_2_0, 0f))
                pos.add(point(0f, 0f))
                pos.add(point(+hm + r_2_0, 0f))
                pos.add(point(-hm - r_2_0, +vm + r_2_0))
                pos.add(point(0f, +vm + r_2_0))
                pos.add(point(+hm + r_2_0, +vm + r_2_0))
            }
            PiecePlaceEnum.DOT_9_2 -> {
                pos.add(point(0f, -a_2_0 - c_2_0))
                pos.add(point(-hm_0_5 - r, -a - c))
                pos.add(point(+hm_0_5 + r, -a - c))
                pos.add(point(-b_2_0, 0f))
                pos.add(point(0f, 0f))
                pos.add(point(+b_2_0, 0f))
                pos.add(point(-hm_0_5 - r, +a + c))
                pos.add(point(+hm_0_5 + r, +a + c))
                pos.add(point(0f, +a_2_0 + c_2_0))
            }
            PiecePlaceEnum.DOT_9_3 -> {
                pos.add(point(0f, -a_2_0))
                pos.add(point(-a, -a))
                pos.add(point(+a, -a))
                pos.add(point(-a_2_0, 0f))
                pos.add(point(0f, 0f))
                pos.add(point(+a_2_0, 0f))
                pos.add(point(-a, +a))
                pos.add(point(+a, +a))
                pos.add(point(0f, +a_2_0))
            }
            PiecePlaceEnum.Custom -> {
                for (po in bmb.customPiecePlacePositions) {
                    pos.add(point(po.x, po.y))
                }
            }
            else -> throw RuntimeException("Unknown piece-place-enum!")
        }

        for (po in pos) {
            positions.add(
                RectF(
                    po.x + parentSize.x / 2 - r,
                    po.y + parentSize.y / 2 - r,
                    r_2_0,
                    r_2_0
                )
            )
        }

        return positions
    }

    @JvmStatic
    fun getHamPositions(bmb: KarSuMenuButton, parentSize: Point): ArrayList<RectF> {
        val positions = ArrayList<RectF>()
        var pos = ArrayList<PointF>()

        val pn = bmb.piecePlaceEnum.pieceNumber()
        val pn_0_5 = pn / 2

        val w = bmb.hamWidth
        val w_0_5 = w / 2
        val h = bmb.hamHeight
        val h_0_5 = h / 2

        val vm = bmb.pieceVerticalMargin
        val vm_0_5 = vm / 2

        if (bmb.piecePlaceEnum != PiecePlaceEnum.Custom) {
            if (pn % 2 == 0) {
                for (i in pn_0_5 - 1 downTo 0) {
                    pos.add(point(0f, -h_0_5 - vm_0_5 - i * (h + vm)))
                }
                for (i in 0 until pn_0_5) {
                    pos.add(point(0f, +h_0_5 + vm_0_5 + i * (h + vm)))
                }
            } else {
                for (i in pn_0_5 - 1 downTo 0) {
                    pos.add(point(0f, -h - vm - i * (h + vm)))
                }
                pos.add(point(0f, 0f))
                for (i in 0 until pn_0_5) {
                    pos.add(point(0f, +h + vm + i * (h + vm)))
                }
            }
        } else {
            pos = bmb.customPiecePlacePositions
        }

        for (po in pos) {
            positions.add(
                RectF(
                    po.x + parentSize.x / 2 - w_0_5,
                    po.y + parentSize.y / 2 - h_0_5,
                    w,
                    h
                )
            )
        }

        return positions
    }

    @JvmStatic
    fun getShareDotPositions(
        bmb: KarSuMenuButton,
        parentSize: Point,
        n: Int
    ): ArrayList<RectF> {
        val positions = ArrayList<RectF>()

        val r = bmb.dotRadius
        val h = (bmb.shareLineLength * sqrt(3.0) / 3).toFloat()
        val h_0_5 = h / 2
        val l_0_5 = bmb.shareLineLength / 2

        for (i in 0 until n) {
            when (i % 3) {
                0 -> positions.add(RectF(h_0_5, -l_0_5, r + r, r + r))
                1 -> positions.add(RectF(-h, 0f, r + r, r + r))
                2 -> positions.add(RectF(h_0_5, +l_0_5, r + r, r + r))
            }
        }

        positions.sortWith { o1, o2 ->
            when {
                o1.top < o2.top -> -1
                o1.top > o2.top -> 1
                else -> 0
            }
        }

        for (i in positions.indices) {
            positions[i].left += parentSize.x / 2 - r
            positions[i].top += parentSize.y / 2 - r
        }

        return positions
    }

    @JvmStatic
    fun createPiece(bmb: KarSuMenuButton, builder: KarSuButtonBuilder<*>): KarSuPiece {
        return when (bmb.buttonEnum) {
            com.karsu.ballonsmenu.ButtonEnum.SimpleCircle,
            com.karsu.ballonsmenu.ButtonEnum.TextInsideCircle,
            com.karsu.ballonsmenu.ButtonEnum.TextOutsideCircle -> {
                createDot(bmb.context, builder, bmb.pieceCornerRadius)
            }
            com.karsu.ballonsmenu.ButtonEnum.Ham -> {
                createHam(bmb.context, builder, bmb.pieceCornerRadius)
            }
            else -> throw RuntimeException("Unknown button-enum!")
        }
    }

    private fun createDot(
        context: Context,
        builder: KarSuButtonBuilder<*>,
        pieceCornerRadius: Float
    ): Dot {
        val dot = Dot(context)
        builder.piece(dot)
        dot.init(builder.pieceColor(context), pieceCornerRadius)
        return dot
    }

    private fun createHam(
        context: Context,
        builder: KarSuButtonBuilder<*>,
        pieceCornerRadius: Float
    ): Ham {
        val ham = Ham(context)
        builder.piece(ham)
        ham.init(builder.pieceColor(context), pieceCornerRadius)
        return ham
    }

    private fun point(x: Float, y: Float): PointF = PointF(x, y)
}
