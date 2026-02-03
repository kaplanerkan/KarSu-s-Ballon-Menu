/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.BoomButtons.BoomButton
import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.BoomButtons.SimpleCircleButton
import com.karsu.ballonsmenu.BoomMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.OnBoomListener
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum

class ListenerActivity : AppCompatActivity() {

    private lateinit var textViewForAnimation: TextView
    private lateinit var textViewForButton: TextView
    private lateinit var bmb: BoomMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listener)

        textViewForButton = findViewById(R.id.text_for_button)
        textViewForAnimation = findViewById(R.id.text_for_animation)

        bmb = findViewById<BoomMenuButton>(R.id.bmb).apply {
            buttonEnum = ButtonEnum.SimpleCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_6_3
            buttonPlaceEnum = ButtonPlaceEnum.SC_6_3
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder()
            }
        }

        // Use OnBoomListener to listen all methods
        bmb.onBoomListener = object : OnBoomListener {
            override fun onClicked(index: Int, boomButton: BoomButton?) {
                // If you have implement listeners for boom-buttons in builders,
                // then you shouldn't add any listener here for duplicate callbacks.
            }

            override fun onBackgroundClick() {
                textViewForAnimation.text = "Click background!!!"
            }

            override fun onBoomWillHide() {
                Log.d("BMB", "onBoomWillHide: ${bmb.isBoomed} ${bmb.isReBoomed}")
                textViewForAnimation.text = "Will RE-BOOM!!!"
            }

            override fun onBoomDidHide() {
                Log.d("BMB", "onBoomDidHide: ${bmb.isBoomed} ${bmb.isReBoomed}")
                textViewForAnimation.text = "Did RE-BOOM!!!"
            }

            override fun onBoomWillShow() {
                Log.d("BMB", "onBoomWillShow: ${bmb.isBoomed} ${bmb.isReBoomed}")
                textViewForAnimation.text = "Will BOOM!!!"
            }

            override fun onBoomDidShow() {
                Log.d("BMB", "onBoomDidShow: ${bmb.isBoomed} ${bmb.isReBoomed}")
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
