/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.layout

import com.karsu.ballonsmenu.helper.BuilderManager
import com.karsu.ballonsmenu.ButtonEnum
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.app.databinding.ActivityActionBarBinding
import com.karsu.ballonsmenu.app.databinding.CustomActionbarBinding
import com.karsu.ballonsmenu.karsu_buttons.ButtonPlaceEnum
import com.karsu.ballonsmenu.piece.PiecePlaceEnum

class ActionBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityActionBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(false)
            setDisplayShowTitleEnabled(false)

            val actionBarBinding = CustomActionbarBinding.inflate(layoutInflater)

            actionBarBinding.titleText.text = getString(R.string.title_actionbar)

            customView = actionBarBinding.root
            setDisplayShowCustomEnabled(true)

            (actionBarBinding.root.parent as? Toolbar)?.setContentInsetsAbsolute(0, 0)

            actionBarBinding.actionBarLeftBmb.apply {
                buttonEnum = ButtonEnum.TextOutsideCircle
                piecePlaceEnum = PiecePlaceEnum.DOT_9_1
                buttonPlaceEnum = ButtonPlaceEnum.SC_9_1
                repeat(piecePlaceEnum.pieceNumber()) {
                    addBuilder(BuilderManager.getTextOutsideCircleButtonBuilderWithDifferentPieceColor())
                }
            }

            actionBarBinding.actionBarRightBmb.apply {
                buttonEnum = ButtonEnum.Ham
                piecePlaceEnum = PiecePlaceEnum.HAM_4
                buttonPlaceEnum = ButtonPlaceEnum.HAM_4
                repeat(piecePlaceEnum.pieceNumber()) {
                    addBuilder(BuilderManager.getHamButtonBuilderWithDifferentPieceColor())
                }
            }
        }
    }
}
