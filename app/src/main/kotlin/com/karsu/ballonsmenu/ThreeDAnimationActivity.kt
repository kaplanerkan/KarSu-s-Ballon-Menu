/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.slider.Slider
import com.google.android.material.textview.MaterialTextView

import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.KarSuMenuButton

class ThreeDAnimationActivity : AppCompatActivity() {

    private lateinit var durationTextView: MaterialTextView
    private lateinit var bmb1: KarSuMenuButton
    private lateinit var bmb2: KarSuMenuButton
    private lateinit var bmb3: KarSuMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three_d_animation)

        bmb1 = findViewById<KarSuMenuButton>(R.id.bmb1).also { bmb ->
            repeat(bmb.piecePlaceEnum.pieceNumber()) {
                bmb.addBuilder(BuilderManager.getSquareSimpleCircleButtonBuilder())
            }
        }

        bmb2 = findViewById<KarSuMenuButton>(R.id.bmb2).also { bmb ->
            repeat(bmb.piecePlaceEnum.pieceNumber()) {
                bmb.addBuilder(BuilderManager.getHamButtonBuilderWithDifferentPieceColor())
            }
        }

        bmb3 = findViewById<KarSuMenuButton>(R.id.bmb3).also { bmb ->
            repeat(bmb.piecePlaceEnum.pieceNumber()) {
                bmb.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder())
            }
        }

        findViewById<MaterialSwitch>(R.id.three_d_animation_switch).apply {
            setOnCheckedChangeListener { _, isChecked ->
                bmb1.isUse3DTransformAnimation = isChecked
                bmb2.isUse3DTransformAnimation = isChecked
                bmb3.isUse3DTransformAnimation = isChecked
            }
            isChecked = true
        }

        durationTextView = findViewById(R.id.duration_text)

        findViewById<Slider>(R.id.duration_seek).apply {
            value = 300f
            addOnChangeListener { _, value, _ ->
                val duration = value.toLong()
                durationTextView.text = "Show/Hide duration = $duration ms"
                bmb1.duration = duration
                bmb2.duration = duration
                bmb3.duration = duration
            }
            // Trigger initial text update
            durationTextView.text = "Show/Hide duration = ${value.toLong()} ms"
        }
    }
}
