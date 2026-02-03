/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.karsu.ballonsmenu.BoomButtons.BoomButton;
import com.karsu.ballonsmenu.BoomButtons.ButtonPlaceEnum;
import com.karsu.ballonsmenu.BoomButtons.OnBMClickListener;
import com.karsu.ballonsmenu.BoomButtons.SimpleCircleButton;
import com.karsu.ballonsmenu.BoomMenuButton;
import com.karsu.ballonsmenu.ButtonEnum;
import com.karsu.ballonsmenu.OnBoomListener;
import com.karsu.ballonsmenu.OnBoomListenerAdapter;
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum;

public class ListenerActivity extends AppCompatActivity {

    private TextView textViewForAnimation;
    private TextView textViewForButton;
    private BoomMenuButton bmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener);

        textViewForButton = (TextView) findViewById(R.id.text_for_button);
        textViewForAnimation = (TextView) findViewById(R.id.text_for_animation);

        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.SimpleCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_6_3);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_6_3);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) addBuilder();

        // Use OnBoomListenerAdapter to listen part of methods
        bmb.setOnBoomListener(new OnBoomListenerAdapter() {
            @Override
            public void onBoomWillShow() {
                super.onBoomWillShow();
                // logic here
            }
        });

        // Use OnBoomListener to listen all methods
        bmb.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                // If you have implement listeners for boom-buttons in builders,
                // then you shouldn't add any listener here for duplicate callbacks.
            }

            @Override
            public void onBackgroundClick() {
                textViewForAnimation.setText("Click background!!!");
            }

            @Override
            public void onBoomWillHide() {
                Log.d("BMB", "onBoomWillHide: " + bmb.isBoomed() + " " + bmb.isReBoomed());
                textViewForAnimation.setText("Will RE-BOOM!!!");
            }

            @Override
            public void onBoomDidHide() {
                Log.d("BMB", "onBoomDidHide: " + bmb.isBoomed() + " " + bmb.isReBoomed());
                textViewForAnimation.setText("Did RE-BOOM!!!");
            }

            @Override
            public void onBoomWillShow() {
                Log.d("BMB", "onBoomWillShow: " + bmb.isBoomed() + " " + bmb.isReBoomed());
                textViewForAnimation.setText("Will BOOM!!!");
            }

            @Override
            public void onBoomDidShow() {
                Log.d("BMB", "onBoomDidShow: " + bmb.isBoomed() + " " + bmb.isReBoomed());
                textViewForAnimation.setText("Did BOOM!!!");
            }
        });
    }

    private void addBuilder() {
        bmb.addBuilder(new SimpleCircleButton.Builder()
                .normalImageRes(BuilderManager.getImageResource())
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        textViewForButton.setText("No." + index + " boom-button is clicked!");
                    }
                }));
    }
}
