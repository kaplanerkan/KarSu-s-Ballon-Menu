package com.nightonke.boommenusample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nightonke.boommenu.BoomMenuButton

class ControlActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bmb: BoomMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        bmb = findViewById<BoomMenuButton>(R.id.bmb).apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        listOf(
            R.id.boom,
            R.id.reboom,
            R.id.boom_immediately,
            R.id.reboom_immediately
        ).forEach { id ->
            findViewById<View>(id).setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.boom -> bmb.boom()
            R.id.reboom -> bmb.reboom()
            R.id.boom_immediately -> bmb.boomImmediately()
            R.id.reboom_immediately -> bmb.reboomImmediately()
        }
    }
}
