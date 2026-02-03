/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.karsu.ballonsmenu.BoomMenuButton

class BMBFragment : Fragment() {

    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bmb, container, false).also { fragment ->
            fragment.findViewById<TextView>(R.id.text).apply {
                text = position.toString()
            }

            fragment.findViewById<BoomMenuButton>(R.id.bmb1).also { bmb ->
                repeat(bmb.piecePlaceEnum.pieceNumber()) {
                    bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
                }
            }

            fragment.findViewById<BoomMenuButton>(R.id.bmb2).also { bmb ->
                repeat(bmb.piecePlaceEnum.pieceNumber()) {
                    bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
                }
            }
        }
    }

    fun position(position: Int): Fragment {
        this.position = position
        return this
    }
}
