package com.karsu.ballonsmenu;

import com.karsu.ballonsmenu.BoomButtons.BoomButton;

/**
 * Created by Erkan Kaplan on 2026-02-03
 */

public interface OnBoomListener {

    /**
     * When one of the boom-button is clicked.
     *
     * @param index index of the clicked boom-button
     * @param boomButton the clicked boom-button
     */
    void onClicked(int index, BoomButton boomButton);

    /**
     * When the background of boom-buttons is clicked.
     */
    void onBackgroundClick();

    /**
     * When the BMB is going to hide its boom-buttons.
     */
    void onBoomWillHide();

    /**
     * When the BMB finishes hide animations.
     */
    void onBoomDidHide();

    /**
     * When the BMB is going to show its boom-buttons.
     */
    void onBoomWillShow();

    /**
     * When the BMB finished boom animations.
     */
    void onBoomDidShow();

}
