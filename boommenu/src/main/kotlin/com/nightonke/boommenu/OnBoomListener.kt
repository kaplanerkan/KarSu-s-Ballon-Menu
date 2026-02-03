package com.nightonke.boommenu

import com.nightonke.boommenu.BoomButtons.BoomButton

interface OnBoomListener {
    /**
     * When one of the boom-button is clicked.
     *
     * @param index index of the clicked boom-button
     * @param boomButton the clicked boom-button
     */
    fun onClicked(index: Int, boomButton: BoomButton)

    /**
     * When the background of boom-buttons is clicked.
     */
    fun onBackgroundClick()

    /**
     * When the BMB is going to hide its boom-buttons.
     */
    fun onBoomWillHide()

    /**
     * When the BMB finishes hide animations.
     */
    fun onBoomDidHide()

    /**
     * When the BMB is going to show its boom-buttons.
     */
    fun onBoomWillShow()

    /**
     * When the BMB finished boom animations.
     */
    fun onBoomDidShow()
}
