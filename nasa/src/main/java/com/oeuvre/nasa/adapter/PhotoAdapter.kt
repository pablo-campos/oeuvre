package com.oeuvre.nasa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oeuvre.nasa.R
import com.oeuvre.nasa.model.Photo

class PhotoAdapter (private val photos: List<Photo>) : RecyclerView.Adapter<PhotoViewHolder>() {



	// Create new views (invoked by the layout manager)
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_foto, parent, false)
		return PhotoViewHolder(view)
	}



	// Replace the contents of a view (invoked by the layout manager)
	override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
		holder.initialize(photos.get(position))
	}



	// Return the size of your dataset (invoked by the layout manager)
	override fun getItemCount(): Int {
		return photos.size
	}
}