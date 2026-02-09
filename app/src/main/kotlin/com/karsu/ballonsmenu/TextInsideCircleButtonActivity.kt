/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import android.util.Pair
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView

import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.karsu_buttons.ButtonPlaceEnum
import com.karsu.ballonsmenu.piece.PiecePlaceEnum

class TextInsideCircleButtonActivity : AppCompatActivity() {

    private lateinit var bmb: KarSuMenuButton
    private val piecesAndButtons = ArrayList<Pair<PiecePlaceEnum, ButtonPlaceEnum>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_inside_circle_button)

        bmb = findViewById<KarSuMenuButton>(R.id.bmb).apply {
            buttonEnum = ButtonEnum.TextInsideCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_1
            buttonPlaceEnum = ButtonPlaceEnum.SC_1
            addBuilder(BuilderManager.getTextInsideCircleButtonBuilder())
        }

        findViewById<ListView>(R.id.list_view).apply {
            adapter = ArrayAdapter(
                this@TextInsideCircleButtonActivity,
                android.R.layout.simple_expandable_list_item_1,
                BuilderManager.getCircleButtonData(piecesAndButtons)
            )
            setOnItemClickListener { _, _, position, _ ->
                bmb.piecePlaceEnum = piecesAndButtons[position].first
                bmb.buttonPlaceEnum = piecesAndButtons[position].second
                bmb.clearBuilders()
                repeat(bmb.piecePlaceEnum.pieceNumber()) {
                    bmb.addBuilder(BuilderManager.getTextInsideCircleButtonBuilder())
                }
            }
        }
    }
}
