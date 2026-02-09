/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.animation

import com.karsu.ballonsmenu.helper.BuilderManager
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.KarSuMenuButton
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.databinding.ActivityOrderExampleBinding
import com.karsu.ballonsmenu.animation.OrderEnum
import com.karsu.ballonsmenu.karsu_buttons.ButtonPlaceEnum
import com.karsu.ballonsmenu.piece.PiecePlaceEnum

class OrderExampleActivity : AppCompatActivity() {

    private lateinit var bmb: KarSuMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOrderExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bmb = binding.bmb.apply {
            buttonEnum = ButtonEnum.SimpleCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_9_1
            buttonPlaceEnum = ButtonPlaceEnum.SC_9_1
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        binding.listView.apply {
            adapter = ArrayAdapter(
                this@OrderExampleActivity,
                android.R.layout.simple_expandable_list_item_1,
                getData()
            )
            setOnItemClickListener { _, _, position, _ ->
                bmb.orderEnum = OrderEnum.values()[position]
            }
        }
    }

    private fun getData(): List<String> =
        OrderEnum.values()
            .dropLast(1)
            .map { it.toString() }
}
