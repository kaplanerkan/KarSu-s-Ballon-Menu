/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.KarSuButtons.KarSuButton
import com.karsu.ballonsmenu.KarSuButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.KarSuButtons.SimpleCircleButton
import com.karsu.ballonsmenu.KarSuMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.OnKarSuListener
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum

class ListenerActivity : AppCompatActivity() {

    private lateinit var textViewForAnimation: TextView
    private lateinit var textViewForButton: TextView
    private lateinit var bmb: KarSuMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listener)

        textViewForButton = findViewById(R.id.text_for_button)
        textViewForAnimation = findViewById(R.id.text_for_animation)

        bmb = findViewById<KarSuMenuButton>(R.id.bmb).apply {
            buttonEnum = ButtonEnum.SimpleCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_6_3
            buttonPlaceEnum = ButtonPlaceEnum.SC_6_3
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder()
            }
        }

        // Use OnKarSuListener to listen all methods
        bmb.onKarSuListener = object : OnKarSuListener {
            override fun onKarSuButtonClick(index: Int) {
                // If you have implement listeners for boom-buttons in builders,
                // then you shouldn't add any listener here for duplicate callbacks.
            }

            override fun onBackgroundClick() {
                textViewForAnimation.text = "Click background!!!"
            }

            override fun onKarSuWillHide() {
                Log.d("BMB", "onKarSuWillHide: ${bmb.isKarSued()} ${bmb.isReKarSued()}")
                textViewForAnimation.text = "Will RE-BOOM!!!"
            }

            override fun onKarSuDidHide() {
                Log.d("BMB", "onKarSuDidHide: ${bmb.isKarSued()} ${bmb.isReKarSued()}")
                textViewForAnimation.text = "Did RE-BOOM!!!"
            }

            override fun onKarSuWillShow() {
                Log.d("BMB", "onKarSuWillShow: ${bmb.isKarSued()} ${bmb.isReKarSued()}")
                textViewForAnimation.text = "Will BOOM!!!"
            }

            override fun onKarSuDidShow() {
                Log.d("BMB", "onKarSuDidShow: ${bmb.isKarSued()} ${bmb.isReKarSued()}")
                textViewForAnimation.text = "Did BOOM!!!"
            }
        }
    }

    private fun addBuilder() {
        bmb.addBuilder(
            SimpleCircleButton.Builder()
                .normalImageRes(BuilderManager.getImageResource())
                .listener { index ->
                    textViewForButton.text = "No.$index boom-button is clicked!"
                }
        )
    }
}
