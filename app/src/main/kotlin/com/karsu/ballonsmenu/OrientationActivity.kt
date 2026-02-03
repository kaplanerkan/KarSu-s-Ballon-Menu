/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.BoomMenuButton
import com.karsu.ballonsmenu.ButtonEnum
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum

class OrientationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orientation)

        findViewById<BoomMenuButton>(R.id.bmb).apply {
            buttonEnum = ButtonEnum.SimpleCircle
            piecePlaceEnum = PiecePlaceEnum.DOT_9_1
            buttonPlaceEnum = ButtonPlaceEnum.SC_9_1
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
            // android:configChanges="keyboardHidden|orientation|screenSize" in AndroidManifest.xml
            isOrientationAdaptable = true
        }
    }
}
