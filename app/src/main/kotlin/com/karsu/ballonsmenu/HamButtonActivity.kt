/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import android.util.Pair
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.BoomMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum

class HamButtonActivity : AppCompatActivity() {

    private lateinit var bmb: BoomMenuButton
    private val piecesAndButtons = ArrayList<Pair<*, *>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ham_button)

        bmb = findViewById<BoomMenuButton>(R.id.bmb).apply {
            buttonEnum = ButtonEnum.Ham
            piecePlaceEnum = PiecePlaceEnum.HAM_1
            buttonPlaceEnum = ButtonPlaceEnum.HAM_1
            addBuilder(BuilderManager.getHamButtonBuilder())
        }

        findViewById<ListView>(R.id.list_view).apply {
            adapter = ArrayAdapter(
                this@HamButtonActivity,
                android.R.layout.simple_expandable_list_item_1,
                BuilderManager.getHamButtonData(piecesAndButtons)
            )
            setOnItemClickListener { _, _, position, _ ->
                piecesAndButtons[position].let { pair ->
                    bmb.piecePlaceEnum = pair.first as PiecePlaceEnum
                    bmb.buttonPlaceEnum = pair.second as ButtonPlaceEnum
                    bmb.clearBuilders()
                    repeat(bmb.piecePlaceEnum.pieceNumber()) {
                        bmb.addBuilder(BuilderManager.getHamButtonBuilder())
                    }
                }
            }
        }
    }
}
