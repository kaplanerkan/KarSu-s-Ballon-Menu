/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.feature

import com.karsu.ballonsmenu.helper.BuilderManager
import com.karsu.ballonsmenu.KarSuMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.OnKarSuListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.app.databinding.ActivityListenerBinding
import com.karsu.ballonsmenu.karsu_buttons.ButtonPlaceEnum
import com.karsu.ballonsmenu.karsu_buttons.SimpleCircleButton
import com.karsu.ballonsmenu.piece.PiecePlaceEnum

class ListenerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListenerBinding
    private lateinit var bmb: KarSuMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListenerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bmb = binding.bmb.apply {
            buttonEnum = ButtonEnum.SimpleCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_6_3
            buttonPlaceEnum = ButtonPlaceEnum.SC_6_3
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder()
            }
        }

        bmb.onKarSuListener = object : OnKarSuListener {
            override fun onKarSuButtonClick(index: Int) {}

            override fun onBackgroundClick() {
                binding.textForAnimation.text = getString(R.string.click_background)
            }

            override fun onKarSuWillHide() {
                Log.d("BMB", "onKarSuWillHide: ${bmb.isKarSued()} ${bmb.isReKarSued()}")
                binding.textForAnimation.text = getString(R.string.will_rekarsu)
            }

            override fun onKarSuDidHide() {
                Log.d("BMB", "onKarSuDidHide: ${bmb.isKarSued()} ${bmb.isReKarSued()}")
                binding.textForAnimation.text = getString(R.string.did_rekarsu)
            }

            override fun onKarSuWillShow() {
                Log.d("BMB", "onKarSuWillShow: ${bmb.isKarSued()} ${bmb.isReKarSued()}")
                binding.textForAnimation.text = getString(R.string.will_karsu)
            }

            override fun onKarSuDidShow() {
                Log.d("BMB", "onKarSuDidShow: ${bmb.isKarSued()} ${bmb.isReKarSued()}")
                binding.textForAnimation.text = getString(R.string.did_karsu)
            }
        }
    }

    private fun addBuilder() {
        bmb.addBuilder(
            SimpleCircleButton.Builder()
                .normalImageRes(BuilderManager.getImageResource())
                .listener { index ->
                    binding.textForButton.text = getString(R.string.button_clicked_format, index)
                }
        )
    }
}
