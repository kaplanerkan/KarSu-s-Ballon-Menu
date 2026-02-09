/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.feature

import com.karsu.ballonsmenu.helper.BuilderManager
import com.karsu.ballonsmenu.KarSuMenuButton
import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.app.databinding.ActivityShareBinding

class ShareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShareBinding
    private lateinit var bmb1: KarSuMenuButton
    private lateinit var bmb2: KarSuMenuButton
    private lateinit var bmb3: KarSuMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bmb1 = binding.bmb1.also { initBmb(it) }
        bmb2 = binding.bmb2.also { initBmbWithWhitePieceColor(it) }
        bmb3 = binding.bmb3.also { initBmb(it) }

        bmb1.apply {
            setShareLineLength(45f)
            setShareLineWidth(5f)
            setDotRadius(12f)
        }

        bmb3.apply {
            setShareLine1Color(Color.BLACK)
            setShareLine2Color(Color.BLACK)
        }

        initShowDelaySeek()
        initShowDurationSeek()
        initHideDelaySeek()
        initHideDurationSeek()
    }

    private fun initBmb(bmb: KarSuMenuButton): KarSuMenuButton = bmb.apply {
        repeat(buttonPlaceEnum.buttonNumber()) {
            addBuilder(BuilderManager.getTextInsideCircleButtonBuilder())
        }
    }

    private fun initBmbWithWhitePieceColor(bmb: KarSuMenuButton): KarSuMenuButton = bmb.apply {
        repeat(buttonPlaceEnum.buttonNumber()) {
            addBuilder(BuilderManager.getTextInsideCircleButtonBuilderWithDifferentPieceColor())
        }
    }

    private fun initShowDelaySeek() {
        binding.showDelaySeek.apply {
            max = 1000
            progress = bmb1.showDelay.toInt()
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    binding.showDelayText.text = getString(R.string.show_delay_format, seekBar.progress)
                    bmb1.showDelay = progress.toLong()
                    bmb2.showDelay = progress.toLong()
                    bmb3.showDelay = progress.toLong()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
            binding.showDelayText.text = getString(R.string.show_delay_format, progress)
        }
    }

    private fun initShowDurationSeek() {
        binding.showDurationSeek.apply {
            max = 1000
            progress = bmb1.showDuration.toInt()
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    binding.showDurationText.text = getString(R.string.show_duration_format, seekBar.progress)
                    bmb1.showDuration = progress.toLong()
                    bmb2.showDuration = progress.toLong()
                    bmb3.showDuration = progress.toLong()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
            binding.showDurationText.text = getString(R.string.show_duration_format, progress)
        }
    }

    private fun initHideDelaySeek() {
        binding.hideDelaySeek.apply {
            max = 1000
            progress = bmb1.hideDelay.toInt()
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    binding.hideDelayText.text = getString(R.string.hide_delay_format, seekBar.progress)
                    bmb1.hideDelay = progress.toLong()
                    bmb2.hideDelay = progress.toLong()
                    bmb3.hideDelay = progress.toLong()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
            binding.hideDelayText.text = getString(R.string.hide_delay_format, progress)
        }
    }

    private fun initHideDurationSeek() {
        binding.hideDurationSeek.apply {
            max = 1000
            progress = bmb1.hideDuration.toInt()
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    binding.hideDurationText.text = getString(R.string.hide_duration_format, seekBar.progress)
                    bmb1.hideDuration = progress.toLong()
                    bmb2.hideDuration = progress.toLong()
                    bmb3.hideDuration = progress.toLong()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
            binding.hideDurationText.text = getString(R.string.hide_duration_format, progress)
        }
    }
}
