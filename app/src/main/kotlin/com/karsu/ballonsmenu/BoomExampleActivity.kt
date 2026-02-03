/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.Animation.KarSuEnum
import com.karsu.ballonsmenu.KarSuButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.KarSuMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum

class KarSuExampleActivity : AppCompatActivity() {

    private lateinit var bmb: KarSuMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boom_example)

        bmb = findViewById<KarSuMenuButton>(R.id.bmb).apply {
            buttonEnum = ButtonEnum.SimpleCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_9_1
            buttonPlaceEnum = ButtonPlaceEnum.SC_9_1
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        findViewById<ListView>(R.id.list_view).apply {
            adapter = ArrayAdapter(
                this@KarSuExampleActivity,
                android.R.layout.simple_expandable_list_item_1,
                getData()
            )
            setOnItemClickListener { _, _, position, _ ->
                bmb.boomEnum = KarSuEnum.values()[position]
            }
        }
    }

    private fun getData(): List<String> =
        KarSuEnum.values()
            .dropLast(1)
            .map { it.toString() }
}
