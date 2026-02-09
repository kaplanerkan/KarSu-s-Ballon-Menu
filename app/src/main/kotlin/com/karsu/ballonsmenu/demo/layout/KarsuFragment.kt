/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.layout

import com.karsu.ballonsmenu.helper.BuilderManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.karsu.ballonsmenu.app.databinding.FragmentBmbBinding

class KarsuFragment : Fragment() {

    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBmbBinding.inflate(inflater, container, false)

        binding.text.text = position.toString()

        binding.bmb1.also { bmb ->
            repeat(bmb.piecePlaceEnum.pieceNumber()) {
                bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        binding.bmb2.also { bmb ->
            repeat(bmb.piecePlaceEnum.pieceNumber()) {
                bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        return binding.root
    }

    fun position(position: Int): Fragment {
        this.position = position
        return this
    }
}
