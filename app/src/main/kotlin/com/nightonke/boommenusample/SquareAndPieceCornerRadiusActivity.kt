package com.nightonke.boommenusample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nightonke.boommenu.BoomMenuButton

class SquareAndPieceCornerRadiusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square_and_piece_corner_radius)

        findViewById<BoomMenuButton>(R.id.bmb1).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSquareSimpleCircleButtonBuilder())
            }
        }

        findViewById<BoomMenuButton>(R.id.bmb2).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSquareTextInsideCircleButtonBuilder())
            }
        }

        findViewById<BoomMenuButton>(R.id.bmb3).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getTextOutsideCircleButtonBuilderWithDifferentPieceColor())
            }
        }

        findViewById<BoomMenuButton>(R.id.bmb4).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getPieceCornerRadiusHamButtonBuilder())
            }
        }
    }
}
