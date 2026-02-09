/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.feature

import com.karsu.ballonsmenu.helper.BuilderManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.databinding.ActivityFadeViewsBinding

class FadeViewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFadeViewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bmb1.apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        binding.bmb2.apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getHamButtonBuilderWithDifferentPieceColor())
            }
        }
    }
}
