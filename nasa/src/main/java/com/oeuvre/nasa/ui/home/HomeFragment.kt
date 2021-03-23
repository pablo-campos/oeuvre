package com.oeuvre.nasa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oeuvre.nasa.R
import com.oeuvre.nasa.adapter.PhotoAdapter
import com.oeuvre.nasa.model.Photo

class HomeFragment : Fragment() {


	private lateinit var viewAdapter: RecyclerView.Adapter<*>
	private lateinit var viewManager: RecyclerView.LayoutManager
	private lateinit var homeViewModel: HomeViewModel

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

		homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

		val root = inflater.inflate(R.layout.fragment_apod, container, false)
		val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)

		homeViewModel.text.observe(viewLifecycleOwner, Observer {
			// TODO update: textView.text = it
		})

		// Add fotos here:
		val photos1 = Photo("Title 1", "Description 1")
		val photos2 = Photo("Title 2", "Description 2")
		val photos3 = Photo("Title 3", "Description 3")
		val photos = listOf(photos1, photos2, photos3)

		viewManager = LinearLayoutManager(activity)
		viewAdapter = PhotoAdapter(photos)

		recyclerView.apply {

			// Use this setting to improve performance if you know that changes
			// in content do not change the layout size of the RecyclerView
			setHasFixedSize(true)

			// use a linear layout manager
			layoutManager = viewManager

			// specify an viewAdapter (see also next example)
			adapter = viewAdapter
		}

		return root
	}
}