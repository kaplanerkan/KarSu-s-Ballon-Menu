/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.config

import com.karsu.ballonsmenu.helper.BuilderManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.databinding.ActivitySquareAndPieceCornerRadiusBinding

class SquareAndPieceCornerRadiusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySquareAndPieceCornerRadiusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bmb1.apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSquareSimpleCircleButtonBuilder())
            }
        }

        binding.bmb2.apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSquareTextInsideCircleButtonBuilder())
            }
        }

        binding.bmb3.apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getTextOutsideCircleButtonBuilderWithDifferentPieceColor())
            }
        }

        binding.bmb4.apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getPieceCornerRadiusHamButtonBuilder())
            }
        }
    }
}
