package com.nightonke.boommenusample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nightonke.boommenu.BoomMenuButton

class ListViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        findViewById<ListView>(R.id.list_view).apply {
            adapter = MyAdapter()
            setOnItemClickListener { _, view, _, _ ->
                view.findViewById<BoomMenuButton>(R.id.bmb1).boom()
            }
        }
    }

    private class MyAdapter : BaseAdapter() {

        override fun getCount(): Int = 1000

        override fun getItem(position: Int): Any? = null

        override fun getItemId(position: Int): Long = 0

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val viewHolder: ViewHolder
            val view: View

            if (convertView == null) {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item, null)
                viewHolder = ViewHolder(
                    text = view.findViewById(R.id.text),
                    bmb1 = view.findViewById(R.id.bmb1),
                    bmb2 = view.findViewById(R.id.bmb2),
                    bmb3 = view.findViewById(R.id.bmb3)
                )
                view.tag = viewHolder
            } else {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }

            viewHolder.text.text = "No. $position"

            viewHolder.bmb1.apply {
                clearBuilders()
                repeat(piecePlaceEnum.pieceNumber()) {
                    addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
                }
            }

            viewHolder.bmb2.apply {
                clearBuilders()
                repeat(piecePlaceEnum.pieceNumber()) {
                    addBuilder(BuilderManager.getHamButtonBuilder())
                }
            }

            viewHolder.bmb3.apply {
                clearBuilders()
                repeat(buttonPlaceEnum.buttonNumber()) {
                    addBuilder(BuilderManager.getSimpleCircleButtonBuilder())
                }
            }

            return view
        }

        private data class ViewHolder(
            val text: TextView,
            val bmb1: BoomMenuButton,
            val bmb2: BoomMenuButton,
            val bmb3: BoomMenuButton
        )
    }
}
