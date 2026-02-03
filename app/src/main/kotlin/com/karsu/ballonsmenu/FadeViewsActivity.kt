/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.BoomMenuButton

class FadeViewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fade_views)

        findViewById<BoomMenuButton>(R.id.bmb1).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        findViewById<BoomMenuButton>(R.id.bmb2).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getHamButtonBuilderWithDifferentPieceColor())
            }
        }
    }
}
