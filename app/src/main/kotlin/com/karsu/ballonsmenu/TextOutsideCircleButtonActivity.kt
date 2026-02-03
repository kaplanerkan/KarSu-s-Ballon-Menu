/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import android.util.Pair
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView

import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.KarSuButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.KarSuMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum

class TextOutsideCircleButtonActivity : AppCompatActivity() {

    private lateinit var bmb: KarSuMenuButton
    private val piecesAndButtons = ArrayList<Pair<PiecePlaceEnum, ButtonPlaceEnum>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_outside_circle_button)

        bmb = findViewById<KarSuMenuButton>(R.id.bmb).apply {
            buttonEnum = ButtonEnum.TextOutsideCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_1
            buttonPlaceEnum = ButtonPlaceEnum.SC_1
            addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder())
        }

        findViewById<ListView>(R.id.list_view).apply {
            adapter = ArrayAdapter(
                this@TextOutsideCircleButtonActivity,
                android.R.layout.simple_expandable_list_item_1,
                BuilderManager.getCircleButtonData(piecesAndButtons)
            )
            setOnItemClickListener { _, _, position, _ ->
                bmb.piecePlaceEnum = piecesAndButtons[position].first
                bmb.buttonPlaceEnum = piecesAndButtons[position].second
                bmb.clearBuilders()
                repeat(bmb.piecePlaceEnum.pieceNumber()) {
                    bmb.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder())
                }
            }
        }
    }
}
