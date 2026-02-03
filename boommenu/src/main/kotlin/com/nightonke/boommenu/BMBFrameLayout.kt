package com.nightonke.boommenu

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Created by Weiping Huang at 23:50 on 16/12/11
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 *
 * Converted to Kotlin
 */
internal class BMBFrameLayout @JvmOverloads constructor(
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
