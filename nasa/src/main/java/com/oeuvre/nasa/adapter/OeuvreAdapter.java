package com.oeuvre.nasa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oeuvre.nasa.R;
import com.oeuvre.nasa.model.Work;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class OeuvreAdapter extends RecyclerView.Adapter<ViewHolder> {

	private List<Work> works;
	 OeuvreClickListener oeuvreClickListener;



	// Provide a suitable constructor (depends on the kind of dataset)
	public OeuvreAdapter (List<Work> works, OeuvreClickListener oeuvreClickListener) {
		this.works = works;
		this.oeuvreClickListener = oeuvreClickListener;
	}



	// Create new views (invoked by the layout manager)
	@Override
	public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_oeuvre, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}



	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder (ViewHolder holder, final int position) {
		holder.initialize(works.get(position), oeuvreClickListener);
	}



	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount () {
		return works.size();
	}
}