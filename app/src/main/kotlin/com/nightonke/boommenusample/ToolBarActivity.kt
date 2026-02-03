package com.nightonke.boommenusample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.nightonke.boommenu.BoomMenuButton
import com.nightonke.boommenu.ButtonEnum

class ToolBarActivity : AppCompatActivity() {

    private lateinit var bmb: BoomMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tool_bar)

        findViewById<Toolbar>(R.id.tool_bar).let { toolbar ->
            setSupportActionBar(toolbar)
        }

        bmb = findViewById<BoomMenuButton>(R.id.bmb).apply {
            buttonEnum = ButtonEnum.Ham
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getHamButtonBuilderWithDifferentPieceColor())
            }
        }
    }
}
