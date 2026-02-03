package com.karsu.ballonsmenu.Piece;

import android.content.Context;
import android.graphics.RectF;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by Erkan Kaplan on 2026-02-03
 */

public abstract class BoomPiece extends View {

    private boolean requestLayoutNotFinish = false;

    public BoomPiece(Context context) {
        super(context);
    }

    public abstract void init(int color, float cornerRadius);

    public abstract void setColor(int color);

    public abstract void setColorRes(int colorRes);

    public void place(RectF rectF) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        if (layoutParams != null) {
            layoutParams.leftMargin = (int) rectF.left;
            layoutParams.topMargin = (int) rectF.top;
            layoutParams.width = (int) rectF.right;
            layoutParams.height = (int) rectF.bottom;
            setLayoutParams(layoutParams);
        }
    }

    @Override
    public void requestLayout() {
        if (requestLayoutNotFinish) return;
        requestLayoutNotFinish = true;
        super.requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        requestLayoutNotFinish = false;
    }
}

