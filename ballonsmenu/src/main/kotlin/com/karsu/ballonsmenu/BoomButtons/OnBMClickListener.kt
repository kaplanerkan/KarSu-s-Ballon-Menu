/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.BoomButtons

fun interface OnBMClickListener {
    /**
     * When the boom-button is click
     *
     * @param index the index of boom-button
     */
    fun onBoomButtonClick(index: Int)
}
