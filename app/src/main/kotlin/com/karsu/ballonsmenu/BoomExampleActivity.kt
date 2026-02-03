/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.Animation.BoomEnum
import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.BoomMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum

class BoomExampleActivity : AppCompatActivity() {

    private lateinit var bmb: BoomMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boom_example)

        bmb = findViewById<BoomMenuButton>(R.id.bmb).apply {
            buttonEnum = ButtonEnum.SimpleCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_9_1
            buttonPlaceEnum = ButtonPlaceEnum.SC_9_1
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        findViewById<ListView>(R.id.list_view).apply {
            adapter = ArrayAdapter(
                this@BoomExampleActivity,
                android.R.layout.simple_expandable_list_item_1,
                getData()
            )
            setOnItemClickListener { _, _, position, _ ->
                bmb.boomEnum = BoomEnum.values()[position]
            }
        }
    }

    private fun getData(): List<String> =
        BoomEnum.values()
            .dropLast(1)
            .map { it.toString() }
}
