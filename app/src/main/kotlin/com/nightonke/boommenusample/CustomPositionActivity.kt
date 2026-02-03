package com.nightonke.boommenusample

import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nightonke.boommenu.BoomMenuButton
import com.nightonke.boommenu.Util

class CustomPositionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_position)

        initializeBmb1()
        initializeBmb2()
        initializeBmb3()
        initializeBmb4()
    }

    private fun initializeBmb1() {
        findViewById<BoomMenuButton>(R.id.bmb1).apply {
            repeat(buttonPlaceEnum.buttonNumber()) {
                addBuilder(BuilderManager.getHamButtonBuilder())
            }

            val w05 = hamWidth / 2
            val h05 = hamHeight / 2
            val hm05 = pieceHorizontalMargin / 2
            val vm05 = pieceVerticalMargin / 2

            customPiecePlacePositions.also { positions ->
                positions.add(PointF(-w05 - hm05, -h05 - vm05))
                positions.add(PointF(+w05 + hm05, -h05 - vm05))
                positions.add(PointF(-w05 - hm05, +h05 + vm05))
                positions.add(PointF(+w05 + hm05, +h05 + vm05))
            }
        }
    }

    private fun initializeBmb2() {
        findViewById<BoomMenuButton>(R.id.bmb2).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }

            customButtonPlacePositions.also { positions ->
                positions.add(PointF(Util.dp2px(-80f), Util.dp2px(-80f)))
                positions.add(PointF(0f, 0f))
                positions.add(PointF(Util.dp2px(+80f), Util.dp2px(+80f)))
            }
        }
    }

    private fun initializeBmb3() {
        findViewById<BoomMenuButton>(R.id.bmb3).apply {
            repeat(12) {
                addBuilder(BuilderManager.getTextOutsideCircleButtonBuilderWithDifferentPieceColor())
            }

            val w = Util.dp2px(80f)
            val h = Util.dp2px(96f)
            val h05 = h / 2
            val h15 = h * 1.5f

            val hm = buttonHorizontalMargin
            val vm = buttonVerticalMargin
            val vm05 = vm / 2
            val vm15 = vm * 1.5f

            customButtonPlacePositions.also { positions ->
                positions.add(PointF(-w - hm, -h15 - vm15))
                positions.add(PointF(0f, -h15 - vm15))
                positions.add(PointF(+w + hm, -h15 - vm15))
                positions.add(PointF(-w - hm, -h05 - vm05))
                positions.add(PointF(0f, -h05 - vm05))
                positions.add(PointF(+w + hm, -h05 - vm05))
                positions.add(PointF(-w - hm, +h05 + vm05))
                positions.add(PointF(0f, +h05 + vm05))
                positions.add(PointF(+w + hm, +h05 + vm05))
                positions.add(PointF(-w - hm, +h15 + vm15))
                positions.add(PointF(0f, +h15 + vm15))
                positions.add(PointF(+w + hm, +h15 + vm15))
            }
        }
    }

    private fun initializeBmb4() {
        findViewById<BoomMenuButton>(R.id.bmb4).apply {
            repeat(3) {
                addBuilder(BuilderManager.getTextInsideCircleButtonBuilder())
            }

            customPiecePlacePositions.also { positions ->
                positions.add(PointF(Util.dp2px(+6f), Util.dp2px(-6f)))
                positions.add(PointF(0f, 0f))
                positions.add(PointF(Util.dp2px(-6f), Util.dp2px(+6f)))
            }

            customButtonPlacePositions.also { positions ->
                positions.add(PointF(Util.dp2px(-80f), Util.dp2px(-80f)))
                positions.add(PointF(0f, 0f))
                positions.add(PointF(Util.dp2px(+80f), Util.dp2px(+80f)))
            }
        }
    }
}
