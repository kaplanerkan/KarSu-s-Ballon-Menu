/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.piece

import android.content.Context
import android.graphics.drawable.GradientDrawable
import com.karsu.ballonsmenu.R
import com.karsu.ballonsmenu.Util

internal class Dot(context: Context) : KarSuPiece(context) {

    override fun init(color: Int, cornerRadius: Float) {
        val backgroundDrawable = if (cornerRadius < 0) {
            Util.getDrawable(this, R.drawable.piece_dot, null).mutate()
        } else {
            Util.getDrawable(this, R.drawable.piece, null).mutate()
        }

        (backgroundDrawable as GradientDrawable).setColor(color)
        if (cornerRadius >= 0) {
            backgroundDrawable.cornerRadius = cornerRadius
        }
        Util.setDrawable(this, backgroundDrawable)
    }

    override fun setColor(color: Int) {
        (background as? GradientDrawable)?.setColor(color)
    }

    override fun setColorRes(colorRes: Int) {
        setColor(Util.getColor(context, colorRes))
    }
}
