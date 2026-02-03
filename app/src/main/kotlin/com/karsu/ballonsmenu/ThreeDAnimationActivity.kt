/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView

import com.karsu.ballonsmenu.BoomMenuButton

class ThreeDAnimationActivity : AppCompatActivity() {

    private lateinit var durationTextView: TextView
    private lateinit var bmb1: BoomMenuButton
    private lateinit var bmb2: BoomMenuButton
    private lateinit var bmb3: BoomMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three_d_animation)

        bmb1 = findViewById<BoomMenuButton>(R.id.bmb1).also { bmb ->
            repeat(bmb.piecePlaceEnum.pieceNumber()) {
                bmb.addBuilder(BuilderManager.getSquareSimpleCircleButtonBuilder())
            }
        }

        bmb2 = findViewById<BoomMenuButton>(R.id.bmb2).also { bmb ->
            repeat(bmb.piecePlaceEnum.pieceNumber()) {
                bmb.addBuilder(BuilderManager.getHamButtonBuilderWithDifferentPieceColor())
            }
        }

        bmb3 = findViewById<BoomMenuButton>(R.id.bmb3).also { bmb ->
            repeat(bmb.piecePlaceEnum.pieceNumber()) {
                bmb.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder())
            }
        }

        findViewById<Switch>(R.id.three_d_animation_switch).apply {
            setOnCheckedChangeListener { _, isChecked ->
                bmb1.setUse3DTransformAnimation(isChecked)
                bmb2.setUse3DTransformAnimation(isChecked)
                bmb3.setUse3DTransformAnimation(isChecked)
            }
            isChecked = true
        }

        val durationSeekBar = findViewById<SeekBar>(R.id.duration_seek).apply {
            max = 3000
            progress = 300
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    durationTextView.text = "Show/Hide duration = ${seekBar.progress} ms"
                    bmb1.duration = progress.toLong()
                    bmb2.duration = progress.toLong()
                    bmb3.duration = progress.toLong()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    // No action needed
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    // No action needed
                }
            })
        }

        durationTextView = findViewById<TextView>(R.id.duration_text).apply {
            text = "Show/Hide duration = ${durationSeekBar.progress} ms"
        }
    }
}
