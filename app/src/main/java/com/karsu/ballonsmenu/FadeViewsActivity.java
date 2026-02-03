/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.karsu.ballonsmenu.BoomMenuButton;

public class FadeViewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fade_views);

        BoomMenuButton bmb1 = (BoomMenuButton) findViewById(R.id.bmb1);
        for (int i = 0; i < bmb1.getPiecePlaceEnum().pieceNumber(); i++)
            bmb1.addBuilder(BuilderManager.getSimpleCircleButtonBuilder());

        BoomMenuButton bmb2 = (BoomMenuButton) findViewById(R.id.bmb2);
        for (int i = 0; i < bmb2.getPiecePlaceEnum().pieceNumber(); i++)
            bmb2.addBuilder(BuilderManager.getHamButtonBuilderWithDifferentPieceColor());
    }
}
