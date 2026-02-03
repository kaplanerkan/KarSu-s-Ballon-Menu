/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.BoomButtons.BoomButton
import com.karsu.ballonsmenu.BoomButtons.HamButton
import com.karsu.ballonsmenu.BoomMenuButton
import com.karsu.ballonsmenu.OnBoomListenerAdapter

class ChangeBoomButtonActivity : AppCompatActivity() {

    private lateinit var bmb: BoomMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_boom_button)

        bmb = findViewById<BoomMenuButton>(R.id.bmb).apply {
            addBuilder(BuilderManager.getHamButtonBuilder("Change Text", "..."))
            addBuilder(
                BuilderManager.getHamButtonBuilder("Change Image", "...")
                    .normalImageRes(R.drawable.elephant)
            )
            addBuilder(
                BuilderManager.getHamButtonBuilder("Change Color", "...")
                    .normalColorRes(R.color.colorPrimary)
            )
            addBuilder(BuilderManager.getHamButtonBuilder("Change Piece Color", "..."))
            addBuilder(
                BuilderManager.getHamButtonBuilder("Change Unable", "...")
                    .unableColor(Color.BLUE)
                    .unableImageRes(R.drawable.butterfly)
                    .unableText("Unable!")
            )

            setOnBoomListener(object : OnBoomListenerAdapter() {
                override fun onClicked(index: Int, boomButton: BoomButton) {
                    super.onClicked(index, boomButton)
                    changeBoomButton(index)
                }
            })
        }
    }

    private fun changeBoomButton(index: Int) {
        // From version 2.0.9, BMB supports a new feature to change contents in boom-button
        // by changing contents in the corresponding builder.
        // Please notice that not every method supports this feature. Only the method whose comment
        // contains the "Synchronicity" tag supports.
        // For more details, check:
        // https://github.com/Nightonke/BoomMenu/wiki/Change-Boom-Buttons-Dynamically
        val builder = bmb.getBuilder(index) as HamButton.Builder

        when (index) {
            0 -> builder.apply {
                normalText("Changed!")
                highlightedText("Highlighted, changed!")
                subNormalText("Sub-text, changed!")
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
