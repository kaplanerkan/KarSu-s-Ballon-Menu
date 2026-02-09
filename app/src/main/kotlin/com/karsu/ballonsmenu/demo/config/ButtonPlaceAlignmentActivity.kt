/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.config

import com.karsu.ballonsmenu.helper.BuilderManager
import com.karsu.ballonsmenu.KarSuMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.Util
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.app.databinding.ActivityButtonPlaceAlignmentBinding
import com.karsu.ballonsmenu.karsu_buttons.ButtonPlaceAlignmentEnum
import com.karsu.ballonsmenu.karsu_buttons.ButtonPlaceEnum
import com.karsu.ballonsmenu.piece.PiecePlaceEnum

class ButtonPlaceAlignmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityButtonPlaceAlignmentBinding
    private lateinit var bmb: KarSuMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityButtonPlaceAlignmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bmb = binding.bmb.apply {
            buttonEnum = ButtonEnum.SimpleCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_4_1
            buttonPlaceEnum = ButtonPlaceEnum.SC_4_1
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        binding.listView.apply {
            adapter = ArrayAdapter(
                this@ButtonPlaceAlignmentActivity,
                android.R.layout.simple_expandable_list_item_1,
                getData()
            )
            setOnItemClickListener { _, _, position, _ ->
                bmb.buttonPlaceAlignmentEnum = ButtonPlaceAlignmentEnum.getEnum(position)
            }
        }

        initTopMarginSeek()
        initBottomMarginSeek()
        initLeftMarginSeek()
        initRightMarginSeek()
    }

    private fun initTopMarginSeek() {
        binding.topMarginSeek.apply {
            max = Util.dp2px(50f)
            progress = bmb.buttonTopMargin.toInt()
            binding.topMarginText.text = getString(R.string.top_margin_format, progress)
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    binding.topMarginText.text = getString(R.string.top_margin_format, progress)
                    bmb.buttonTopMargin = progress.toFloat()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }

    private fun initBottomMarginSeek() {
        binding.bottomMarginSeek.apply {
            max = Util.dp2px(50f)
            progress = bmb.buttonBottomMargin.toInt()
            binding.bottomMarginText.text = getString(R.string.bottom_margin_format, progress)
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    binding.bottomMarginText.text = getString(R.string.bottom_margin_format, progress)
                    bmb.buttonBottomMargin = progress.toFloat()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }

    private fun initLeftMarginSeek() {
        binding.leftMarginSeek.apply {
            max = Util.dp2px(50f)
            progress = bmb.buttonLeftMargin.toInt()
            binding.leftMarginText.text = getString(R.string.left_margin_format, progress)
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    binding.leftMarginText.text = getString(R.string.left_margin_format, progress)
                    bmb.buttonLeftMargin = progress.toFloat()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }

    private fun initRightMarginSeek() {
        binding.rightMarginSeek.apply {
            max = Util.dp2px(50f)
            progress = bmb.buttonRightMargin.toInt()
            binding.rightMarginText.text = getString(R.string.right_margin_format, progress)
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    binding.rightMarginText.text = getString(R.string.right_margin_format, progress)
                    bmb.buttonRightMargin = progress.toFloat()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }

    private fun getData(): List<String> =
        ButtonPlaceAlignmentEnum.values()
            .dropLast(1)
            .mapIndexed { index, _ -> ButtonPlaceAlignmentEnum.getEnum(index).toString() }
}
