/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import android.util.Pair
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.KarSuButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.KarSuMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum

class SimpleCircleButtonActivity : AppCompatActivity() {

    private lateinit var bmb: KarSuMenuButton
    private val piecesAndButtons = ArrayList<Pair<PiecePlaceEnum, ButtonPlaceEnum>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_circle_button)

        bmb = findViewById<KarSuMenuButton>(R.id.bmb).apply {
            buttonEnum = ButtonEnum.SimpleCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_1
            buttonPlaceEnum = ButtonPlaceEnum.SC_1
            addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
        }

        findViewById<ListView>(R.id.list_view).apply {
            adapter = ArrayAdapter(
                this@SimpleCircleButtonActivity,
                android.R.layout.simple_expandable_list_item_1,
                BuilderManager.getCircleButtonData(piecesAndButtons)
            )
            setOnItemClickListener { _, _, position, _ ->
                piecesAndButtons[position].let { pair ->
                    bmb.piecePlaceEnum = pair.first
                    bmb.buttonPlaceEnum = pair.second
                    bmb.clearBuilders()
                    repeat(bmb.piecePlaceEnum.pieceNumber()) {
                        bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
                    }
                }
            }
        }
    }
}
