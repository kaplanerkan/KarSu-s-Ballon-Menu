/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.config

import com.karsu.ballonsmenu.helper.BuilderManager
import com.karsu.ballonsmenu.KarSuMenuButton
import com.karsu.ballonsmenu.OnKarSuListenerAdapter
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.app.databinding.ActivityChangeKarsuButtonBinding
import com.karsu.ballonsmenu.karsu_buttons.HamButton

class ChangeKarSuButtonActivity : AppCompatActivity() {

    private lateinit var bmb: KarSuMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChangeKarsuButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bmb = binding.bmb.apply {
            addBuilder(BuilderManager.getHamButtonBuilder(getString(R.string.change_text), getString(R.string.ellipsis)))
            addBuilder(
                BuilderManager.getHamButtonBuilder(getString(R.string.change_image), getString(R.string.ellipsis))
                    .normalImageRes(R.drawable.elephant)
            )
            addBuilder(
                BuilderManager.getHamButtonBuilder(getString(R.string.change_color), getString(R.string.ellipsis))
                    .normalColorRes(R.color.colorPrimary)
            )
            addBuilder(BuilderManager.getHamButtonBuilder(getString(R.string.change_piece_color), getString(R.string.ellipsis)))
            addBuilder(
                BuilderManager.getHamButtonBuilder(getString(R.string.change_unable), getString(R.string.ellipsis))
                    .unableColor(Color.BLUE)
                    .unableImageRes(R.drawable.butterfly)
                    .unableText(getString(R.string.unable_text))
            )

            onKarSuListener = object : OnKarSuListenerAdapter() {
                override fun onKarSuButtonClick(index: Int) {
                    super.onKarSuButtonClick(index)
                    changeKarSuButton(index)
                }
            }
        }
    }

    private fun changeKarSuButton(index: Int) {
        val builder = bmb.getBuilder(index) as HamButton.Builder

        when (index) {
            0 -> builder.apply {
                normalText(getString(R.string.changed_text))
                highlightedText(getString(R.string.highlighted_changed))
                subNormalText(getString(R.string.sub_text_changed))
                normalTextColor(Color.YELLOW)
                highlightedTextColorRes(R.color.colorPrimary)
                subNormalTextColor(Color.BLACK)
            }
            1 -> builder.apply {
                normalImageRes(R.drawable.bat)
                highlightedImageRes(R.drawable.bear)
            }
            2 -> builder.normalColorRes(R.color.colorAccent)
            3 -> builder.pieceColor(Color.WHITE)
            4 -> builder.unable(true)
        }
    }
}
