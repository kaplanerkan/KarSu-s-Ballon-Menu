/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.demo.layout

import com.karsu.ballonsmenu.adapter.RecyclerViewAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.karsu.ballonsmenu.app.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
            adapter = RecyclerViewAdapter()
        }
    }
}
