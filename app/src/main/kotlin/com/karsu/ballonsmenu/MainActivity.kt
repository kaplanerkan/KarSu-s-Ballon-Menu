package com.karsu.ballonsmenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.karsu.ballonsmenu.adapter.MainMenuAdapter
import com.karsu.ballonsmenu.adapter.MenuItem
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.app.databinding.ActivityMainBinding
import com.karsu.ballonsmenu.demo.animation.EaseActivity
import com.karsu.ballonsmenu.demo.animation.OrderExampleActivity
import com.karsu.ballonsmenu.demo.animation.ThreeDAnimationActivity
import com.karsu.ballonsmenu.demo.buttons.HamButtonActivity
import com.karsu.ballonsmenu.demo.buttons.SimpleCircleButtonActivity
import com.karsu.ballonsmenu.demo.buttons.TextInsideCircleButtonActivity
import com.karsu.ballonsmenu.demo.buttons.TextOutsideCircleButtonActivity
import com.karsu.ballonsmenu.demo.config.ButtonPlaceAlignmentActivity
import com.karsu.ballonsmenu.demo.config.ChangeKarSuButtonActivity
import com.karsu.ballonsmenu.demo.config.CustomPositionActivity
import com.karsu.ballonsmenu.demo.config.KarSuExampleActivity
import com.karsu.ballonsmenu.demo.config.SquareAndPieceCornerRadiusActivity
import com.karsu.ballonsmenu.demo.feature.ControlActivity
import com.karsu.ballonsmenu.demo.feature.DraggableActivity
import com.karsu.ballonsmenu.demo.feature.FadeViewsActivity
import com.karsu.ballonsmenu.demo.feature.ListenerActivity
import com.karsu.ballonsmenu.demo.feature.OrientationActivity
import com.karsu.ballonsmenu.demo.feature.ShareActivity
import com.karsu.ballonsmenu.demo.layout.ActionBarActivity
import com.karsu.ballonsmenu.demo.layout.FragmentActivity
import com.karsu.ballonsmenu.demo.layout.ListViewActivity
import com.karsu.ballonsmenu.demo.layout.RecyclerViewActivity
import com.karsu.ballonsmenu.demo.layout.ToolBarActivity
import com.karsu.ballonsmenu.helper.LocaleHelper

class MainActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.applyLocale(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainMenuAdapter(menuItems) { cls ->
                startActivity(Intent(this@MainActivity, cls))
            }
        }

        setupLanguageChips(binding)
    }

    private fun setupLanguageChips(binding: ActivityMainBinding) {
        val currentLang = LocaleHelper.getLanguage(this)
        when (currentLang) {
            "tr" -> binding.chipTr.isChecked = true
            "de" -> binding.chipDe.isChecked = true
            else -> binding.chipEn.isChecked = true
        }

        binding.chipTr.setOnClickListener { switchLanguage("tr") }
        binding.chipEn.setOnClickListener { switchLanguage("en") }
        binding.chipDe.setOnClickListener { switchLanguage("de") }
    }

    private fun switchLanguage(language: String) {
        val current = LocaleHelper.getLanguage(this)
        if (current != language) {
            LocaleHelper.setLocale(this, language)
            recreate()
        }
    }

    private val menuItems by lazy {
        listOf(
            MenuItem(R.string.menu_simple_circle_title, R.string.menu_simple_circle_desc, R.drawable.ic_circle, SimpleCircleButtonActivity::class.java),
            MenuItem(R.string.menu_text_inside_title, R.string.menu_text_inside_desc, R.drawable.ic_text_circle, TextInsideCircleButtonActivity::class.java),
            MenuItem(R.string.menu_text_outside_title, R.string.menu_text_outside_desc, R.drawable.ic_text_circle, TextOutsideCircleButtonActivity::class.java),
            MenuItem(R.string.menu_ham_title, R.string.menu_ham_desc, R.drawable.ic_hamburger, HamButtonActivity::class.java),
            MenuItem(R.string.menu_square_corner_title, R.string.menu_square_corner_desc, R.drawable.ic_rounded_square, SquareAndPieceCornerRadiusActivity::class.java),
            MenuItem(R.string.menu_animation_types_title, R.string.menu_animation_types_desc, R.drawable.ic_animation, KarSuExampleActivity::class.java),
            MenuItem(R.string.menu_alignment_title, R.string.menu_alignment_desc, R.drawable.ic_grid, ButtonPlaceAlignmentActivity::class.java),
            MenuItem(R.string.menu_order_title, R.string.menu_order_desc, R.drawable.ic_sort, OrderExampleActivity::class.java),
            MenuItem(R.string.menu_actionbar_title, R.string.menu_actionbar_desc, R.drawable.ic_toolbar, ActionBarActivity::class.java),
            MenuItem(R.string.menu_toolbar_title, R.string.menu_toolbar_desc, R.drawable.ic_toolbar, ToolBarActivity::class.java),
            MenuItem(R.string.menu_draggable_title, R.string.menu_draggable_desc, R.drawable.ic_drag, DraggableActivity::class.java),
            MenuItem(R.string.menu_ease_title, R.string.menu_ease_desc, R.drawable.ic_ease, EaseActivity::class.java),
            MenuItem(R.string.menu_listener_title, R.string.menu_listener_desc, R.drawable.ic_bell, ListenerActivity::class.java),
            MenuItem(R.string.menu_control_title, R.string.menu_control_desc, R.drawable.ic_play, ControlActivity::class.java),
            MenuItem(R.string.menu_share_title, R.string.menu_share_desc, R.drawable.ic_share, ShareActivity::class.java),
            MenuItem(R.string.menu_list_title, R.string.menu_list_desc, R.drawable.ic_list, ListViewActivity::class.java),
            MenuItem(R.string.menu_recyclerview_title, R.string.menu_recyclerview_desc, R.drawable.ic_list, RecyclerViewActivity::class.java),
            MenuItem(R.string.menu_fragment_title, R.string.menu_fragment_desc, R.drawable.ic_layers, FragmentActivity::class.java),
            MenuItem(R.string.menu_change_button_title, R.string.menu_change_button_desc, R.drawable.ic_edit, ChangeKarSuButtonActivity::class.java),
            MenuItem(R.string.menu_3d_title, R.string.menu_3d_desc, R.drawable.ic_3d, ThreeDAnimationActivity::class.java),
            MenuItem(R.string.menu_custom_pos_title, R.string.menu_custom_pos_desc, R.drawable.ic_target, CustomPositionActivity::class.java),
            MenuItem(R.string.menu_fade_title, R.string.menu_fade_desc, R.drawable.ic_visibility, FadeViewsActivity::class.java),
            MenuItem(R.string.menu_orientation_title, R.string.menu_orientation_desc, R.drawable.ic_rotate, OrientationActivity::class.java)
        )
    }
}
