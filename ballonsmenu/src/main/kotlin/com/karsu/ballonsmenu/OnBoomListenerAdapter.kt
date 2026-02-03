/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

/**
 * Helper class for creating OnBoomListener with default implementations.
 */
abstract class OnBoomListenerAdapter : OnBoomListener {
    override fun onBoomWillShow() { /* empty */ }
    override fun onBoomDidShow() { /* empty */ }
    override fun onBoomWillHide() { /* empty */ }
    override fun onBoomDidHide() { /* empty */ }
    override fun onBoomButtonClick(index: Int) { /* empty */ }
    override fun onBackgroundClick() { /* empty */ }
}
