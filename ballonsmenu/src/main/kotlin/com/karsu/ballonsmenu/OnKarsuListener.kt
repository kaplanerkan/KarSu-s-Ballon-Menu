/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

/**
 * Listener for BMB life-cycle.
 */
interface OnKarSuListener {
    /**
     * When the karsu animation is about to start.
     */
    fun onKarSuWillShow()

    /**
     * When the karsu animation has finished.
     */
    fun onKarSuDidShow()

    /**
     * When the re-karsu animation is about to start.
     */
    fun onKarSuWillHide()

    /**
     * When the re-karsu animation has finished.
     */
    fun onKarSuDidHide()

    /**
     * When the user clicks one of the karsu-buttons.
     *
     * @param index the index of the karsu-button
     */
    fun onKarSuButtonClick(index: Int)

    /**
     * When the user clicks the background of the BMB.
     */
    fun onBackgroundClick()
}
