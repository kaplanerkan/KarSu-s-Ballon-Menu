/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.animation

import android.graphics.Camera
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

class Rotate3DAnimation(
    private val centerX: Float,
    private val centerY: Float,
    private val xs: ArrayList<Float>,
    private val ys: ArrayList<Float>
) : Animation() {

    private var startX: Float = 0f
    private var startY: Float = 0f
    private var camera: Camera? = null
    private var view: View? = null

    init {
        setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                camera = null
                view = null
            }
        })
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        camera = Camera()
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val camera = this.camera ?: return
        val matrix = t.matrix

        var x = 0f
        var y = 0f
        if (interpolatedTime != 1f) {
            val xOffset = interpolatedTime * xs.size
            val lXIndex = xOffset.toInt()
            var rXIndex = lXIndex + 1
            if (rXIndex >= xs.size) rXIndex = xs.size - 1
            x = xs[lXIndex] + (xs[rXIndex] - xs[lXIndex]) * (xOffset - lXIndex)

            val yOffset = interpolatedTime * ys.size
            val lYIndex = yOffset.toInt()
            var rYIndex = lYIndex + 1
            if (rYIndex >= ys.size) rYIndex = ys.size - 1
            y = ys[lYIndex] + (ys[rYIndex] - ys[lYIndex]) * (yOffset - lYIndex)
        }

        camera.save()
        camera.rotateX(x)
        camera.rotateY(y)
        camera.getMatrix(matrix)
        camera.restore()

        val currentView = view ?: return
        val offsetX = currentView.x - startX
        val offsetY = currentView.y - startY

        matrix.preTranslate(-offsetX - centerX, -offsetY - centerY)
        matrix.postTranslate(offsetX + centerX, offsetY + centerY)
    }

    fun set(view: View, startX: Float, startY: Float) {
        this.view = view
        this.startX = startX
        this.startY = startY
    }
}
