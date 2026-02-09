/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.buttons

import com.karsu.ballonsmenu.helper.BuilderManager
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.KarSuMenuButton
import android.os.Bundle
import android.util.Pair
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.databinding.ActivitySimpleCircleButtonBinding
import com.karsu.ballonsmenu.karsu_buttons.ButtonPlaceEnum
import com.karsu.ballonsmenu.piece.PiecePlaceEnum

class SimpleCircleButtonActivity : AppCompatActivity() {

    private lateinit var bmb: KarSuMenuButton
    private val piecesAndButtons = ArrayList<Pair<PiecePlaceEnum, ButtonPlaceEnum>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySimpleCircleButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bmb = binding.bmb.apply {
            buttonEnum = ButtonEnum.SimpleCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_1
            buttonPlaceEnum = ButtonPlaceEnum.SC_1
            addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
        }

        binding.listView.apply {
            adapter = ArrayAdapter(
                this@SimpleCircleButtonActivity,
                android.R.layout.simple_expandable_list_item_1,
                BuilderManager.getCircleButtonData(piecesAndButtons)
            )
            setOnItemClickListener { _, _, position, _ ->
                piecesAndButtons[position].let { pair ->
                    bmb.piecePlaceEnum = pair.first
                    bmb.buttonPlaceEnum = pair.second
                    bmb.clearBuilders()
                    repeat(bmb.piecePlaceEnum.pieceNumber()) {
                        bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
                    }
                }
            }
        }
    }
}
