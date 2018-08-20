package com.oeuvre.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oeuvre.R;
import com.oeuvre.model.Work;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public class ViewHolder extends RecyclerView.ViewHolder {

	// Each data item is just a string in this case
	public CardView cardView;
	public TextView title;
	public TextView description;
	public ImageView preview;



	public ViewHolder (View v) {
		super(v);

		cardView = (CardView) v;
		cardView.setCardBackgroundColor(v.getContext().getResources().getColor(R.color.cardBackgroundColor, null));
		title = v.findViewById(R.id.work_title);
		description = v.findViewById(R.id.work_description);
		preview = v.findViewById(R.id.work_image);
	}



	public void initialize(final Work work){

		title.setText(work.getTitle());
		description.setText(work.getDescription());

		if (work.getPreview() == null){
			preview.setVisibility(View.GONE);
		} else {
			preview.setVisibility(View.VISIBLE);
			Glide.with(preview.getContext()).load(work.getPreview()).into(preview);
		}
	}

}