/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.BoomMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum

class ActionBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_bar)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(false)
            setDisplayShowTitleEnabled(false)

            val actionBarView = LayoutInflater.from(this@ActionBarActivity)
                .inflate(R.layout.custom_actionbar, null)

            actionBarView.findViewById<TextView>(R.id.title_text).text = "ActionBar"

            customView = actionBarView
            setDisplayShowCustomEnabled(true)

            (actionBarView.parent as? Toolbar)?.setContentInsetsAbsolute(0, 0)

            actionBarView.findViewById<BoomMenuButton>(R.id.action_bar_left_bmb).apply {
                buttonEnum = ButtonEnum.TextOutsideCircle
                piecePlaceEnum = PiecePlaceEnum.DOT_9_1
                buttonPlaceEnum = ButtonPlaceEnum.SC_9_1
                repeat(piecePlaceEnum.pieceNumber()) {
                    addBuilder(BuilderManager.getTextOutsideCircleButtonBuilderWithDifferentPieceColor())
                }
            }

            actionBarView.findViewById<BoomMenuButton>(R.id.action_bar_right_bmb).apply {
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
