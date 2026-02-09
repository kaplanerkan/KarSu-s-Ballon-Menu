/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.app.databinding.ItemBinding
import com.karsu.ballonsmenu.helper.BuilderManager

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val itemResIds = intArrayOf(
        R.string.item_profile,
        R.string.item_notifications,
        R.string.item_messages,
        R.string.item_favorites,
        R.string.item_help
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.text.text = holder.itemView.context.getString(itemResIds[position])

        holder.binding.bmb1.apply {
            clearBuilders()
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }

        holder.binding.bmb2.apply {
            clearBuilders()
            repeat(piecePlaceEnum.pieceNumber()) {
                addBuilder(BuilderManager.getHamButtonBuilder())
            }
        }

        holder.binding.bmb3.apply {
            clearBuilders()
            repeat(buttonPlaceEnum.buttonNumber()) {
                addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
            }
        }
    }

    override fun getItemCount(): Int = itemResIds.size

    inner class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)
}
