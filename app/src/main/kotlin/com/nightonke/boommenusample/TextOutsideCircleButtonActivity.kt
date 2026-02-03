package com.nightonke.boommenusample

import android.os.Bundle
import android.util.Pair
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.BoomMenuButton
import com.nightonke.boommenu.ButtonEnum
import com.nightonke.boommenu.Piece.PiecePlaceEnum

class TextOutsideCircleButtonActivity : AppCompatActivity() {

    private lateinit var bmb: BoomMenuButton
    private val piecesAndButtons = ArrayList<Pair<PiecePlaceEnum, ButtonPlaceEnum>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_outside_circle_button)

        bmb = findViewById<BoomMenuButton>(R.id.bmb).apply {
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
