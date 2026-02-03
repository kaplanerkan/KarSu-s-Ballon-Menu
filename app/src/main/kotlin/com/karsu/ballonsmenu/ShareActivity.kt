/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.BoomMenuButton

class ShareActivity : AppCompatActivity() {

    private lateinit var bmb1: BoomMenuButton
    private lateinit var bmb2: BoomMenuButton
    private lateinit var bmb3: BoomMenuButton

    private lateinit var showDelaySeekText: TextView
    private lateinit var showDurationSeekText: TextView
    private lateinit var hideDelaySeekText: TextView
    private lateinit var hideDurationSeekText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        bmb1 = findViewById<BoomMenuButton>(R.id.bmb1).also { initBmb(it) }
        bmb2 = findViewById<BoomMenuButton>(R.id.bmb2).also { initBmbWithWhitePieceColor(it) }
        bmb3 = findViewById<BoomMenuButton>(R.id.bmb3).also { initBmb(it) }

        bmb1.apply {
            shareLineLength = 45
            shareLineWidth = 5
            dotRadius = 12
        }

        bmb3.apply {
            shareLine1Color = Color.BLACK
            shareLine2Color = Color.BLACK
        }

        initShowDelaySeek()
        initShowDurationSeek()
        initHideDelaySeek()
        initHideDurationSeek()
    }

    private fun initBmb(bmb: BoomMenuButton): BoomMenuButton = bmb.apply {
        repeat(buttonPlaceEnum.buttonNumber()) {
            addBuilder(BuilderManager.getTextInsideCircleButtonBuilder())
        }
    }

    private fun initBmbWithWhitePieceColor(bmb: BoomMenuButton): BoomMenuButton = bmb.apply {
        repeat(buttonPlaceEnum.buttonNumber()) {
            addBuilder(BuilderManager.getTextInsideCircleButtonBuilderWithDifferentPieceColor())
        }
    }

    private fun initShowDelaySeek() {
        showDelaySeekText = findViewById(R.id.show_delay_text)

        findViewById<SeekBar>(R.id.show_delay_seek).apply {
            max = 1000
            progress = bmb1.showDelay.toInt()
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    showDelaySeekText.text = "Show delay = ${seekBar.progress} ms"
                    bmb1.showDelay = progress.toLong()
                    bmb2.showDelay = progress.toLong()
                    bmb3.showDelay = progress.toLong()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
            showDelaySeekText.text = "Show delay = $progress ms"
        }
    }

    private fun initShowDurationSeek() {
        showDurationSeekText = findViewById(R.id.show_duration_text)

        findViewById<SeekBar>(R.id.show_duration_seek).apply {
            max = 1000
            progress = bmb1.showDuration.toInt()
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    showDurationSeekText.text = "Show duration = ${seekBar.progress} ms"
                    bmb1.showDuration = progress.toLong()
                    bmb2.showDuration = progress.toLong()
                    bmb3.showDuration = progress.toLong()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
            showDurationSeekText.text = "Show duration = $progress ms"
        }
    }

    private fun initHideDelaySeek() {
        hideDelaySeekText = findViewById(R.id.hide_delay_text)

        findViewById<SeekBar>(R.id.hide_delay_seek).apply {
            max = 1000
            progress = bmb1.hideDelay.toInt()
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    hideDelaySeekText.text = "Hide delay = ${seekBar.progress} ms"
                    bmb1.hideDelay = progress.toLong()
                    bmb2.hideDelay = progress.toLong()
                    bmb3.hideDelay = progress.toLong()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
            hideDelaySeekText.text = "Hide delay = $progress ms"
        }
    }

    private fun initHideDurationSeek() {
        hideDurationSeekText = findViewById(R.id.hide_duration_text)

        findViewById<SeekBar>(R.id.hide_duration_seek).apply {
            max = 1000
            progress = bmb1.hideDuration.toInt()
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    hideDurationSeekText.text = "Hide duration = ${seekBar.progress} ms"
                    bmb1.hideDuration = progress.toLong()
                    bmb2.hideDuration = progress.toLong()
                    bmb3.hideDuration = progress.toLong()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
            hideDurationSeekText.text = "Hide duration = $progress ms"
        }
    }
}
