package com.karsu.ballonsmenu.Piece;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.karsu.ballonsmenu.R;
import com.karsu.ballonsmenu.Util;

/**
 * Created by Erkan Kaplan on 2026-02-03
 */

final class Ham extends BoomPiece {

    public Ham(Context context) {
        super(context);
    }

    @Override
    public void init(int color, float cornerRadius) {
        Drawable backgroundDrawable = Util.getDrawable(this, R.drawable.piece, null);
        ((GradientDrawable)backgroundDrawable).setColor(color);
        ((GradientDrawable)backgroundDrawable).setCornerRadius(cornerRadius);
        Util.setDrawable(this, backgroundDrawable);
    }

    @Override
    public void setColor(int color) {
        ((GradientDrawable)getBackground()).setColor(color);
    }

    @Override
    public void setColorRes(int colorRes) { setColor(Util.getColor(getContext(), colorRes)); }
}

