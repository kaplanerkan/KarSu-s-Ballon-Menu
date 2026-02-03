/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

/**
 * Listener for BMB life-cycle.
 */
interface OnBoomListener {
    /**
     * When the boom animation is about to start.
     */
    fun onBoomWillShow()

    /**
     * When the boom animation has finished.
     */
    fun onBoomDidShow()

    /**
     * When the re-boom animation is about to start.
     */
    fun onBoomWillHide()

    /**
     * When the re-boom animation has finished.
     */
    fun onBoomDidHide()

    /**
     * When the user clicks one of the boom-buttons.
     *
     * @param index the index of the boom-button
     */
    fun onBoomButtonClick(index: Int)

    /**
     * When the user clicks the background of the BMB.
     */
    fun onBackgroundClick()
}
