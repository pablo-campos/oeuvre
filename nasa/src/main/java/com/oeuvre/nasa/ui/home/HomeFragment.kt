package com.oeuvre.nasa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.oeuvre.nasa.R

class HomeFragment : Fragment() {


	private lateinit var recyclerView: RecyclerView
	private lateinit var viewAdapter: RecyclerView.Adapter<*>
	private lateinit var viewManager: RecyclerView.LayoutManager
	private lateinit var homeViewModel: HomeViewModel

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

		homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

		val root = inflater.inflate(R.layout.fragment_home, container, false)
		val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)







		homeViewModel.text.observe(viewLifecycleOwner, Observer {
			// TODO update: textView.text = it
		})

		return root




		// Add projects here:
/*		val work1 = Work("Title 1", "Description 1")
		val work2 : Work
		work2 = Work("Title 2", "Description 2")
		work2.preview = "http://goo.gl/gEgYUd"
		val work3 : Work = Work("Title 3", "Description 3")

		val works = listOf(work1, work2, work3)

		val oeuvreClickListener = OeuvreClickListener {
			Toast.makeText(this, "Testing", Toast.LENGTH_SHORT).show()
		}

		oeuvreClickListener.let {

		}

		viewManager = LinearLayoutManager(this)
		viewAdapter = OeuvreAdapter(works, oeuvreClickListener)

		recyclerView = findViewById<RecyclerView>(R.id.works_list).apply {

			// Use this setting to improve performance if you know that changes
			// in content do not change the layout size of the RecyclerView
			setHasFixedSize(true)

			// use a linear layout manager
			layoutManager = viewManager

			// specify an viewAdapter (see also next example)
			adapter = viewAdapter
		}*/
	}
}