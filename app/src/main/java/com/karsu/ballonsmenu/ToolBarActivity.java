/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.karsu.ballonsmenu.BoomMenuButton;
import com.karsu.ballonsmenu.ButtonEnum;

public class ToolBarActivity extends AppCompatActivity {

    private BoomMenuButton bmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.Ham);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
            bmb.addBuilder(BuilderManager.getHamButtonBuilderWithDifferentPieceColor());
    }
}
