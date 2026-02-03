/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.Piece

import android.content.Context
import android.graphics.RectF
import android.view.View
import android.widget.FrameLayout

abstract class BoomPiece(context: Context) : View(context) {

    private var requestLayoutNotFinish = false

    abstract fun init(color: Int, cornerRadius: Float)

    abstract fun setColor(color: Int)

    abstract fun setColorRes(colorRes: Int)

    fun place(rectF: RectF) {
        (layoutParams as? FrameLayout.LayoutParams)?.let { layoutParams ->
            layoutParams.leftMargin = rectF.left.toInt()
            layoutParams.topMargin = rectF.top.toInt()
            layoutParams.width = rectF.right.toInt()
            layoutParams.height = rectF.bottom.toInt()
            setLayoutParams(layoutParams)
        }
    }

    override fun requestLayout() {
        if (requestLayoutNotFinish) return
        requestLayoutNotFinish = true
        super.requestLayout()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        requestLayoutNotFinish = false
    }
}
