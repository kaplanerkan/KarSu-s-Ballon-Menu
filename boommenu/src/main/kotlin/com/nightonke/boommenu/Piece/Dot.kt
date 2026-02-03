package com.nightonke.boommenu.Piece

import android.content.Context
import android.graphics.drawable.GradientDrawable
import com.nightonke.boommenu.R
import com.nightonke.boommenu.Util

/**
 * Created by Weiping Huang at 01:03 on 16/11/7
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 */
internal class Dot(context: Context) : BoomPiece(context) {

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
        (background as GradientDrawable).setColor(color)
    }

    override fun setColorRes(colorRes: Int) {
        setColor(Util.getColor(context, colorRes))
    }
}
