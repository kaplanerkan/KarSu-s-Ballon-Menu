/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.KarSuMenuButton

class SquareAndPieceCornerRadiusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square_and_piece_corner_radius)

        findViewById<KarSuMenuButton>(R.id.bmb1).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSquareSimpleCircleButtonBuilder())
            }
        }

        findViewById<KarSuMenuButton>(R.id.bmb2).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSquareTextInsideCircleButtonBuilder())
            }
        }

        findViewById<KarSuMenuButton>(R.id.bmb3).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getTextOutsideCircleButtonBuilderWithDifferentPieceColor())
            }
        }

        findViewById<KarSuMenuButton>(R.id.bmb4).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getPieceCornerRadiusHamButtonBuilder())
            }
        }
    }
}
