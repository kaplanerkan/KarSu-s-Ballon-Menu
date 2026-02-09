/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.feature

import com.karsu.ballonsmenu.helper.BuilderManager
import com.karsu.ballonsmenu.KarSuMenuButton
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.app.databinding.ActivityControlBinding

class ControlActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bmb: KarSuMenuButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bmb = binding.bmb.apply {
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        listOf(
            binding.karsu,
            binding.rekarsu,
            binding.karsuImmediately,
            binding.rekarsuImmediately
        ).forEach { it.setOnClickListener(this) }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.karsu -> bmb.karsu()
            R.id.rekarsu -> bmb.rekarsu()
            R.id.karsu_immediately -> bmb.karsuImmediately()
            R.id.rekarsu_immediately -> bmb.rekarsuImmediately()
        }
    }
}
