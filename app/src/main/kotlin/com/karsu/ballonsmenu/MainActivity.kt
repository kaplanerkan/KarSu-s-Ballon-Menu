/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOf(
            R.id.simple_circle_button_example,
            R.id.text_inside_circle_button_example,
            R.id.text_outside_circle_button_example,
            R.id.ham_button_example,
            R.id.square_and_piece_corner_radius_example,
            R.id.boom_example,
            R.id.button_place_alignment_example,
            R.id.order_example,
            R.id.actionbar_example,
            R.id.tool_bar_example,
            R.id.draggable_example,
            R.id.ease_example,
            R.id.listener_example,
            R.id.control_example,
            R.id.share_example,
            R.id.list_example,
            R.id.recycler_view_example,
            R.id.fragment_example,
            R.id.change_boom_button_example,
            R.id.three_d_animation_example,
            R.id.custom_position_example,
            R.id.fade_views_example,
            R.id.orientation_example
        ).forEach { id ->
            findViewById<View>(id).setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
        val activityClass: Class<*>? = when (v.id) {
            R.id.simple_circle_button_example -> SimpleCircleButtonActivity::class.java
            R.id.text_inside_circle_button_example -> TextInsideCircleButtonActivity::class.java
            R.id.text_outside_circle_button_example -> TextOutsideCircleButtonActivity::class.java
            R.id.ham_button_example -> HamButtonActivity::class.java
            R.id.square_and_piece_corner_radius_example -> SquareAndPieceCornerRadiusActivity::class.java
            R.id.boom_example -> BoomExampleActivity::class.java
            R.id.button_place_alignment_example -> ButtonPlaceAlignmentActivity::class.java
            R.id.order_example -> OrderExampleActivity::class.java
            R.id.tool_bar_example -> ToolBarActivity::class.java
            R.id.actionbar_example -> ActionBarActivity::class.java
            R.id.draggable_example -> DraggableActivity::class.java
            R.id.ease_example -> EaseActivity::class.java
            R.id.listener_example -> ListenerActivity::class.java
            R.id.control_example -> ControlActivity::class.java
            R.id.share_example -> ShareActivity::class.java
            R.id.list_example -> ListViewActivity::class.java
            R.id.recycler_view_example -> RecyclerViewActivity::class.java
            R.id.fragment_example -> FragmentActivity::class.java
            R.id.change_boom_button_example -> ChangeBoomButtonActivity::class.java
            R.id.three_d_animation_example -> ThreeDAnimationActivity::class.java
            R.id.custom_position_example -> CustomPositionActivity::class.java
            R.id.fade_views_example -> FadeViewsActivity::class.java
            R.id.orientation_example -> OrientationActivity::class.java
            else -> null
        }
        activityClass?.let { startActivity(it) }
    }

    private fun startActivity(cls: Class<*>) {
        startActivity(Intent(this, cls))
    }
}
