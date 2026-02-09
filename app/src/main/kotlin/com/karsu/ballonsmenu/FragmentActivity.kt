/** Created by Erkan Kaplan on 2026-02-03 */
@file:Suppress("DEPRECATION")
package com.karsu.ballonsmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R

class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        findViewById<ViewPager>(R.id.view_pager).apply {
            adapter = object : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                override fun getItem(position: Int): Fragment =
                    KarsuFragment().position(position)

                override fun getCount(): Int = 50
            }
        }
    }
}
