/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

/**
 * Helper class for creating OnKarSuListener with default implementations.
 */
abstract class OnKarSuListenerAdapter : OnKarSuListener {
    override fun onKarSuWillShow() { /* empty */ }
    override fun onKarSuDidShow() { /* empty */ }
    override fun onKarSuWillHide() { /* empty */ }
    override fun onKarSuDidHide() { /* empty */ }
    override fun onKarSuButtonClick(index: Int) { /* empty */ }
    override fun onBackgroundClick() { /* empty */ }
}
