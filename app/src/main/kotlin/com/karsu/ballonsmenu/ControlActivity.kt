/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.KarSuMenuButton

class ControlActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bmb: KarSuMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        bmb = findViewById<KarSuMenuButton>(R.id.bmb).apply {
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
