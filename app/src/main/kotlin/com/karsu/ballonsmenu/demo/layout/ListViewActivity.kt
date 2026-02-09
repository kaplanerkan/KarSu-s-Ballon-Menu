/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.layout

import com.karsu.ballonsmenu.helper.BuilderManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.app.databinding.ActivityListViewBinding
import com.karsu.ballonsmenu.app.databinding.ItemBinding

class ListViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf(
            getString(R.string.item_profile),
            getString(R.string.item_notifications),
            getString(R.string.item_messages),
            getString(R.string.item_favorites),
            getString(R.string.item_help)
        )

        binding.listView.apply {
            adapter = MyAdapter(items)
            setOnItemClickListener { _, view, _, _ ->
                val itemBinding = ItemBinding.bind(view)
                itemBinding.bmb1.karsu()
            }
        }
    }

    private class MyAdapter(private val items: List<String>) : BaseAdapter() {

        override fun getCount(): Int = items.size

        override fun getItem(position: Int): Any = items[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val itemBinding: ItemBinding

            if (convertView == null) {
                itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context))
                itemBinding.root.tag = itemBinding
            } else {
                itemBinding = convertView.tag as ItemBinding
            }

            itemBinding.text.text = items[position]

            itemBinding.bmb1.apply {
                clearBuilders()
                repeat(piecePlaceEnum.pieceNumber()) {
                    addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
                }
            }

            itemBinding.bmb2.apply {
                clearBuilders()
                repeat(piecePlaceEnum.pieceNumber()) {
                    addBuilder(BuilderManager.getHamButtonBuilder())
                }
            }

            itemBinding.bmb3.apply {
                clearBuilders()
                repeat(buttonPlaceEnum.buttonNumber()) {
                    addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
                }
            }

            return itemBinding.root
        }
    }
}
