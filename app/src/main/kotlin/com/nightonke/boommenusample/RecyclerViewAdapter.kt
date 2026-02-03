package com.nightonke.boommenusample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nightonke.boommenu.BoomMenuButton

/**
 * Created by Weiping Huang at 02:25 on 16/12/6
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 */
class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, null, false).apply {
            layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = "No. $position"

        holder.bmb1.apply {
            clearBuilders()
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        holder.bmb2.apply {
            clearBuilders()
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getHamButtonBuilder())
            }
        }

        holder.bmb3.apply {
            clearBuilders()
            repeat(buttonPlaceEnum.buttonNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }
    }

    override fun getItemCount(): Int = 1000

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.text)
        val bmb1: BoomMenuButton = view.findViewById(R.id.bmb1)
        val bmb2: BoomMenuButton = view.findViewById(R.id.bmb2)
        val bmb3: BoomMenuButton = view.findViewById(R.id.bmb3)
    }
}
