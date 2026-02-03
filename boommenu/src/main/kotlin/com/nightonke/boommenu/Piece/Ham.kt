package com.nightonke.boommenu.Piece

import android.content.Context
import android.graphics.drawable.GradientDrawable
import com.nightonke.boommenu.R
import com.nightonke.boommenu.Util

/**
 * Created by Weiping Huang at 00:00 on 16/11/27
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 */
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
