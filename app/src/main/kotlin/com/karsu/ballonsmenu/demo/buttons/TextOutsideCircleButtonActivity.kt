/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.buttons

import com.karsu.ballonsmenu.helper.BuilderManager
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.KarSuMenuButton
import android.os.Bundle
import android.util.Pair
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.databinding.ActivityTextOutsideCircleButtonBinding
import com.karsu.ballonsmenu.karsu_buttons.ButtonPlaceEnum
import com.karsu.ballonsmenu.piece.PiecePlaceEnum

class TextOutsideCircleButtonActivity : AppCompatActivity() {

    private lateinit var bmb: KarSuMenuButton
    private val piecesAndButtons = ArrayList<Pair<PiecePlaceEnum, ButtonPlaceEnum>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTextOutsideCircleButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bmb = binding.bmb.apply {
            buttonEnum = ButtonEnum.TextOutsideCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_1
            buttonPlaceEnum = ButtonPlaceEnum.SC_1
            addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder())
        }

        binding.listView.apply {
            adapter = ArrayAdapter(
                this@TextOutsideCircleButtonActivity,
                android.R.layout.simple_expandable_list_item_1,
                BuilderManager.getCircleButtonData(piecesAndButtons)
            )
            setOnItemClickListener { _, _, position, _ ->
                bmb.piecePlaceEnum = piecesAndButtons[position].first
                bmb.buttonPlaceEnum = piecesAndButtons[position].second
                bmb.clearBuilders()
                repeat(bmb.piecePlaceEnum.pieceNumber()) {
                    bmb.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder())
                }
            }
        }
    }
}
