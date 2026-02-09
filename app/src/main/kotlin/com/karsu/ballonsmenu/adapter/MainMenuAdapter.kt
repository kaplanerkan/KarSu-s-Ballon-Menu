package com.karsu.ballonsmenu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.karsu.ballonsmenu.app.databinding.ItemMainMenuBinding

data class MenuItem(
    @StringRes val titleRes: Int,
    @StringRes val descRes: Int,
    @DrawableRes val icon: Int,
    val activityClass: Class<*>
)

class MainMenuAdapter(
    private val items: List<MenuItem>,
    private val onClick: (Class<*>) -> Unit
) : RecyclerView.Adapter<MainMenuAdapter.ViewHolder>() {

    private val iconBgColors = intArrayOf(
        0xFF009688.toInt(),
        0xFF00ACC1.toInt(),
        0xFF26A69A.toInt(),
        0xFF00897B.toInt(),
        0xFF0097A7.toInt(),
        0xFF00796B.toInt(),
        0xFF4DB6AC.toInt(),
        0xFF80CBC4.toInt()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val ctx = holder.itemView.context
        holder.binding.title.text = ctx.getString(item.titleRes)
        holder.binding.description.text = ctx.getString(item.descRes)
        holder.binding.icon.setImageResource(item.icon)

        val bgDrawable = holder.binding.iconBg.background.mutate()
        bgDrawable.setTint(iconBgColors[position % iconBgColors.size])
        holder.binding.iconBg.background = bgDrawable

        holder.binding.card.setOnClickListener { onClick(item.activityClass) }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: ItemMainMenuBinding) : RecyclerView.ViewHolder(binding.root)
}
