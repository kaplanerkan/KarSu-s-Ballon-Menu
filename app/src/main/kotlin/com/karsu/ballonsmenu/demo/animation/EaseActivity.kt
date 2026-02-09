/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.animation

import com.karsu.ballonsmenu.helper.BuilderManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.databinding.ActivityEaseBinding

class EaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listOf(
            binding.bmb1, binding.bmb2, binding.bmb3,
            binding.bmb4, binding.bmb5, binding.bmb6,
            binding.bmb7, binding.bmb8, binding.bmb9
        ).forEach { bmb ->
            bmb.apply {
                repeat(piecePlaceEnum.pieceNumber()) {
                    addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
                }
            }
        }
    }
}
