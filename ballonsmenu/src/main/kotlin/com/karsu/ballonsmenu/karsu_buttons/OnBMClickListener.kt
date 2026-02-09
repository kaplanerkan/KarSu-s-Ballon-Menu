/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.karsu_buttons

fun interface OnBMClickListener {
    /**
     * When the karsu-button is click
     *
     * @param index the index of karsu-button
     */
    fun onKarSuButtonClick(index: Int)
}
