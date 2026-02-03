/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceAlignmentEnum
import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.BoomMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum
import com.karsu.ballonsmenu.Util

class ButtonPlaceAlignmentActivity : AppCompatActivity() {

    private lateinit var bmb: BoomMenuButton
    private lateinit var topMarginSeekText: TextView
    private lateinit var bottomMarginSeekText: TextView
    private lateinit var leftMarginSeekText: TextView
    private lateinit var rightMarginSeekText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_place_alignment)

        bmb = findViewById<BoomMenuButton>(R.id.bmb).apply {
            buttonEnum = ButtonEnum.SimpleCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_4_1
            buttonPlaceEnum = ButtonPlaceEnum.SC_4_1
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        findViewById<ListView>(R.id.list_view).apply {
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
        topMarginSeekText = findViewById(R.id.top_margin_text)
        findViewById<SeekBar>(R.id.top_margin_seek).apply {
            max = Util.dp2px(50f).toInt()
            progress = bmb.buttonTopMargin.toInt()
            topMarginSeekText.text = "Top margin = $progress pixel(s)"
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    topMarginSeekText.text = "Top margin = $progress pixel(s)"
                    bmb.buttonTopMargin = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }

    private fun initBottomMarginSeek() {
        bottomMarginSeekText = findViewById(R.id.bottom_margin_text)
        findViewById<SeekBar>(R.id.bottom_margin_seek).apply {
            max = Util.dp2px(50f).toInt()
            progress = bmb.buttonBottomMargin.toInt()
            bottomMarginSeekText.text = "Bottom margin = $progress pixel(s)"
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    bottomMarginSeekText.text = "Bottom margin = $progress pixel(s)"
                    bmb.buttonBottomMargin = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }

    private fun initLeftMarginSeek() {
        leftMarginSeekText = findViewById(R.id.left_margin_text)
        findViewById<SeekBar>(R.id.left_margin_seek).apply {
            max = Util.dp2px(50f).toInt()
            progress = bmb.buttonLeftMargin.toInt()
            leftMarginSeekText.text = "Left margin = $progress pixel(s)"
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    leftMarginSeekText.text = "Left margin = $progress pixel(s)"
                    bmb.buttonLeftMargin = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }

    private fun initRightMarginSeek() {
        rightMarginSeekText = findViewById(R.id.right_margin_text)
        findViewById<SeekBar>(R.id.right_margin_seek).apply {
            max = Util.dp2px(50f).toInt()
            progress = bmb.buttonRightMargin.toInt()
            rightMarginSeekText.text = "Right margin = $progress pixel(s)"
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    rightMarginSeekText.text = "Right margin = $progress pixel(s)"
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
