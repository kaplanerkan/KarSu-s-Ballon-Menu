/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.Animation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.view.View
import android.widget.FrameLayout
import com.karsu.ballonsmenu.KarSuMenuButton
import com.karsu.ballonsmenu.Util

class ShareLinesView(context: Context) : View(context) {

    private var animationShowDelay1: Long = 0
    private var animationShowDuration1: Long = 0
    private var animationShowDelay2: Long = 0
    private var animationShowDuration2: Long = 0
    private var animationShowDelay3: Long = 0
    private var animationShowTotalDuration: Long = 0

    private var animationHideDelay1: Long = 0
    private var animationHideDuration1: Long = 0
    private var animationHideDelay2: Long = 0
    private var animationHideDuration2: Long = 0
    private var animationHideDelay3: Long = 0
    private var animationHideTotalDuration: Long = 0

    private var processForLine1 = 1f
    private var processForLine2 = 1f

    private var piecePositions: ArrayList<PointF>? = null

    private var line1Color = Color.WHITE
    private var line2Color = Color.WHITE
    private var lineWidth = 3f

    private val linePaint: Paint = Paint().apply {
        isAntiAlias = true
    }

    fun setData(piecePositions: ArrayList<RectF>?, bmb: KarSuMenuButton) {
        if (piecePositions == null) return
        val xOffset = bmb.dotRadius - lineWidth / 4f
        val yOffset = (bmb.dotRadius - lineWidth * kotlin.math.sqrt(3.0) / 4f).toFloat() + Util.dp2px(0.25f)

        this.piecePositions = ArrayList()
        for (piecePosition in piecePositions) {
            var existed = false
            for (p in this.piecePositions!!) {
                if (p.equals(piecePosition.left, piecePosition.top)) {
                    existed = true
                    break
                }
            }
            if (!existed) this.piecePositions!!.add(PointF(piecePosition.left, piecePosition.top))
        }
        for (piecePosition in this.piecePositions!!) piecePosition.offset(xOffset, yOffset)

        val pieceNumbers = IntArray(3)
        val pieceNumber = piecePositions.size
        for (i in 0 until pieceNumber) pieceNumbers[i % 3]++

        animationShowDelay1 = bmb.showDelay * (pieceNumbers[0] - 1)
        animationShowDuration1 = pieceNumbers[0] * bmb.showDelay
        animationShowDelay2 = (pieceNumbers[0] - 1 + pieceNumbers[1]) * bmb.showDelay
        animationShowDuration2 = (pieceNumbers[0] + pieceNumbers[1]) * bmb.showDelay
        animationShowDelay3 = bmb.showDelay * (pieceNumbers[2] - 1 + pieceNumbers[1] + pieceNumbers[0]) + bmb.showDuration
        animationShowTotalDuration = animationShowDelay3

        animationHideDelay1 = (pieceNumbers[2] - 1) * bmb.hideDelay + bmb.hideDuration
        animationHideDuration1 = pieceNumbers[2] * bmb.hideDelay + bmb.hideDuration
        animationHideDelay2 = bmb.hideDelay * (pieceNumbers[2] - 1 + pieceNumbers[1]) + bmb.hideDuration
        animationHideDuration2 = (pieceNumbers[2] + pieceNumbers[1]) * bmb.hideDelay + bmb.hideDuration
        animationHideDelay3 = bmb.hideDelay * (pieceNumbers[2] - 1 + pieceNumbers[1] + pieceNumbers[0]) + bmb.hideDuration
        animationHideTotalDuration = animationHideDelay3
    }

    @Suppress("unused")
    fun setShowProcess(process: Float) {
        val current = (process * animationShowTotalDuration).toLong()
        when {
            animationShowDelay1 < current && current <= animationShowDuration1 -> {
                processForLine1 = (animationShowDuration1 - current) /
                        (animationShowDuration1 - animationShowDelay1).toFloat()
            }
            animationShowDuration1 < current && current <= animationShowDelay2 -> {
                processForLine1 = 0f
            }
            animationShowDelay2 < current && current <= animationShowDuration2 -> {
                processForLine2 = (animationShowDuration2 - current) /
                        (animationShowDuration2 - animationShowDelay2).toFloat()
            }
            animationShowDuration2 <= current -> {
                processForLine1 = 0f
                processForLine2 = 0f
            }
        }
        invalidate()
    }

    @Suppress("unused")
    fun setHideProcess(process: Float) {
        val current = (process * animationHideTotalDuration).toLong()
        when {
            animationHideDelay1 < current && current <= animationHideDuration1 -> {
                processForLine2 = 1 - (animationHideDuration1 - current) /
                        (animationHideDuration1 - animationHideDelay1).toFloat()
            }
            animationHideDuration1 < current && current <= animationHideDelay2 -> {
                processForLine2 = 1f
            }
            animationHideDelay2 < current && current <= animationHideDuration2 -> {
                processForLine1 = 1 - (animationHideDuration2 - current) /
                        (animationHideDuration2 - animationHideDelay2).toFloat()
            }
            animationHideDuration2 <= current -> {
                processForLine1 = 1f
                processForLine2 = 1f
            }
        }
        invalidate()
    }

    fun setLine1Color(line1Color: Int) {
        this.line1Color = line1Color
        linePaint.color = line1Color
    }

    fun setLine2Color(line2Color: Int) {
        this.line2Color = line2Color
    }

    fun setLineWidth(lineWidth: Float) {
        this.lineWidth = lineWidth
        linePaint.strokeWidth = lineWidth
    }

    override fun onDraw(canvas: Canvas) {
        val positions = piecePositions ?: return
        if (positions.size < 3) return

        canvas.drawLine(
            positions[1].x,
            positions[1].y,
            (positions[0].x - positions[1].x) * processForLine1 + positions[1].x,
            (positions[0].y - positions[1].y) * processForLine1 + positions[1].y,
            linePaint
        )
        linePaint.color = line2Color
        canvas.drawLine(
            positions[2].x,
            positions[2].y,
            (positions[1].x - positions[2].x) * processForLine2 + positions[2].x,
            (positions[1].y - positions[2].y) * processForLine2 + positions[2].y,
            linePaint
        )
        super.onDraw(canvas)
    }

    fun place(left: Int, top: Int, width: Int, height: Int) {
        val layoutParams = layoutParams as? FrameLayout.LayoutParams
        if (layoutParams != null) {
            layoutParams.leftMargin = left
            layoutParams.topMargin = top
            layoutParams.width = width
            layoutParams.height = height
            setLayoutParams(layoutParams)
        }
    }
}
