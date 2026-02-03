/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.Piece

import android.content.Context
import android.graphics.drawable.GradientDrawable
import com.karsu.ballonsmenu.R
import com.karsu.ballonsmenu.Util

internal class Ham(context: Context) : BoomPiece(context) {

    override fun init(color: Int, cornerRadius: Float) {
        val backgroundDrawable = Util.getDrawable(this, R.drawable.piece, null)
        (backgroundDrawable as GradientDrawable).apply {
            setColor(color)
            this.cornerRadius = cornerRadius
        }
        Util.setDrawable(this, backgroundDrawable)
    }

    override fun setColor(color: Int) {
        (background as GradientDrawable).setColor(color)
    }

    override fun setColorRes(colorRes: Int) {
        setColor(Util.getColor(context, colorRes))
    }
}
