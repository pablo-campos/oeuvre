package com.oeuvre.nasa.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.oeuvre.nasa.R
import com.oeuvre.nasa.model.Photo

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
class PhotoViewHolder(v: View) : RecyclerView.ViewHolder(v) {



	// Each data item is just a string in this case
	var cardView: CardView = v as CardView
	var title: TextView = v.findViewById(R.id.work_title)
	var description: TextView = v.findViewById(R.id.work_description)
	var preview: ImageView = v.findViewById(R.id.work_image)



	fun initialize(photo: Photo) {
		cardView.setOnClickListener { v: View? -> }
		title.text = photo.title
		description.text = photo.description

		/*if (work.getPreview() == null){
			preview.setVisibility(View.GONE);
		} else {
			preview.setVisibility(View.VISIBLE);
			Glide.with(preview.getContext()).load(work.getPreview()).into(preview);
		}*/
	}

}