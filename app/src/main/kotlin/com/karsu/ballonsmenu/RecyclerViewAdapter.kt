/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.KarSuMenuButton

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val items = listOf(
        "Profil Ayarları",
        "Bildirimler",
        "Mesajlar",
        "Favoriler",
        "Yardım"
    )

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
        holder.text.text = items[position]

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

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.text)
        val bmb1: KarSuMenuButton = view.findViewById(R.id.bmb1)
        val bmb2: KarSuMenuButton = view.findViewById(R.id.bmb2)
        val bmb3: KarSuMenuButton = view.findViewById(R.id.bmb3)
    }
}
