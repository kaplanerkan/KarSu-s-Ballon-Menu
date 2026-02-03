/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceEnum;
import com.karsu.ballonsmenu.BoomMenuButton;
import com.karsu.ballonsmenu.ButtonEnum;
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum;

public class OrientationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);

        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.SimpleCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_9_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_9_1);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
            bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder());

        // android:configChanges="keyboardHidden|orientation|screenSize" in AndroidManifest.xml
        bmb.setOrientationAdaptable(true);
    }
}
