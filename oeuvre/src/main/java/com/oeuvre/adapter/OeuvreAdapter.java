package com.oeuvre.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oeuvre.R;
import com.oeuvre.model.Work;

import java.util.List;

public class OeuvreAdapter extends RecyclerView.Adapter<ViewHolder> {

	private List<Work> works;



	// Provide a suitable constructor (depends on the kind of dataset)
	public OeuvreAdapter (List<Work> works) {
		this.works = works;
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
	public void onBindViewHolder (ViewHolder holder, int position) {
		holder.initialize(works.get(position));
	}



	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount () {
		return works.size();
	}
}