/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.KarSuMenuButton

class EaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ease)

        listOf(
            R.id.bmb1, R.id.bmb2, R.id.bmb3,
            R.id.bmb4, R.id.bmb5, R.id.bmb6,
            R.id.bmb7, R.id.bmb8, R.id.bmb9
        ).forEach { initBmb(it) }
    }

    private fun initBmb(res: Int): KarSuMenuButton =
        findViewById<KarSuMenuButton>(res).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }
}
