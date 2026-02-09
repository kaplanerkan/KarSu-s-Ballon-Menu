/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

internal class KarsuFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var requestLayoutNotFinish = false

    override fun requestLayout() {
        if (requestLayoutNotFinish) return
        requestLayoutNotFinish = true
        super.requestLayout()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        requestLayoutNotFinish = false
    }
}
