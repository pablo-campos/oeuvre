package com.oeuvre.nasa.ui.apod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.oeuvre.nasa.R
import com.oeuvre.nasa.model.Apod


class ApodFragment : Fragment() {


	private var apod: Apod = Apod()
	private lateinit var apodViewModel: ApodViewModel
	lateinit var imageView: ImageView
	lateinit var title: TextView
	lateinit var explanation: TextView
	lateinit var copyright: TextView
	lateinit var date: TextView

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

		apodViewModel = ViewModelProvider(this).get(ApodViewModel::class.java)

		val root = inflater.inflate(R.layout.fragment_apod, container, false)
		imageView = root.findViewById(R.id.apod_image)
		title = root.findViewById(R.id.apod_title)
		explanation = root.findViewById(R.id.apod_explanation)
		copyright = root.findViewById(R.id.apod_copyright)
		date = root.findViewById(R.id.apod_date)

		apodViewModel = ViewModelProviders.of(this).get(ApodViewModel::class.java)
		apodViewModel.init()
		apodViewModel.getNewsRepository()?.observe(viewLifecycleOwner) { response ->
			if (response != null) {
				apod = response
				updateUI()
			}
		}

		return root
	}


	fun updateUI(){
		Glide.with(this).load(apod.hdUrl).into(imageView)
		title.text = apod.title
		explanation.text = apod.explanation
		copyright.text = apod.copyright
		date.text = apod.date
	}
}