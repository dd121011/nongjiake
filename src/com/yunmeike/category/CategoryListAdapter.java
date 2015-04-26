package com.yunmeike.category;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoryListAdapter extends BaseAdapter {
	Activity context;
	List<CategoryBean> list;

	public CategoryListAdapter(Activity context, List<CategoryBean> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		CategoryBean item = list.get(position);
		
		LinearLayout layout = new LinearLayout(context);
		TextView text = new TextView(context);
		layout.addView(text);
		convertView = layout;

		text.setText(item.name);
		
		return convertView;
	}

}
