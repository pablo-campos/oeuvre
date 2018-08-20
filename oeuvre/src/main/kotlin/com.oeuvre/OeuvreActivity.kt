package com.oeuvre

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.oeuvre.adapter.OeuvreAdapter
import com.oeuvre.model.Work

class OeuvreActivity : AppCompatActivity() {


	private lateinit var recyclerView: RecyclerView
	private lateinit var viewAdapter: RecyclerView.Adapter<*>
	private lateinit var viewManager: RecyclerView.LayoutManager


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_oeuvre)

		// Add projects here:
		var work1 = Work("Title 1", "Description 1")
		var work2 : Work
		work2 = Work("Title 2", "Description 2")
		work2.preview = "http://goo.gl/gEgYUd"
		var work3 : Work = Work("Title 3", "Description 3")

		val works = listOf(work1, work2, work3)

		viewManager = LinearLayoutManager(this)
		viewAdapter = OeuvreAdapter(works)

		recyclerView = findViewById<RecyclerView>(R.id.works_list).apply {

			// Use this setting to improve performance if you know that changes
			// in content do not change the layout size of the RecyclerView
			setHasFixedSize(true)

			// use a linear layout manager
			layoutManager = viewManager

			// specify an viewAdapter (see also next example)
			adapter = viewAdapter
		}
	}

}
