/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Switch
import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.BoomMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum

class DraggableActivity : AppCompatActivity() {

    private lateinit var bmb: BoomMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draggable)

        bmb = findViewById<BoomMenuButton>(R.id.bmb).apply {
            buttonEnum = ButtonEnum.SimpleCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_9_1
            buttonPlaceEnum = ButtonPlaceEnum.SC_9_1
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        findViewById<Switch>(R.id.draggable_switch).apply {
            setOnCheckedChangeListener { _, isChecked ->
                bmb.isDraggable = isChecked
            }
            isChecked = true
        }
    }
}
