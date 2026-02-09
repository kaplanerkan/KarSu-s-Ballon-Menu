/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.animation

import com.karsu.ballonsmenu.helper.BuilderManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.app.databinding.ActivityThreeDAnimationBinding

class ThreeDAnimationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThreeDAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThreeDAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bmb1.also { bmb ->
            repeat(bmb.piecePlaceEnum.pieceNumber()) {
                bmb.addBuilder(BuilderManager.getSquareSimpleCircleButtonBuilder())
            }
        }

        binding.bmb2.also { bmb ->
            repeat(bmb.piecePlaceEnum.pieceNumber()) {
                bmb.addBuilder(BuilderManager.getHamButtonBuilderWithDifferentPieceColor())
            }
        }

        binding.bmb3.also { bmb ->
            repeat(bmb.piecePlaceEnum.pieceNumber()) {
                bmb.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder())
            }
        }

        binding.threeDAnimationSwitch.apply {
            setOnCheckedChangeListener { _, isChecked ->
                binding.bmb1.isUse3DTransformAnimation = isChecked
                binding.bmb2.isUse3DTransformAnimation = isChecked
                binding.bmb3.isUse3DTransformAnimation = isChecked
            }
            isChecked = true
        }

        binding.durationSeek.apply {
            value = 300f
            addOnChangeListener { _, value, _ ->
                val duration = value.toLong()
                binding.durationText.text = getString(R.string.show_hide_duration_format, duration.toInt())
                binding.bmb1.duration = duration
                binding.bmb2.duration = duration
                binding.bmb3.duration = duration
            }
            binding.durationText.text = getString(R.string.show_hide_duration_format, value.toInt())
        }
    }
}
