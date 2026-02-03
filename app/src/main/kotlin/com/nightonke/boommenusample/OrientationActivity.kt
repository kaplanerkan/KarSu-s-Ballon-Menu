package com.nightonke.boommenusample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.BoomMenuButton
import com.nightonke.boommenu.ButtonEnum
import com.nightonke.boommenu.Piece.PiecePlaceEnum

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
