package com.nightonke.boommenu

import com.nightonke.boommenu.BoomButtons.BoomButton

open class OnBoomListenerAdapter : OnBoomListener {
    /**
     * When one of the boom-button is clicked.
     *
     * @param index index of the clicked boom-button
     * @param boomButton the clicked boom-button
     */
    override fun onClicked(index: Int, boomButton: BoomButton) {}

    /**
     * When the background of boom-buttons is clicked.
     */
    override fun onBackgroundClick() {}

    /**
     * When the BMB is going to hide its boom-buttons.
     */
    override fun onBoomWillHide() {}

    /**
     * When the BMB finishes hide animations.
     */
    override fun onBoomDidHide() {}

    /**
     * When the BMB is going to show its boom-buttons.
     */
    override fun onBoomWillShow() {}

    /**
     * When the BMB finished boom animations.
     */
    override fun onBoomDidShow() {}
}
